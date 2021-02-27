package pluginlogin.manager.signin.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.persistence.EntityManager;

import commom.Constants;
import core.manager.confmain.impl.ConfMainManagerImpl;
import core.manager.mestreusuariosistema.impl.MestreUsuarioSistemaManagerImpl;
import core.model.dto.MensagemDto;
import core.model.entity.MestreUsuarioSistemaEntity;
import core.util.Funcoes;
import core.util.Mensagem;
import pluginlogin.manager.signin.SignInManager;
import pluginlogin.model.dto.SignInFormDTO;
import pluginlogin.model.dto.SignInResponseDTO;

public class SignInManagerImpl implements SignInManager {
	EntityManager entityManager = new ConfMainManagerImpl().currentEntityManager();

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
			mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl()
					.consultarUsuarioSistemaPorUsuarioESenha(mestreUsuarioSistemaEntity, entityManager);
			if (mestreUsuarioSistemaEntity != null) {
				if (mestreUsuarioSistemaEntity.getAtivo() == Constants.ATIVO_S) {
					mestreUsuarioSistemaEntity
							.setToken(new Funcoes().gerarToken(signInFormDTO.getNumeroDocumentoCPF()));
					mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl().merge(mestreUsuarioSistemaEntity,
							entityManager);
					entityManager.getTransaction().commit();
					entityManager.clear();
					signInResponseDTO.setToken(mestreUsuarioSistemaEntity.getToken());
					signInResponseDTO.setNumeroDocumentoCPF(signInFormDTO.getNumeroDocumentoCPF());
					signInResponseDTO.setMensagemDto(new Mensagem().usuarioEncontrado());
				} else {
					signInResponseDTO.setMensagemDto(new Mensagem().usuarioNaoVigente());
				}
			} else {
				signInResponseDTO.setMensagemDto(new Mensagem().usuarioNaoEncontrado());
			}
		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
			entityManager.getTransaction().rollback();
			entityManager.clear();
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar login");
			signInResponseDTO.setMensagemDto(mensagemDto);
		}
		return signInResponseDTO;
	}
}
