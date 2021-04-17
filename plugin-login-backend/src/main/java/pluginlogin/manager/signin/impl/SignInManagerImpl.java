package pluginlogin.manager.signin.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import commom.Constants;
import commom.MensagemEmail;
import core.manager.autorizacaousuario.impl.AutorizacaoUsuarioManagerImpl;
import core.manager.mailmanager.impl.MailManagerImpl;
import core.manager.mestreusuariosistema.impl.MestreUsuarioSistemaManagerImpl;
import core.manager.paramentro.impl.ParametrosManagerImpl;
import core.manager.persistence.impl.PersistenceManagerImpl;
import core.model.EntityManagerModel;
import core.model.dto.MensagemDto;
import core.model.entity.AutorizacaoUsuarioEntity;
import core.model.entity.MestreUsuarioSistemaEntity;
import core.model.entity.ParametrosEntity;
import core.util.Core_Funcoes;
import core.util.Core_Mensagem;
import pluginlogin.manager.signin.SignInManager;
import pluginlogin.model.dto.SignInFormDTO;
import pluginlogin.model.dto.SignInResponseDTO;

public class SignInManagerImpl implements SignInManager {

	EntityManagerModel entityManagerModel = new PersistenceManagerImpl().createTransaction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pluginlogin.manager.signin.SignInManager#ativarTokenNovoDispositivo(java.lang
	 * .String, java.lang.Integer)
	 */
	public MensagemDto ativarTokenNovoDispositivo(String token, Integer idUsuario) {
		MensagemDto mensagemDto = new MensagemDto();
		try {
			AutorizacaoUsuarioEntity autorizacaoUsuarioEntity = new AutorizacaoUsuarioEntity();
			autorizacaoUsuarioEntity.setToken(token);
			autorizacaoUsuarioEntity.setIdUsuario(idUsuario);
			autorizacaoUsuarioEntity = new AutorizacaoUsuarioManagerImpl()
					.consultarAutorizacaoUsuarioEntityPorIdUsuarioAndToken(autorizacaoUsuarioEntity,
							entityManagerModel.getEntityManager());
			if (autorizacaoUsuarioEntity != null) {
				if (autorizacaoUsuarioEntity.getAtivo() == Constants.ATIVO_N) {
					autorizacaoUsuarioEntity.setAtivo(Constants.ATIVO_S);
					autorizacaoUsuarioEntity = new AutorizacaoUsuarioManagerImpl().merge(autorizacaoUsuarioEntity,
							entityManagerModel.getEntityManager());

					mensagemDto = new Core_Mensagem().tokenEncontrado();
				} else {
					mensagemDto = new Core_Mensagem().tokenJaEstaAtivado();
				}
			} else {
				mensagemDto = new Core_Mensagem().tokenNaoEncontrado();
			}
			new PersistenceManagerImpl().commitTransaction(entityManagerModel);
		} catch (Exception e) {
			new PersistenceManagerImpl().rollbackTransaction(entityManagerModel);
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar ativação do token");
		} finally {
			new PersistenceManagerImpl().closeTransaction(entityManagerModel);
		}
		return mensagemDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see pluginlogin.manager.signin.SignInManager#signIn(pluginlogin.model.dto.
	 * SignInFormDTO)
	 */
	public SignInResponseDTO signIn(SignInFormDTO signInFormDTO) {
		MensagemDto mensagemDto = new MensagemDto();
		SignInResponseDTO signInResponseDTO = new SignInResponseDTO();
		try {
			MestreUsuarioSistemaEntity mestreUsuarioSistemaEntity = new MestreUsuarioSistemaEntity();
			mestreUsuarioSistemaEntity.setUsuarioLogin(signInFormDTO.getNumeroDocumentoCPF());
			mestreUsuarioSistemaEntity.setUsuarioSenha(signInFormDTO.getSenha());
			mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl().consultarUsuarioSistemaPorUsuarioESenha(
					mestreUsuarioSistemaEntity, entityManagerModel.getEntityManager());

			if (mestreUsuarioSistemaEntity != null) {
				if (mestreUsuarioSistemaEntity.getAtivo() == Constants.ATIVO_S) {
					AutorizacaoUsuarioEntity autorizacaoUsuarioEntity = new AutorizacaoUsuarioEntity();
					autorizacaoUsuarioEntity.setToken(new Core_Funcoes().gerarToken(
							mestreUsuarioSistemaEntity.getUsuarioLogin() + signInFormDTO.getIpDispositivo()));
					autorizacaoUsuarioEntity.setIdUsuario(mestreUsuarioSistemaEntity.getIdUsuario());
					autorizacaoUsuarioEntity = new AutorizacaoUsuarioManagerImpl()
							.consultarAutorizacaoUsuarioEntityPorIdUsuarioAndToken(autorizacaoUsuarioEntity,
									entityManagerModel.getEntityManager());
					if (autorizacaoUsuarioEntity == null) {
						// - Usuario e senha existem, porem o dispositivo não possui autorização.
						autorizacaoUsuarioEntity = new AutorizacaoUsuarioEntity();

						autorizacaoUsuarioEntity.setToken(new Core_Funcoes().gerarToken(
								mestreUsuarioSistemaEntity.getUsuarioLogin() + signInFormDTO.getIpDispositivo()));
						autorizacaoUsuarioEntity.setIdUsuario(mestreUsuarioSistemaEntity.getIdUsuario());
						autorizacaoUsuarioEntity.setDataRegistro(new Date());
						autorizacaoUsuarioEntity.setIpDispositivo(signInFormDTO.getIpDispositivo());
						autorizacaoUsuarioEntity.setAtivo(Constants.ATIVO_N);
						autorizacaoUsuarioEntity = new AutorizacaoUsuarioManagerImpl().merge(autorizacaoUsuarioEntity,
								entityManagerModel.getEntityManager());

						new MailManagerImpl().enviarEmail(
								this.pegarUrlAtivarTokenUsuario(autorizacaoUsuarioEntity.getToken(),
										mestreUsuarioSistemaEntity.getIdUsuario()),
								mestreUsuarioSistemaEntity.getEmail());

						mensagemDto = new MensagemDto();
						mensagemDto = new Core_Mensagem().loginNovoDispositivo();
						mensagemDto.setMensagem(
								mensagemDto.getMensagem().replace("<EMAIL>", mestreUsuarioSistemaEntity.getEmail()));
						signInResponseDTO.setMensagemDto(mensagemDto);
					} else {
						if (autorizacaoUsuarioEntity.getAtivo() == Constants.ATIVO_N) {
							mensagemDto = new MensagemDto();
							mensagemDto = new Core_Mensagem().loginNovoDispositivo();

							new MailManagerImpl().enviarEmail(
									this.pegarUrlAtivarTokenUsuario(autorizacaoUsuarioEntity.getToken(),
											mestreUsuarioSistemaEntity.getIdUsuario()),
									mestreUsuarioSistemaEntity.getEmail());

							mensagemDto.setMensagem(mensagemDto.getMensagem().replace("<EMAIL>",
									mestreUsuarioSistemaEntity.getEmail()));
							signInResponseDTO.setMensagemDto(mensagemDto);
						} else {
							signInResponseDTO.setToken(autorizacaoUsuarioEntity.getToken());
							signInResponseDTO.setNumeroDocumentoCPF(signInFormDTO.getNumeroDocumentoCPF());
							signInResponseDTO.setMensagemDto(new Core_Mensagem().usuarioEncontrado());
						}
					}
				} else {
					signInResponseDTO.setMensagemDto(new Core_Mensagem().usuarioNaoVigente());
				}
			} else {
				signInResponseDTO.setMensagemDto(new Core_Mensagem().usuarioNaoEncontrado());
			}
			new PersistenceManagerImpl().commitTransaction(entityManagerModel);
		} catch (Exception e) {
			new PersistenceManagerImpl().rollbackTransaction(entityManagerModel);
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar login");
			signInResponseDTO.setMensagemDto(mensagemDto);
		} finally {
			new PersistenceManagerImpl().closeTransaction(entityManagerModel);
		}
		return signInResponseDTO;
	}

	/**
	 * Consulta a url para ativar o token do usuario <br>
	 * Criado em 29 de mar. de 2021 as 22:48:01 <br>
	 * 
	 * @author Paulo Gomes Marques <br>
	 * @param token
	 * @param idUsuario
	 * @return
	 * @throws Exception
	 */
	private String pegarUrlAtivarTokenUsuario(String token, int idUsuario) throws Exception {
		ParametrosEntity parametrosEntity = new ParametrosEntity();
		if (token != null) {
			parametrosEntity.setIdParamentro(Constants.PARAMETROS.ID_PARAMETRO_URL_PARA_LINK_ATIVAR_TOKEN_USUARIO);
			parametrosEntity = new ParametrosManagerImpl().consultarParamentrosEntityIdParamentro(parametrosEntity,
					entityManagerModel.getEntityManager());

			return new MensagemEmail().EMAIL_CONFIRMACAO_LOGIN_NOVO_DISPOSIVITO.replace("<LINK>",
					parametrosEntity.getValor() + "/" + token + "/" + idUsuario);
		}
		throw new Exception("O token não pode ser null");
	}
}
