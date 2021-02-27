package pluginlogin.model.dto;

import core.model.dto.MensagemDto;

public class SignInResponseDTO {

	private String token;
	private String numeroDocumentoCPF;
	private MensagemDto mensagemDto;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getNumeroDocumentoCPF() {
		return numeroDocumentoCPF;
	}

	public void setNumeroDocumentoCPF(String numeroDocumentoCPF) {
		this.numeroDocumentoCPF = numeroDocumentoCPF;
	}

	public MensagemDto getMensagemDto() {
		return mensagemDto;
	}

	public void setMensagemDto(MensagemDto mensagemDto) {
		this.mensagemDto = mensagemDto;
	}

}
