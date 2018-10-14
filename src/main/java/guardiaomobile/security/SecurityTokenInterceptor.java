package guardiaomobile.security;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import static br.com.caelum.vraptor.view.Results.*;
import guardiaomobile.enums.Role;
import guardiaomobile.security.annotations.Secured;
import guardiaomobile.security.util.ErrorMessage;
import guardiaomobile.security.util.JwtUtil;

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
		String token = request.getHeader("Authorization");
		
		try {
			DecodedJWT jwt = JwtUtil.decode(token);
			
			if (!isAuthorized(jwt, method)) {
				result.use(http()).setStatusCode(401);
				result.use(json()).from("não autorizado", "msg").serialize();
				return;
			}
			
			stack.next(method, resourceInstance);
		} catch (TokenExpiredException e) {
			result.use(http()).setStatusCode(401);
			result.use(json())
				.from(new ErrorMessage("token expirado", e.getClass().getSimpleName(), 401), "error").serialize();
		} catch (JWTVerificationException e) {
			result.use(http()).setStatusCode(401);
			result.use(json())
				.from(new ErrorMessage("erro ao verificar assinatura do token", e.getClass().getSimpleName(), 401), "error").serialize();
		}
		
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return method.getMethod().isAnnotationPresent(Secured.class);
	}
	
	private boolean isAuthorized(DecodedJWT jwt, ResourceMethod method) {
		List<Role> userRoles = jwt.getClaim("roles").asList(Role.class);
		Secured s = method.getMethod().getAnnotation(Secured.class);
		
		Role[] rolesMethod = s.value();
		return isUserRolesIn(userRoles, rolesMethod);
	}
	
	private boolean isUserRolesIn(List<Role> userRoles, Role[] rolesMethod) {
		for (int i = 0; i < rolesMethod.length; i++) {
			if (userRoles.contains(rolesMethod[i])) {
				return true;
			}
		}
		return false;
	}

}
