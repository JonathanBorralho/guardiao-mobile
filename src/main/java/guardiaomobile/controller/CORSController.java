package guardiaomobile.controller;

import java.util.Set;

import br.com.caelum.vraptor.Options;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.RequestInfo;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.resource.HttpMethod;
import br.com.caelum.vraptor.view.Results;

@Resource
public class CORSController {
	
	private final Result result;
	private final Router router;
	private final RequestInfo requestInfo;

	public CORSController(Result result, Router router, RequestInfo requestInfo) {
		this.result			= result;
		this.router			= router;
		this.requestInfo	= requestInfo;
	}
	
	@Options @Path("/*")
	public void options() {
		Set<HttpMethod> allowed = router.allowedMethodsFor(requestInfo.getRequestedUri());
		
		result.use(Results.status()).header("Allow", allowed.toString().replaceAll("\\[|\\]", ""));        
		result.use(Results.status()).header("Access-Control-Allow-Origin", "*");        
		result.use(Results.status()).header("Access-Control-Allow-Methods", allowed.toString().replaceAll("\\[|\\]", ""));        
		result.use(Results.status()).header("Access-Control-Allow-Headers", "Content-Type, accept, authorization, origin");        
		result.use(Results.status()).noContent();
	}
}
