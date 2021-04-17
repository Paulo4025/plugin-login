import { Injectable } from "@angular/core";
import { Configuration } from "configuration/ambient/configuration";
import { UtilConstant } from "plugins/plugin-util-common/util-common-frontend/src/app/util-constants";
import { ServiceInterface } from "./ServiceInterface";

@Injectable()
export class ServiceConfig {
  constructor(
    private utilConstant: UtilConstant,
    private configuration: Configuration
  ) {}

  public service: ServiceInterface = {
    consultarListaTipoCargo: {
      endPoint:
        this.configuration.ambient.endPoint + "/tipoService/consultarTipoCargo",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {},
    },
    consultaPerfilFuncionario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/pessoaService/consultarPessoaNatural",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCPF: null,
        numeroDocumentoCNPJ: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: null,
          token: null,
        },
      },
    },
    consultarListaTipoEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarTipoEmpresa",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {},
    },
    consultarPerfilEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/empresaService/consultarPerfilEmpresa",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCNPJ: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: null,
          token: null,
        },
      },
    },
    preRegistrarEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/empresaService/preRegistrarEmpresa",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCNPJ: null,
        razaoSocial: null,
        email: null,
        idTipoUsuario: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: null,
          token: null,
        },
      },
    },
    alterarDadosUsuario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/userService/alterarDadosUsuario",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        email: null,
        emailAntigo: null,
        senha: null,
        usuarioAcesso: null,
      },
    },
    consultarListaTipoUsuarioLoja: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarTipoUsuarioLoja",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {},
    },
    recuperarSenhaUsuario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/userService/recuperarSenhaUsuario",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        email: null,
        numeroDocumentoCPF: null,
      },
    },
    gerenciarImagemPerfilPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/documentoService/gerenciarImagemPerfilPessoa",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        extencao: null,
        contenType: null,
        base64: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstant.constants.sessao.usuario
          ),
          token: localStorage.getItem(this.utilConstant.constants.sessao.token),
        },
      },
    },
    consultaIpDispositivo: {
      endPoint: "https://api64.ipify.org/?format=json",
      method: this.utilConstant.constants.TIPO.REQUEST.GET,
      json: null,
    },
    realizarCadastroBasico: {
      endPoint:
        this.configuration.ambient.endPoint + "/cadastroService/cadastroBasico",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        nomeCompleto: null,
        numeroDocumentoCPF: null,
        email: null,
      },
    },
    login: {
      endPoint: this.configuration.ambient.endPoint + "/loginService/login",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: null,
    },
    consultarInformacoesUsuario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/userService/consultarInformacoesUsuario",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCPF: localStorage.getItem(
          this.utilConstant.constants.sessao.usuario
        ),
        token: localStorage.getItem(this.utilConstant.constants.sessao.token),
      },
    },
    gerenciarPessoaNatural: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/pessoaService/gerenciarPessoaNatural",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCPF: null,
        nomeCompleto: null,
        dataNascimento: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstant.constants.sessao.usuario
          ),
          token: localStorage.getItem(this.utilConstant.constants.sessao.token),
        },
      },
    },
    gerenciarContatoPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/contatoService/gerenciarContatoPessoa",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        numeroTelefone: null,
        numeroCelular: null,
        email: null,
        idPessoa: null,
        isWhatssap: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstant.constants.sessao.usuario
          ),
          token: localStorage.getItem(this.utilConstant.constants.sessao.token),
        },
      },
    },
    consultarListaTipoEstado: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarListaTipoEstado",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {},
    },
    consultarListaTipoCidade: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarListaTipoCidade",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        idTipoEstado: null,
      },
    },
    gerenciarEnderecoPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/enderecoService/gerenciarEnderecoPessoaNatural",
      method: this.utilConstant.constants.TIPO.REQUEST.POST,
      json: {
        idEndereco: null,
        ativo: null,
        enderecoCompleto: null,
        numeroEndereco: null,
        complemento: null,
        cep: null,
        descricao: null,
        idTipoCidade: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstant.constants.sessao.usuario
          ),
          token: localStorage.getItem(this.utilConstant.constants.sessao.token),
        },
      },
    },
  };
}
