import { Injectable } from "@angular/core";
import { Configuration } from "configuration/ambient/configuration";
import { UtilConstants } from "plugins/plugin-common/plugin-common-frontend/src/app/util-constants";
import { ServiceInterface } from "./ServiceInterface";

@Injectable()
export class ServiceConfig {
  constructor(
    private utilConstants: UtilConstants,
    private configuration: Configuration
  ) {}

  public serviceInterface: ServiceInterface = {
    alterarDadosEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/empresaService/alterarDadosEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        razaoSocial: null,
        nomeFantasia: null,
        numeroDocumentoCNPJ: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    excluirContato: {
      endPoint:
        this.configuration.ambient.endPoint + "/contatoService/excluirContato",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        idContatoPessoa: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    excluirEndereco: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/enderecoService/excluirEndereco",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        idEndereco: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    alterarEnderecoEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/enderecoService/alterarEnderecoEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        idEndereco: null,
        ativo: null,
        enderecoCompleto: null,
        numeroEndereco: null,
        complemento: null,
        cep: null,
        descricao: null,
        idTipoCidade: null,
        numeroDocumento: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    inserirEnderecoEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/enderecoService/inserirEnderecoEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        idEndereco: null,
        ativo: null,
        enderecoCompleto: null,
        numeroEndereco: null,
        complemento: null,
        cep: null,
        descricao: null,
        idTipoCidade: null,
        numeroDocumento: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    alterarContatoEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/contatoService/alterarContatoEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroDocumentCNPJ: null,
        contato: null,
        idTipoContato: null,
        contatoAntigo: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: null,
          token: null,
        },
      },
    },
    inserirContatoEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/contatoService/inserirContatoEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroDocumentCNPJ: null,
        contato: null,
        idTipoContato: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: null,
          token: null,
        },
      },
    },
    consultarListaTipoContato: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarListaTipoContato",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {},
    },
    desvincularCargoFuncionario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/funcionarioService/desvincularCargoFuncionario",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        listaIdCargo: [],
        numeroDocumentoCNPJ: null,
        numeroDocumentoCPF: null,
      },
    },
    consultarCargoFuncionario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/funcionarioService/consultarCargoFuncionario",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCNPJ: null,
        numeroDocumentoCPF: null,
      },
    },
    adicionarCargoFuncionario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/funcionarioService/adicionarCargoFuncionario",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        listaIdCargo: [],
        numeroDocumentoCNPJ: null,
        numeroDocumentoCPF: null,
      },
    },
    consultarListaTipoCargo: {
      endPoint:
        this.configuration.ambient.endPoint + "/tipoService/consultarTipoCargo",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {},
    },
    consultaPerfilFuncionario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/pessoaService/consultarPessoaNatural",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
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
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {},
    },
    consultarPerfilEmpresa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/empresaService/consultarPerfilEmpresa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
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
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
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
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
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
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {},
    },
    recuperarSenhaUsuario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/userService/recuperarSenhaUsuario",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        email: null,
        numeroDocumentoCPF: null,
      },
    },
    gerenciarImagemPerfilPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/documentoService/gerenciarImagemPerfilPessoa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        extencao: null,
        contenType: null,
        base64: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    consultaIpDispositivo: {
      endPoint: "https://api64.ipify.org/?format=json",
      method: this.utilConstants.ID_TIPO.REQUEST.GET,
      json: null,
    },
    realizarCadastroBasico: {
      endPoint:
        this.configuration.ambient.endPoint + "/cadastroService/cadastroBasico",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        nomeCompleto: null,
        numeroDocumentoCPF: null,
        email: null,
      },
    },
    login: {
      endPoint: this.configuration.ambient.endPoint + "/loginService/login",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: null,
    },
    consultarInformacoesUsuario: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/userService/consultarInformacoesUsuario",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCPF: localStorage.getItem(
          this.utilConstants.SESSAO.USUARIO
        ),
        token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
      },
    },
    gerenciarPessoaNatural: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/pessoaService/gerenciarPessoaNatural",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroDocumentoCPF: null,
        nomeCompleto: null,
        dataNascimento: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    gerenciarContatoPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/contatoService/gerenciarContatoPessoa",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        numeroTelefone: null,
        numeroCelular: null,
        email: null,
        idPessoa: null,
        isWhatssap: null,
        loginDto: {
          ipDispositivo: null,
          usuarioAcesso: localStorage.getItem(
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
    consultarListaTipoEstado: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarListaTipoEstado",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {},
    },
    consultarListaTipoCidade: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/tipoService/consultarListaTipoCidade",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
      json: {
        idTipoEstado: null,
      },
    },
    gerenciarEnderecoPessoa: {
      endPoint:
        this.configuration.ambient.endPoint +
        "/enderecoService/gerenciarEnderecoPessoaNatural",
      method: this.utilConstants.ID_TIPO.REQUEST.POST,
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
            this.utilConstants.SESSAO.USUARIO
          ),
          token: localStorage.getItem(this.utilConstants.SESSAO.TOKEN),
        },
      },
    },
  };
}
