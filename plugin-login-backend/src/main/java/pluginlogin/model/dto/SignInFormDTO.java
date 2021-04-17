package pluginlogin.model.dto;

public class SignInFormDTO {

	private String numeroDocumentoCPF;
	private String senha;
	private String ipDispositivo;

	public String getIpDispositivo() {
		return ipDispositivo;
	}

	public void setIpDispositivo(String ipDispositivo) {
		this.ipDispositivo = ipDispositivo;
	}

	public String getNumeroDocumentoCPF() {
		return numeroDocumentoCPF;
	}

	public void setNumeroDocumentoCPF(String numeroDocumentoCPF) {
		this.numeroDocumentoCPF = numeroDocumentoCPF;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
