package guardiaomobile.controller;

import java.util.ArrayList;
import java.util.List;

import com.auth0.jwt.exceptions.JWTCreationException;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import static br.com.caelum.vraptor.view.Results.*;
import guardiaomobile.enums.Role;
import guardiaomobile.model.User;
import guardiaomobile.security.annotations.Public;
import guardiaomobile.security.util.ErrorMessage;
import guardiaomobile.security.util.JwtUtil;

@Resource
@Path("/login")
public class LoginController {
	
	private final Result result;
	
	public LoginController(Result result) {
		this.result = result;
	}
	
	@Public
	@Consumes("application/json")
	@Post("/")
	public void login(User user) {
		
		// verifica se usuario e senhas conferem
		
		// se sim, cria token jwt
		
		List<Role> roles = new ArrayList<>();
		roles.add(Role.ADMIN);
		
		user.setNome("Jonathan Sousa Rosendo");
		user.setRoles(roles);
		
		String token = null;
		
		try {
			token = JwtUtil.createToken(user);
		} catch (JWTCreationException e) {
			result.use(http()).setStatusCode(500);
			result.use(json()).from(new ErrorMessage("erro no servidor", e.getClass().getSimpleName(), 401), "error").serialize();
			return;
		}
		
		result.use(json()).from(token, "token").serialize();
	}
	
}
