package pluginlogin.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import core.model.dto.MensagemDto;
import pluginlogin.manager.signUp.impl.SignUpManagerImpl;
import pluginlogin.model.dto.SignUpFormDto;

@Path("/pluginSignUpService")
public class SignUpService {

	@POST
	@Path("/signUpBasic")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public MensagemDto signUpFormBasic(SignUpFormDto signUpFormDto) throws ClassNotFoundException, Exception, NullPointerException {
		return new SignUpManagerImpl().signUpBasic(signUpFormDto);
	}

}
