package org.openpaas.paasta.marketplace.web.user.common;//package org.openpaas.paasta.marketplace.web.common;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.*;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Base64Utils;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.lang.reflect.Method;
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Rest Template Service 클래스
// *
// * @author hrjin
// * @version 1.0
// * @since 2019.03.25
// */
//@Service
//public class RestTemplateService extends Common{
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);
//    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
//    private static final String CF_AUTHORIZATION_HEADER_KEY = "cf-Authorization";
//    private static final String CONTENT_TYPE = "Content-Type";
//    private final String marketApiUri;
//    private final String marketApiBase64Authorization;
//    private final RestTemplate restTemplate;
//    private String base64Authorization;
//
//    @Autowired
//    public RestTemplateService(@Value("${portal.market.api.uri}") String marketApiUri,
//                               @Value("${portal.market.api.authorization.username}") String marketApiAuthorizationId,
//                               @Value("${portal.market.api.authorization.password}") String marketApiAuthorizationPassword,
//                               RestTemplate restTemplate) {
//        this.marketApiUri = marketApiUri;
//        this.restTemplate = restTemplate;
//        marketApiBase64Authorization = "Basic "
//                + Base64Utils.encodeToString(
//                (marketApiAuthorizationId + ":" + marketApiAuthorizationPassword).getBytes(StandardCharsets.UTF_8));
//    }
//
//
//    /**
//     * Send t.
//     *
//     * @param <T>          the type parameter
//     * @param reqUrl       the req url
//     * @param httpMethod   the http method
//     * @param bodyObject   the body object
//     * @param responseType the response type
//     * @return the t
//     */
//    public <T> T send(String reqUrl, String token, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
//
//        HttpHeaders reqHeaders = new HttpHeaders();
//        reqHeaders.add(AUTHORIZATION_HEADER_KEY, marketApiBase64Authorization);
//        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        if(token != null && !token.equals("")){
//            reqHeaders.add(CF_AUTHORIZATION_HEADER_KEY, token);
//        }
//
//        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
//
//        LOGGER.info("<T> T send :: Request : {} {} : {}, Content-Type: {}", httpMethod, marketApiUri, reqUrl, reqHeaders.get(CONTENT_TYPE));
//
//        try {
//            ResponseEntity<T> resEntity = restTemplate.exchange(marketApiUri + reqUrl, httpMethod, reqEntity, responseType);
//            if (resEntity.getBody() != null) {
//                LOGGER.info("Response Type: {}", resEntity.getBody().getClass());
//                LOGGER.info(resEntity.getBody().toString());
//            } else {
//                LOGGER.info("Response Type: {}", "response body is null");
//            }
//
//            return resEntity.getBody();
//        } catch (Exception e) {
//            Map<String, Object> resultMap = new HashMap();
//            resultMap.put("resultCode", "FAIL");
//            resultMap.put("resultMessage", e.getMessage());
//            ObjectMapper mapper = new ObjectMapper();
//
//            LOGGER.error("Error resultMap : {}", resultMap);
//
//            return mapper.convertValue(resultMap, responseType);
//        }
//    }
//
//    /**
//     * REST TEMPLATE 처리
//     *
//     * @param reqUrl     the req url
//     * @param httpMethod the http method
//     * @param bodyObject  the bodyObject
//     * @param token   the token
//     * @return map map
//     */
//    public Map<String, Object> procRestTemplate(String reqUrl, String token, HttpMethod httpMethod, Object bodyObject) {
//
//        HttpHeaders reqHeaders = new HttpHeaders();
//        reqHeaders.add(AUTHORIZATION_HEADER_KEY, marketApiBase64Authorization);
//        reqHeaders.add(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        if (null != token && !"".equals(token)) reqHeaders.add(CF_AUTHORIZATION_HEADER_KEY, "bearer " + token);
//
//        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
//
//        ResponseEntity<Map> resEntity = restTemplate.exchange(marketApiUri + reqUrl, httpMethod, reqEntity, Map.class);
//        Map<String, Object> resultMap = resEntity.getBody();
//
//        LOGGER.info("procRestTemplate reqUrl :: {} || resultMap :: {}", reqUrl, resultMap.toString());
//        return resultMap;
//    }
//
//
//    /**
//     * REST TEMPLATE 처리
//     *
//     * @param reqUrl   the req url
//     * @param file     the file
//     * @param reqToken the req token
//     * @return map map
//     * @throws Exception the exception
//     */
//    public Map<String, Object> procRestTemplate(String reqUrl, MultipartFile file, String reqToken) throws Exception {
//        HttpHeaders reqHeaders = new HttpHeaders();
//        reqHeaders.add(AUTHORIZATION_HEADER_KEY, base64Authorization);
//        if (null != reqToken && !"".equals(reqToken)) reqHeaders.add(CF_AUTHORIZATION_HEADER_KEY, reqToken);
//
//        final MultiValueMap<String, Object> data = new LinkedMultiValueMap<String, Object>();
//
//        ByteArrayResource resource = new ByteArrayResource(file.getBytes()) {
//            @Override
//            public String getFilename() throws IllegalStateException {
//                return file.getOriginalFilename();
//            }
//        };
//
//        data.add("file", resource);
//        final HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(data, reqHeaders);
//        final ResponseEntity<Map> resEntity = restTemplate.exchange(apiUrl + reqUrl, HttpMethod.POST, requestEntity, Map.class);
//
//        Map resultMap = resEntity.getBody();
//
//        LOGGER.info("procRestTemplate resultMap :: {}", resultMap.toString());
//        return resultMap;
//    }
//
//
//    /**
//     * REST TEMPLATE 처리
//     *
//     * @param <T>          the type parameter
//     * @param reqUrl       the req url
//     * @param httpMethod   the http method
//     * @param obj          the obj
//     * @param reqToken     the req token
//     * @param responseType the response type
//     * @return response entity
//     */
//    public <T> ResponseEntity<T> procRestTemplate(String reqUrl, HttpMethod httpMethod, Object obj, String reqToken, Class<T> responseType) {
//
//        HttpHeaders reqHeaders = new HttpHeaders();
//        reqHeaders.add(AUTHORIZATION_HEADER_KEY, base64Authorization);
//
//        if (null != reqToken && !"".equals(reqToken)) reqHeaders.add(CF_AUTHORIZATION_HEADER_KEY, reqToken);
//
//        HttpEntity<Object> reqEntity = new HttpEntity<>(obj, reqHeaders);
//        ResponseEntity<T> result = restTemplate.exchange(marketApiUri + reqUrl, httpMethod, reqEntity, responseType);
//
//        //LOGGER.info("procRestTemplate reqUrl :: {} || resultBody :: {}", reqUrl, result.getBody().toString());
//
//        return result;
//    }
//
//
//
//    /**
//     * USER ID를 조회한다.
//     *
//     * @return user id
//     */
//    public String getUserId() {
//        return SecurityContextHolder.getContext().getAuthentication().getName();
//    }
//
//
//    /**
//     * USER ID를 설정한다.
//     *
//     * @param classObject the class object
//     * @return the object
//     * @throws Exception the exception
//     */
//    public Object setUserId(Object classObject) throws Exception {
//        String methodName = "setUserId";
//        Method method = classObject.getClass().getMethod(methodName, String.class);
//        Object[] paramObject = new Object[]{this.getUserId()};
//
//        method.invoke(classObject, paramObject);
//
//        return classObject;
//    }
//}
