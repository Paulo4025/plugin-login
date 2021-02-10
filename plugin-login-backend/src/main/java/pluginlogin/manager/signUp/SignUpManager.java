package pluginlogin.manager.signUp;

import core.model.dto.MensagemDto;
import pluginlogin.model.dto.SignUpFormDto;

public interface SignUpManager {
	
	/**
	 * Realiza o cadastro basico <br>
	 * Dados Solicitados: Nome Completo, CPF e Email<br>
	 * Criado em 28 de jan de 2021 as  21:34:40 <br>
	 * @author Paulo Gomes Marques <br>
	 * @param signUpFormDto
	 * @return
	 */
	MensagemDto signUpBasic(SignUpFormDto signUpFormDto);

}
