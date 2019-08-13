//package org.openpaas.paasta.marketplace.web.user.common;
//
//import java.nio.charset.StandardCharsets;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.codehaus.jackson.map.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.Base64Utils;
//import org.springframework.web.client.RestTemplate;
//
//import lombok.extern.slf4j.Slf4j;
//
///**
// * Rest Template Service 클래스
// *
// * @author hrjin
// * @version 1.0
// * @since 2019.03.25
// */
//@Service
//@Slf4j
//public class RestTemplateService {
//
//    private final PropertyService property;
//
//    private final RestTemplate restTemplate;
//
////	@Autowired
//	public RestTemplateService(PropertyService property) {
//		this.property = property;
//		this.restTemplate = new RestTemplate();
//	}
//
//	public <T> T send(String targetApi, String restUrl, String token, HttpMethod httpMethod, Object bodyObject, Class<T> responseType) {
//		Map<String, String> requestMap = setApiUrlAuthorization(targetApi, token);
//		String apiFullUrl = requestMap.get("apiUrl") + restUrl;
//
//		HttpHeaders reqHeaders = new HttpHeaders();
//		if(UserConstants.TARGET_API_CF.equals(targetApi)) {
//			reqHeaders.add(UserConstants.CF_AUTHORIZATION_HEADER_KEY, requestMap.get("authorizationCf"));
//			reqHeaders.add(UserConstants.AUTHORIZATION_HEADER_KEY, requestMap.get("authorizationBasic"));
//		} else {
//			reqHeaders.add(UserConstants.AUTHORIZATION_HEADER_KEY, requestMap.get("authorizationBasic"));
//		}
//        reqHeaders.add(UserConstants.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
//
//        HttpEntity<Object> reqEntity = new HttpEntity<>(bodyObject, reqHeaders);
//        log.info("<T> T send :: Request : {} {} : {}, Content-Type: {}", httpMethod, apiFullUrl, restUrl, reqHeaders.get(UserConstants.CONTENT_TYPE));
//
//        try {
//			ResponseEntity<T> resEntity =  restTemplate.exchange(apiFullUrl, httpMethod, reqEntity, responseType);
//			if (resEntity.getBody() != null) {
//	            log.info("Response Type: {}", resEntity.getBody().getClass());
//	            log.info(resEntity.getBody().toString());
//	        } else {
//	            log.info("Response Type: {}", "response body is null");
//	        }
//
//			return resEntity.getBody();
//		} catch (Exception e) {
//			Map<String, Object> resultMap = new HashMap<String, Object>();
//	        resultMap.put("resultCode", "FAIL");
//	        resultMap.put("resultMessage", e.getMessage());
//	        ObjectMapper mapper = new ObjectMapper();
//
//	        log.error("Error resultMap : {}", resultMap);
//
//	        return mapper.convertValue(resultMap, responseType);
//		}
//	}
//
//	/**
//	 * 전송용 헤더 생성
//	 *
//	 * @param targetApi
//	 * @return
//	 */
//	private Map<String, String> setApiUrlAuthorization(String targetApi, String token) {
//        Map<String, String> requestMap = new HashMap<String, String>();
//
//        // Cf API
//        if (UserConstants.TARGET_API_CF.equals(targetApi)) {
////            requestMap.put("apiUrl", property.getCfJavaClientApiUri());
//            requestMap.put("authorizationCf", "bearer " + token);
////            requestMap.put("authorizationBasic", "Basic " + Base64Utils.encodeToString((property.getCfJavaClientApiUsername() + ":" + property.getCfJavaClientApiPassword()).getBytes(StandardCharsets.UTF_8)));
//        }
//
//        // Market API
//        if (UserConstants.TARGET_API_MARKET.equals(targetApi)) {
//            requestMap.put("apiUrl", property.getMarketApiUri());
//            requestMap.put("authorizationBasic", "Basic " + Base64Utils.encodeToString((property.getMarketApiUsername() + ":" + property.getMarketApiPassword()).getBytes(StandardCharsets.UTF_8)));
//        }
//
//        return requestMap;
//    }
//
//}