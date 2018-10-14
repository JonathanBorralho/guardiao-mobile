package guardiaomobile.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWTVerifyException;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.Results;
import guardiaomobile.enums.Role;

@RequestScoped
@Intercepts
public class SecurityTokenInterceptor implements Interceptor {
	
	private final HttpServletRequest request;
	private final Result result;

	public SecurityTokenInterceptor(HttpServletRequest request, Result result) {
		this.request = request;
		this.result = result;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		String token = request.getHeader("authorization");
		Map<String, Object> claims;
		
		try {
			claims = JwtUtil.decode(token);
			
			@SuppressWarnings("unchecked")
			List<Role> roles = (List<Role>) claims.get("roles");
			
			// TODO: verificar se os Roles extraídos do token contém o papel no parâmetro da anotação no método
			
			result.use(Results.http()).addHeader("authorization", token);
			
			stack.next(method, resourceInstance);
			
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| IllegalStateException | SignatureException | IOException
				| JWTVerifyException e) {
			
			result.use(Results.http()).setStatusCode(401);
			result.use(Results.json()).from(e.getMessage(), "msg").serialize();
		}
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return method.getMethod().isAnnotationPresent(Secured.class);
	}

}
