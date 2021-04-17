package pluginlogin.manager.signin;

import core.model.dto.MensagemDto;
import pluginlogin.model.dto.SignInFormDTO;
import pluginlogin.model.dto.SignInResponseDTO;

public interface SignInManager {

	/**
	 * Ativa o novo token <br>
	 * Criado em 10 de mar. de 2021 as  16:31:14 <br>
	 * @author Paulo Gomes Marques <br>
	 * @param token
	 * @param idUsuario
	 * @return
	 */
	MensagemDto ativarTokenNovoDispositivo(String token, Integer idUsuario);
	
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
