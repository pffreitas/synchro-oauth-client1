package br.com.synchro.oauth.client

import grails.converters.JSON

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.ClientParametersAuthentication
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.MemoryCredentialStore
import com.google.api.client.http.BasicAuthentication
import com.google.api.client.http.GenericUrl
import com.google.api.client.http.HttpRequest
import com.google.api.client.http.HttpRequestFactory
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.http.HttpResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonObjectParser
import com.google.api.client.json.jackson2.JacksonFactory

class LoginWSycnhroIDController {

	/** Token Endpoint */
	private static final String TOKEN_SERVER_URL = "http://localhost:8080/synchro-oauth-provider/OAuth2/token"

	/** Auth Endpoint */
	private static final String AUTHORIZATION_SERVER_URL = "http://localhost:8080/synchro-oauth-provider/OAuth2/auth"

	/** Value of the "API Key". */
	public static final String CLIENT_ID = "12345.apps.synchroapis.com.br"

	/** Value of the "API Secret". */
	public static final String CLIENT_SECRET = "DGWV34bJGneEsD9mnOv_eCVo"

	/** Scopes */
	private static final String SCOPE = "openid profile";

	def index() {
	}

	def auth = {
		AuthorizationCodeFlow codeFlow = buildFlow();

		def redirectUri = createLink(action: "receiveCode", absolute:true)
		def uri = codeFlow.newAuthorizationUrl().setRedirectUri(redirectUri).build();
		redirect(uri: uri)
	}

	def receiveCode = {
		AuthorizationCodeFlow codeFlow = buildFlow();
		
		def redirectUri = createLink(action: "receiveCode", absolute:true)
		def tk = codeFlow.newTokenRequest(params.code).setClientAuthentication(new BasicAuthentication(CLIENT_ID, CLIENT_SECRET)).setRedirectUri(redirectUri).execute();

		session.credential = codeFlow.createAndStoreCredential(tk, "pfreitas");

		redirect(action: "userInfo")
	}


	def userInfo = {
		def httpTransport = new NetHttpTransport()
		def jacksonFactory = new JacksonFactory()
		def  Credential credential = session.credential

		if(credential){

			HttpRequestFactory requestFactory = httpTransport.createRequestFactory(new HttpRequestInitializer() {
						public void initialize(HttpRequest request) throws IOException {
							credential.initialize(request);
							request.setParser(new JsonObjectParser(jacksonFactory));
						}
					});

			HttpRequest apiRq = requestFactory.buildGetRequest(new GenericUrl("http://localhost:8080/synchro-oauth-provider/api/OAuth2/userInfo"));
			HttpResponse apiRs = apiRq.execute();

			def json = JSON.parse(apiRs.parseAsString())

			render (view: "userData", model: [user: json])
		}else{
			redirect(view: "auth")
		}
	}


	private AuthorizationCodeFlow buildFlow() {
		AuthorizationCodeFlow codeFlow = new AuthorizationCodeFlow.Builder(
				BearerToken.authorizationHeaderAccessMethod(), new NetHttpTransport(), new JacksonFactory(), new GenericUrl(
				TOKEN_SERVER_URL), new ClientParametersAuthentication(CLIENT_ID, CLIENT_SECRET), CLIENT_ID,
				AUTHORIZATION_SERVER_URL).setScopes(Arrays.asList(SCOPE))
				.setCredentialStore(new MemoryCredentialStore()).build()
		return codeFlow
	}
}
