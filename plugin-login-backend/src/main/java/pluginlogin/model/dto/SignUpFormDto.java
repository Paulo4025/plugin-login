package pluginlogin.model.dto;

public class SignUpFormDto {

	private String nomeCompleto;
	private String numeroDocumentoCPF;
	private String email;
	private String celular;

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public String getNumeroDocumentoCPF() {
		return numeroDocumentoCPF;
	}

	public void setNumeroDocumentoCPF(String numeroDocumentoCPF) {
		this.numeroDocumentoCPF = numeroDocumentoCPF;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
