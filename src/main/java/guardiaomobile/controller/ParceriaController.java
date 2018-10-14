package guardiaomobile.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import guardiaomobile.enums.Role;
import guardiaomobile.model.Parceria;
import guardiaomobile.security.annotations.Secured;

@Resource
@Path("/parcerias")
public class ParceriaController {
	
	private final Result result;
	
	public ParceriaController(Result result) {
		this.result = result;
	}

	@Secured({Role.ADMIN})
	@Get("/")
	public void lista() {
		List<Parceria> parcerias = new ArrayList<>();
		
		Parceria p1 = new Parceria(1L, "Minds LTDA");
		Parceria p2 = new Parceria(2L, "Subway");
		Parceria p3 = new Parceria(3L, "CineSystem");
		
		parcerias.addAll(Arrays.asList(p1, p2, p3));
		
		result.use(Results.json()).withoutRoot().from(parcerias).serialize();
	}

}
