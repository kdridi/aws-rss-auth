package auth.login;

import java.util.Collections;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.google.gson.Gson;

public class Handler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private static final Logger LOG = LogManager.getLogger(Handler.class);

	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> input, Context context) {
		LOG.info("received: {}", input);

		final HandlerBody body = new Gson().fromJson(String.class.cast(input.get("body")), HandlerBody.class);
		if (body.user.equals(body.pass)) {
			Algorithm algorithm = Algorithm.HMAC256("secret");
			String token = JWT.create().withIssuer("auth0").withClaim("user", body.user).sign(algorithm);

			return ApiGatewayResponse.builder().setStatusCode(200).setObjectBody(new Response().setToken(token))
					.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*")).build();
		}

		return ApiGatewayResponse.builder().setStatusCode(401).setObjectBody(new Response().setError("BAD CREDENTIALS"))
				.setHeaders(Collections.singletonMap("Access-Control-Allow-Origin", "*")).build();
	}
}
