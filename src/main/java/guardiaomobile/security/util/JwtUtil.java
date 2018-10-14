package guardiaomobile.security.util;

import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import guardiaomobile.model.User;

public class JwtUtil {
	
	private static final String SECRET = "secret";
	
	public static String createToken(User user) throws JWTCreationException {
		int experiraEm = 60; // minutos
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		
		String token = JWT.create()
						.withSubject(user.getMatricula())
						.withArrayClaim("roles", user.getRolesAsArray())
						.withIssuedAt(new Date())
						.withExpiresAt(DateUtils.addMinutes(new Date(), experiraEm))
						.sign(algorithm);
		
		return token;
	}
	
	public static DecodedJWT decode(String token) throws JWTVerificationException {
		Algorithm algorithm = Algorithm.HMAC256(SECRET);
		JWTVerifier verifier = JWT.require(algorithm).build();
		DecodedJWT jwt = verifier.verify(token);
		return jwt;
	}

}
