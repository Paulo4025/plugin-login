import { Injectable } from "@angular/core";
import { Configuration } from "configuration/ambient/configuration";
import { UtilConstant } from "plugins/plugin-util-common/util-common-frontend/src/app/util-constants";
import { ServiceInterface } from "./ServiceInterface";

@Injectable()
export class ServiceConfig {

    constructor(private utilConstant: UtilConstant, private configuration: Configuration) { }

    public service: ServiceInterface = {
        realizarCadastroBasico: {
            endPoint: this.configuration.ambient.endPoint + "/cadastroService/cadastroBasico",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: null
        },
        login: {
            endPoint: this.configuration.ambient.endPoint + "/loginService/login",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: null
        },
        consultarInformacoesUsuario: {
            endPoint: this.configuration.ambient.endPoint + "/userService/consultarInformacoesUsuario",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: {
                numeroDocumentoCPF: localStorage.getItem(this.utilConstant.constants.sessao.usuario),
                token: localStorage.getItem(this.utilConstant.constants.sessao.token),
            }
        },
        gerenciarPessoaNatural: {
            endPoint: this.configuration.ambient.endPoint + "/pessoaService/gerenciarPessoaNatural",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: {
                numeroDocumentoCPF: null,
                nomeCompleto: null,
                dataNascimento: null,
                loginDto: {
                    usuarioAcesso: localStorage.getItem(this.utilConstant.constants.sessao.usuario),
                    token: localStorage.getItem(this.utilConstant.constants.sessao.token),
                }
            }
        },
        gerenciarContatoPessoa: {
            endPoint: this.configuration.ambient.endPoint + "/contatoService/gerenciarContatoPessoa",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: {
                numeroTelefone: null,
                numeroCelular: null,
                email: null,
                idPessoa: null,
                isWhatssap: null,
                loginDto: {
                    usuarioAcesso: localStorage.getItem(this.utilConstant.constants.sessao.usuario),
                    token: localStorage.getItem(this.utilConstant.constants.sessao.token),
                }
            }
        },
        consultarListaTipoEstado: {
            endPoint: this.configuration.ambient.endPoint + "/tipoService/consultarListaTipoEstado",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: {}
        },
        consultarListaTipoCidade: {
            endPoint: this.configuration.ambient.endPoint + "/tipoService/consultarListaTipoCidade",
            method: this.utilConstant.constants.TIPO.REQUEST.POST,
            json: {
                idTipoEstado: null
            }
        },
        gerenciarEnderecoPessoa: {
            endPoint: this.configuration.ambient.endPoint + "/enderecoService/gerenciarEnderecoPessoaNatural",
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
                    usuarioAcesso: localStorage.getItem(this.utilConstant.constants.sessao.usuario),
                    token: localStorage.getItem(this.utilConstant.constants.sessao.token),
                }
            }
        }

    }
}