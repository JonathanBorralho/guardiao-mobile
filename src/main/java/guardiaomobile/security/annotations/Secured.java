package guardiaomobile.security.annotations;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import guardiaomobile.enums.Role;

@Retention(RUNTIME)
@Target({ TYPE, METHOD })
public @interface Secured {
	
	Role[] value();

}
