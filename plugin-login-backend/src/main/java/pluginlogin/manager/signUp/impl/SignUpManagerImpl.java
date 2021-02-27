package pluginlogin.manager.signUp.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import commom.Constants;
import core.manager.confmain.impl.ConfMainManagerImpl;
import core.manager.identificacaopessoa.impl.IdentificacaoPessoaManagerImpl;
import core.manager.informacaocontatopessoa.impl.InformacaoContatoPessoaManagerImpl;
import core.manager.mailmanager.impl.MailManagerImpl;
import core.manager.mestrepessoa.impl.MestrePessoaManagerImpl;
import core.manager.mestreusuariosistema.impl.MestreUsuarioSistemaManagerImpl;
import core.manager.pessoanatural.impl.PessoaNaturalManagerImpl;
import core.model.dto.MensagemDto;
import core.model.entity.IdentificacaoPessoaEntity;
import core.model.entity.InformacaoContatoPessoaEntity;
import core.model.entity.MestrePessoaEntity;
import core.model.entity.MestreUsuarioSistemaEntity;
import core.model.entity.PessoaNaturalEntity;
import core.util.Funcoes;
import core.util.Mensagem;
import pluginlogin.manager.signUp.SignUpManager;
import pluginlogin.model.dto.SignUpFormDto;
import pluginlogin.util.MensagemEmail;

public class SignUpManagerImpl implements SignUpManager {

	EntityManager entityManager = new ConfMainManagerImpl().currentEntityManager();

	public MensagemDto realizarCadastroBasico(SignUpFormDto signUpFormDto) {
		MensagemDto mensagemDto = new MensagemDto();
		try {
			mensagemDto = new SignUpManagerImpl().signUpBasic(signUpFormDto);
			switch (mensagemDto.getCodigo()) {
			// - Caso o registro tenha sido realizado com sucesso
			case 1:
				String contentEmail = MensagemEmail.EMAIL_CONFIRMACAO_CADASTRO_USECLINIC_SUCESSO
						.replace("<USER>", signUpFormDto.getNumeroDocumentoCPF())
						.replace("<PASS>", mensagemDto.getTemp());
				new MailManagerImpl().enviarEmail(contentEmail, signUpFormDto.getEmail());
				return mensagemDto;

			}
		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
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

			IdentificacaoPessoaEntity identificacaoPessoaEntity = new IdentificacaoPessoaEntity();
			identificacaoPessoaEntity.setNumeroDocumento(signUpFormDto.getNumeroDocumentoCPF());
			identificacaoPessoaEntity = new IdentificacaoPessoaManagerImpl()
					.consultarIdentificacaoPessoaPorNumeroDocumento(identificacaoPessoaEntity, entityManager);
			InformacaoContatoPessoaEntity informacaoContatoPessoaEntity = new InformacaoContatoPessoaEntity();
			informacaoContatoPessoaEntity.setContato(signUpFormDto.getEmail());
			List<InformacaoContatoPessoaEntity> listaInformacaoContatoPessoaEntity = new InformacaoContatoPessoaManagerImpl()
					.consultaInformacaoContatoPessoaPorContato(informacaoContatoPessoaEntity, entityManager);
			String[] pass = new Funcoes().gerarSenha();
			if (listaInformacaoContatoPessoaEntity.isEmpty() && identificacaoPessoaEntity == null) {
				MestrePessoaEntity mestrePessoaEntity = new MestrePessoaEntity();
				mestrePessoaEntity.setIdTipoPessoa(Constants.PESSOA_NATURAL);
				mestrePessoaEntity = new MestrePessoaManagerImpl().merge(mestrePessoaEntity, entityManager);

				identificacaoPessoaEntity = new IdentificacaoPessoaEntity();
				identificacaoPessoaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				identificacaoPessoaEntity.setNumeroDocumento(signUpFormDto.getNumeroDocumentoCPF());
				identificacaoPessoaEntity.setIdTipoDocumentoIdentificacao(Constants.TIPO_DOCUMENTO_CPF);
				identificacaoPessoaEntity = new IdentificacaoPessoaManagerImpl().merge(identificacaoPessoaEntity,
						entityManager);

				PessoaNaturalEntity pessoaNaturalEntity = new PessoaNaturalEntity();
				pessoaNaturalEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				pessoaNaturalEntity.setNomeCompleto(signUpFormDto.getNomeCompleto());
				pessoaNaturalEntity = new PessoaNaturalManagerImpl().merge(pessoaNaturalEntity, entityManager);

				informacaoContatoPessoaEntity = new InformacaoContatoPessoaEntity();
				informacaoContatoPessoaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				informacaoContatoPessoaEntity.setContato(signUpFormDto.getEmail());
				informacaoContatoPessoaEntity.setIdTipoContato(Constants.TIPO_CONTATO_EMAIL);
				informacaoContatoPessoaEntity.setAtivo(Constants.ATIVO_S);
				informacaoContatoPessoaEntity.setCelularWhatssap(Constants.ATIVO_N);
				informacaoContatoPessoaEntity = new InformacaoContatoPessoaManagerImpl()
						.merge(informacaoContatoPessoaEntity, entityManager);

				MestreUsuarioSistemaEntity mestreUsuarioSistemaEntity = new MestreUsuarioSistemaEntity();
				mestreUsuarioSistemaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				mestreUsuarioSistemaEntity.setUsuarioLogin(signUpFormDto.getNumeroDocumentoCPF());
				mestreUsuarioSistemaEntity.setUsuarioSenha(pass[1]);
				mestreUsuarioSistemaEntity.setIdTipoUsuario(Constants.TIPO_USUARIO_COMUM);
				mestreUsuarioSistemaEntity.setDataRegistro(new Date());
				mestreUsuarioSistemaEntity.setAtivo(Constants.ATIVO_S);
				mestreUsuarioSistemaEntity.setEmail(signUpFormDto.getEmail());
				mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl().merge(mestreUsuarioSistemaEntity,
						entityManager);

				entityManager.getTransaction().commit();
				entityManager.clear();

				mensagemDto = new Mensagem().cadastroRealizadoSucesso();
				mensagemDto.setTemp(pass[0]);
			} else if (!listaInformacaoContatoPessoaEntity.isEmpty()
					&& signUpFormDto.getEmail().equals(listaInformacaoContatoPessoaEntity.get(0).getContato())
					&& identificacaoPessoaEntity != null) {
				mensagemDto = new Mensagem().emailCpfJaCadastrado();
			} else if (!listaInformacaoContatoPessoaEntity.isEmpty() && (listaInformacaoContatoPessoaEntity.isEmpty()
					|| listaInformacaoContatoPessoaEntity.get(0) != null)) {
				mensagemDto = new Mensagem().emailJaCadastrado();
			} else if (identificacaoPessoaEntity != null) {
				mensagemDto = new Mensagem().cpfJaCadastrado();
			}
			return mensagemDto;
		} catch (Exception e) {
			StringWriter outError = new StringWriter();
			e.printStackTrace(new PrintWriter(outError));
			System.out.println(outError.toString());
			entityManager.getTransaction().rollback();
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar cadastro");
			return mensagemDto;
		}

	}

}
