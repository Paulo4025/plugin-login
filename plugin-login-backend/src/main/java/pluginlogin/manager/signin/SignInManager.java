package pluginlogin.manager.signin;

import pluginlogin.model.dto.SignInFormDTO;
import pluginlogin.model.dto.SignInResponseDTO;

public interface SignInManager {

	/**
	 * Realiza a consulta para ver se o usuario esta registrado <br>
	 * Criado em 20 de fev. de 2021 as 00:08:04 <br>
	 * 
	 * @author Paulo Gomes Marques <br>
	 * @param signInFormDTO
	 * @return
	 */
	SignInResponseDTO signIn(SignInFormDTO signInFormDTO);

}
