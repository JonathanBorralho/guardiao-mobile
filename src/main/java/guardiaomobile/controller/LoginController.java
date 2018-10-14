package guardiaomobile.controller;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import guardiaomobile.model.User;
import guardiaomobile.security.JwtUtil;
import guardiaomobile.security.Public;

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
		
		// se sim, cria token jwt e retorna
		
		// user.setRole("ADMIN");
		
		String token = JwtUtil.createToken(user);
		
		result.use(Results.json()).from(token, "token").serialize();
	}
	
}
