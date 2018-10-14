package guardiaomobile.security;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.HttpMethod;
import br.com.caelum.vraptor.resource.ResourceMethod;

@RequestScoped
@Intercepts
public class CORSInterceptor implements Interceptor {
	
	private final HttpServletResponse response;
	private final Router router;
	private final RequestInfo requestInfo;
	
	public CORSInterceptor(HttpServletResponse response, Router router, RequestInfo requestInfo) {
		this.response = response;
		this.router = router;
		this.requestInfo = requestInfo;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance) throws InterceptionException {
		Set<HttpMethod> allowed = router.allowedMethodsFor(requestInfo.getRequestedUri());
		
		response.setHeader("Allow", allowed.toString().replaceAll("\\[|\\]", ""));
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", allowed.toString().replaceAll("\\[|\\]", ""));
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, accept, authorization, origin");
		
		stack.next(method, resourceInstance);
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return !method.containsAnnotation(Options.class);
	}

}
