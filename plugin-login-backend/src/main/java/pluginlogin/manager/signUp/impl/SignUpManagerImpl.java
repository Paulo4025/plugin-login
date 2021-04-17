package pluginlogin.manager.signup.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.persistence.EntityManager;

import commom.Constants;
import commom.MensagemEmail;
import core.manager.identificacaopessoa.impl.IdentificacaoPessoaManagerImpl;
import core.manager.mailmanager.impl.MailManagerImpl;
import core.manager.mestrecontatopessoa.impl.MestreContatoPessoaManagerImpl;
import core.manager.mestrepessoa.impl.MestrePessoaManagerImpl;
import core.manager.mestreusuariosistema.impl.MestreUsuarioSistemaManagerImpl;
import core.manager.persistence.impl.PersistenceManagerImpl;
import core.manager.pessoanatural.impl.PessoaNaturalManagerImpl;
import core.model.EntityManagerModel;
import core.model.dto.MensagemDto;
import core.model.entity.IdentificacaoPessoaEntity;
import core.model.entity.MestreContatoPessoaEntity;
import core.model.entity.MestrePessoaEntity;
import core.model.entity.MestreUsuarioSistemaEntity;
import core.model.entity.PessoaNaturalEntity;
import core.util.Core_Funcoes;
import core.util.Core_Mensagem;
import pluginlogin.manager.signup.SignUpManager;
import pluginlogin.model.dto.SignUpFormDto;
 
public class SignUpManagerImpl implements SignUpManager {

