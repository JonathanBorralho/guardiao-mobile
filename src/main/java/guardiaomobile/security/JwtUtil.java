package guardiaomobile.security;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import guardiaomobile.enums.Role;
import guardiaomobile.model.User;

public class JwtUtil {
	
	private static final String SECRET = "secret";
	
	public static String createToken(User user) {
		JWTSigner signer = new JWTSigner(SECRET);

		Map<String, Object> claims = new HashMap<String, Object>();

		claims.put("user", user.getMatricula());
		claims.put("roles", Arrays.asList(Role.ADMIN, Role.USER));
		
		String token = signer.sign(claims, new JWTSigner.Options()
				.setExpirySeconds(60*60*24*365*30).setIssuedAt(true));

		return token;

	}
	
	public static Map<String, Object> decode(String token)
			throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, SignatureException, IOException,
			JWTVerifyException {
		JWTVerifier verifier = new JWTVerifier(SECRET);

		Map<String, Object> map = verifier.verify(token);

		return map;
	}

}
