export interface ServiceInterface {
  gerenciarImagemPerfilPessoa: ServiceRequest;
  consultaIpDispositivo: ServiceRequest;
  realizarCadastroBasico: ServiceRequest;
  login: ServiceRequest;
  consultarInformacoesUsuario: ServiceRequest;
  gerenciarPessoaNatural: ServiceRequest;
  gerenciarContatoPessoa: ServiceRequest;
  consultarListaTipoEstado: ServiceRequest;
  consultarListaTipoCidade: ServiceRequest;
  gerenciarEnderecoPessoa: ServiceRequest;
  recuperarSenhaUsuario: ServiceRequest;
  consultarListaTipoUsuarioLoja: ServiceRequest;
  alterarDadosUsuario: ServiceRequest;
  preRegistrarEmpresa: ServiceRequest;
  consultarPerfilEmpresa: ServiceRequest;
  consultarListaTipoEmpresa: ServiceRequest;
  consultaPerfilFuncionario: ServiceRequest;
  consultarListaTipoCargo: ServiceRequest;
}
export interface ServiceRequest {
  endPoint: string;
  method: number;
  json: any;
}
