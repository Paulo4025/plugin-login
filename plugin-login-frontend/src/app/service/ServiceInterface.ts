export interface ServiceInterface {
    realizarCadastroBasico: ServiceRequest
    login: ServiceRequest
    consultarInformacoesUsuario: ServiceRequest;
    gerenciarPessoaNatural: ServiceRequest;
    gerenciarContatoPessoa: ServiceRequest;
    consultarListaTipoEstado: ServiceRequest;
    consultarListaTipoCidade: ServiceRequest;
    gerenciarEnderecoPessoa: ServiceRequest;
}
export interface ServiceRequest {
    endPoint: string;
    method: number;
    json: any;
}