	EntityManagerModel entityManagerModel = new PersistenceManagerImpl().createTransaction();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pluginlogin.manager.signup.SignUpManager#realizarCadastroBasico(pluginlogin.
	 * model.dto.SignUpFormDto)
	 */
	public MensagemDto realizarCadastroBasico(SignUpFormDto signUpFormDto) {
		MensagemDto mensagemDto = new MensagemDto();
		try {
			mensagemDto = this.signUpBasic(signUpFormDto);
			switch (mensagemDto.getCodigo()) {
			case 1:
				// - Caso o registro tenha sido realizado com sucesso
				String contentEmail = new MensagemEmail().EMAIL_CONFIRMACAO_CADASTRO_SUCESSO
						.replace("<USER>", signUpFormDto.getNumeroDocumentoCPF())
						.replace("<PASS>", mensagemDto.getTemp());
				new MailManagerImpl().enviarEmail(contentEmail, signUpFormDto.getEmail());
				new PersistenceManagerImpl().commitTransaction(entityManagerModel);
				return mensagemDto;
			}
		} catch (Exception e) {
			new PersistenceManagerImpl().rollbackTransaction(entityManagerModel);
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
		} finally {
			new PersistenceManagerImpl().closeTransaction(entityManagerModel);
		}
		return mensagemDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * pluginlogin.manager.signUp.SignUpManager#signUpBasic(pluginlogin.model.dto.
	 * SignUpFormDto)
	 */
	public MensagemDto signUpBasic(SignUpFormDto signUpFormDto) {

		MensagemDto mensagemDto = new MensagemDto();
		try {
			mensagemDto = this.validarDadosPreCadastro(signUpFormDto.getNumeroDocumentoCPF(), signUpFormDto.getEmail(),
					signUpFormDto.getCelular(), entityManagerModel.getEntityManager());

			String[] pass = new Core_Funcoes().gerarSenha();
			if (mensagemDto.getStatus() == true && mensagemDto.getCodigo() == Constants.CODIGO_VALIDACAO_SUCESSO) {
				MestrePessoaEntity mestrePessoaEntity = new MestrePessoaEntity();
				mestrePessoaEntity.setIdTipoPessoa(Constants.ID_TIPO.PESSOA.ID_TIPO_PESSOA_NATURAL);
				mestrePessoaEntity = new MestrePessoaManagerImpl().merge(mestrePessoaEntity,
						entityManagerModel.getEntityManager());

				IdentificacaoPessoaEntity identificacaoPessoaEntity = new IdentificacaoPessoaEntity();
				identificacaoPessoaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				identificacaoPessoaEntity.setNumeroDocumento(signUpFormDto.getNumeroDocumentoCPF());
				identificacaoPessoaEntity
						.setIdTipoDocumentoIdentificacao(Constants.ID_TIPO.DOCUMENTO_IDENTIDADE.ID_TIPO_DOCUMENTO_CPF);
				identificacaoPessoaEntity = new IdentificacaoPessoaManagerImpl().merge(identificacaoPessoaEntity,
						entityManagerModel.getEntityManager());

				PessoaNaturalEntity pessoaNaturalEntity = new PessoaNaturalEntity();
				pessoaNaturalEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				pessoaNaturalEntity.setNomeCompleto(signUpFormDto.getNomeCompleto());
				pessoaNaturalEntity = new PessoaNaturalManagerImpl().merge(pessoaNaturalEntity,
						entityManagerModel.getEntityManager());

				MestreContatoPessoaEntity mestreContatoPessoaEntity = new MestreContatoPessoaEntity();
				mestreContatoPessoaEntity.setAtivo(Constants.ATIVO_S);
				mestreContatoPessoaEntity.setContato(signUpFormDto.getCelular());
				mestreContatoPessoaEntity.setIdTipoContato(Constants.ID_TIPO.CONTATO.ID_TIPO_CONTATO_CELULAR);
				mestreContatoPessoaEntity = new MestreContatoPessoaManagerImpl().mergeMestreContatoEntity(
						mestreContatoPessoaEntity, mestrePessoaEntity.getIdPessoa(),
						entityManagerModel.getEntityManager());

				mestreContatoPessoaEntity = new MestreContatoPessoaEntity();
				mestreContatoPessoaEntity.setAtivo(Constants.ATIVO_S);
				mestreContatoPessoaEntity.setContato(signUpFormDto.getEmail());
				mestreContatoPessoaEntity.setIdTipoContato(Constants.ID_TIPO.CONTATO.ID_TIPO_CONTATO_EMAIL);
				mestreContatoPessoaEntity = new MestreContatoPessoaManagerImpl().mergeMestreContatoEntity(
						mestreContatoPessoaEntity, mestrePessoaEntity.getIdPessoa(),
						entityManagerModel.getEntityManager());

				MestreUsuarioSistemaEntity mestreUsuarioSistemaEntity = new MestreUsuarioSistemaEntity();
				mestreUsuarioSistemaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				mestreUsuarioSistemaEntity.setUsuarioLogin(signUpFormDto.getNumeroDocumentoCPF());
				mestreUsuarioSistemaEntity.setUsuarioSenha(pass[1]);
				mestreUsuarioSistemaEntity.setIdTipoUsuario(Constants.ID_TIPO.USUARIO.ID_TIPO_USUARIO_COMUM);
				mestreUsuarioSistemaEntity.setDataRegistro(new Date());
				mestreUsuarioSistemaEntity.setAtivo(Constants.ATIVO_S);
				mestreUsuarioSistemaEntity.setEmail(signUpFormDto.getEmail());
				mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl().merge(mestreUsuarioSistemaEntity,
						entityManagerModel.getEntityManager());

				mensagemDto = new Core_Mensagem().cadastroRealizadoSucesso();
				mensagemDto.setTemp(pass[0]);
			}
			return mensagemDto;
		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar cadastro");
			return mensagemDto;
		}

	}

	/**
	 * Realiza a valida��o do dados do pr�-cadastro <br>
	 * Criado em 29 de mar. de 2021 as 22:07:28 <br>
	 * 
	 * @author Paulo Gomes Marques <br>
	 * @param numeroDocumentoCPF
	 * @param email
	 * @param celular
	 * @param entityManager
	 * @return
	 */
	private MensagemDto validarDadosPreCadastro(String numeroDocumentoCPF, String email, String celular,
			EntityManager entityManager) {
		MensagemDto mensagemDto = new MensagemDto();

		IdentificacaoPessoaEntity identificacaoPessoaEntity = new IdentificacaoPessoaEntity()
				.consultarIdentificacaoPessoaPorNumeroDocumento(numeroDocumentoCPF,
						entityManagerModel.getEntityManager());

		MestreContatoPessoaEntity mestreContatoPessoaEntityEmail = new MestreContatoPessoaEntity()
				.consultarMestreContatoPessoaPorContato(email, entityManagerModel.getEntityManager());

		MestreContatoPessoaEntity mestreContatoPessoaEntityCelular = new MestreContatoPessoaEntity()
				.consultarMestreContatoPessoaPorContato(celular, entityManagerModel.getEntityManager());
		if (mestreContatoPessoaEntityEmail == null && identificacaoPessoaEntity == null
				&& mestreContatoPessoaEntityCelular == null) {
			mensagemDto = new Core_Mensagem().validacaoSucesso();
		} else if (mestreContatoPessoaEntityEmail != null && identificacaoPessoaEntity != null
				&& mestreContatoPessoaEntityCelular != null) {
			mensagemDto = new Core_Mensagem().emailCpfCelularJaCadastrado();
		} else if (mestreContatoPessoaEntityEmail != null && identificacaoPessoaEntity != null
				&& mestreContatoPessoaEntityCelular == null) {
			mensagemDto = new Core_Mensagem().emailCpfJaCadastrado();
		} else if (mestreContatoPessoaEntityEmail != null && mestreContatoPessoaEntityCelular != null) {
			mensagemDto = new Core_Mensagem().emailCelularJaRegistrado();
		} else if (mestreContatoPessoaEntityEmail != null) {
			mensagemDto = new Core_Mensagem().emailJaCadastrado();
		} else if (mestreContatoPessoaEntityCelular != null) {
			mensagemDto = new Core_Mensagem().celularJaRegistrado();
		} else if (identificacaoPessoaEntity != null) {
			mensagemDto = new Core_Mensagem().cpfJaCadastrado();
		}
		return mensagemDto;
	}

}
