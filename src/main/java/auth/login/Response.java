package auth.login;

public class Response {
	private String token;

	public String getToken() {
		return token;
	}

	public Response setToken(String token) {
		this.token = token;
		return this;
	}

	private String error;

	public String getError() {
		return error;
	}

	public Response setError(String error) {
		this.error = error;
		return this;
	}
}
