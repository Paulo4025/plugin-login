package pluginlogin.manager.signUp.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

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
import core.util.Constants;
import core.util.Funcoes;
import core.util.Mensagem;
import pluginlogin.manager.signUp.SignUpManager;
import pluginlogin.model.dto.SignUpFormDto;

public class SignUpManagerImpl implements SignUpManager {

	EntityManager entityManager = new ConfMainManagerImpl().currentEntityManager();

	/*
	 * (non-Javadoc)
	 * 
	 * @see pluginlogin.manager.signUp.SignUpManager#signUpBasic(pluginlogin.model.dto.SignUpFormDto)
	 */
	public MensagemDto signUpBasic(SignUpFormDto signUpFormDto) {

		MensagemDto mensagemDto = new MensagemDto();
		try {

			IdentificacaoPessoaEntity identificacaoPessoaEntity = new IdentificacaoPessoaEntity();
			identificacaoPessoaEntity.setNumeroDocumento(signUpFormDto.getNumeroCpf());
			identificacaoPessoaEntity = new IdentificacaoPessoaManagerImpl().consultarIdentificacaoPessoaPorNumeroDocumento(identificacaoPessoaEntity,
					entityManager);
			InformacaoContatoPessoaEntity informacaoContatoPessoaEntity = new InformacaoContatoPessoaEntity();
			informacaoContatoPessoaEntity.setContato(signUpFormDto.getEmail());
			List<InformacaoContatoPessoaEntity> listaInformacaoContatoPessoaEntity = new InformacaoContatoPessoaManagerImpl()
					.consultaInformacaoContatoPessoaPorContato(informacaoContatoPessoaEntity, entityManager);

			if (listaInformacaoContatoPessoaEntity.isEmpty() && identificacaoPessoaEntity == null) {
				MestrePessoaEntity mestrePessoaEntity = new MestrePessoaEntity();
				mestrePessoaEntity.setIdTipoPessoa(Constants.PESSOA_NATURAL);
				mestrePessoaEntity = new MestrePessoaManagerImpl().merge(mestrePessoaEntity, entityManager);

				identificacaoPessoaEntity = new IdentificacaoPessoaEntity();
				identificacaoPessoaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				identificacaoPessoaEntity.setNumeroDocumento(signUpFormDto.getNumeroCpf());
				identificacaoPessoaEntity.setIdTipoDocumentoIdentificacao(Constants.TIPO_DOCUMENTO_CPF);
				identificacaoPessoaEntity = new IdentificacaoPessoaManagerImpl().merge(identificacaoPessoaEntity, entityManager);

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
				informacaoContatoPessoaEntity.setDescricao("E-mail Principal");
				informacaoContatoPessoaEntity = new InformacaoContatoPessoaManagerImpl().merge(informacaoContatoPessoaEntity, entityManager);

				String[] pass = new Funcoes().gerarSenha();

				MestreUsuarioSistemaEntity mestreUsuarioSistemaEntity = new MestreUsuarioSistemaEntity();
				mestreUsuarioSistemaEntity.setIdPessoa(mestrePessoaEntity.getIdPessoa());
				mestreUsuarioSistemaEntity.setUsuarioLogin(signUpFormDto.getNumeroCpf());
				mestreUsuarioSistemaEntity.setUsuarioSenha(pass[1]);
				mestreUsuarioSistemaEntity.setIdTipoUsuario(Constants.TIPO_USUARIO_COMUM);
				mestreUsuarioSistemaEntity.setDataRegistro(new Date());
				mestreUsuarioSistemaEntity.setAtivo(Constants.ATIVO_S);
				mestreUsuarioSistemaEntity.setEmail(signUpFormDto.getEmail());
				mestreUsuarioSistemaEntity = new MestreUsuarioSistemaManagerImpl().merge(mestreUsuarioSistemaEntity, entityManager);

				entityManager.getTransaction().commit();
				entityManager.clear();
				String contentEmail = "Olá, seu cadastro no servicoApp foi realizado com sucesso!\n Dados de Acesso: Usuario: "
						+ signUpFormDto.getNumeroCpf() + "\n Senha " + pass[0];
				new MailManagerImpl().enviarEmail(contentEmail, signUpFormDto.getEmail());
				mensagemDto = new Mensagem().cadastroRealizadoSucesso();
			} else if (listaInformacaoContatoPessoaEntity.get(0) != null && identificacaoPessoaEntity != null) {
				mensagemDto = new Mensagem().emailCpfJaCadastrado();
			} else if (listaInformacaoContatoPessoaEntity.get(0) != null) {
				mensagemDto = new Mensagem().emailJaCadastrado();
			} else if (identificacaoPessoaEntity != null) {
				mensagemDto = new Mensagem().cpfJaCadastrado();
			}
			return mensagemDto;
		} catch (Exception e) {
			System.out.println(e);
			entityManager.getTransaction().rollback();
			mensagemDto = new MensagemDto();
			mensagemDto.setStatus(false);
			mensagemDto.setCodigo(0);
			mensagemDto.setMensagem("Erro ao realizar cadastro");
			return mensagemDto;
		}

	}

}
