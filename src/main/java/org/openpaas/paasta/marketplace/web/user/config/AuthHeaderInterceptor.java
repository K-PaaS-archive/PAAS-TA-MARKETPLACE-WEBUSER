package org.openpaas.paasta.marketplace.web.user.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Basic Oauth2 Token 을 header 에 넣어주는 Interceptor
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-04-09
 */
public class AuthHeaderInterceptor implements ClientHttpRequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthHeaderInterceptor.class);

    private String apiUsername;
    private String apiPassword;
    private String authTokenHeaderName;
    private static final String CONTENT_TYPE = "Content-Type";


    public AuthHeaderInterceptor(String authTokenHeaderName, String apiUsername, String apiPassword) {
        logger.info("AuthHeaderInterceptor: init");
        this.authTokenHeaderName = authTokenHeaderName;
        this.apiUsername = apiUsername;
        this.apiPassword = apiPassword;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        String basic64AuthToken = makeBasic64Authorization(apiUsername, apiPassword);
        request.getHeaders().set(authTokenHeaderName, basic64AuthToken);
        request.getHeaders().set(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        return execution.execute(request, body);
    }

    private String makeBasic64Authorization(String userName, String password){
        return "Basic " + Base64Utils.encodeToString((userName + ":" + password).getBytes(StandardCharsets.UTF_8));
    }

}
