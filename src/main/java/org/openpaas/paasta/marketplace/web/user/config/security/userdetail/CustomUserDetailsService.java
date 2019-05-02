package org.openpaas.paasta.marketplace.web.user.config.security.userdetail;

import org.openpaas.paasta.marketplace.web.user.security.SsoAuthenticationDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CustomUserDetails service 클래스.
 *
 * @author hrjin
 * @version 1.0
 * @since 2019.03.22
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private String token;

    public void setToken(String token){
        this.token = token;
    }

    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;

    @Resource(name = "marketApiRest")
    RestTemplate marketApiRest;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List role = new ArrayList();

        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User("admin", "admin", role);
        return user;
    }

    // TODO ::: 로그인 부분 추후 상세 수정.(WEB-USER)
    public UserDetails loadUserBySsoAuthenticationDetails(SsoAuthenticationDetails ssoAuthenticationDetails) {
        List role = new ArrayList();

        LOGGER.info("username : "+ssoAuthenticationDetails.getUserId());
        LOGGER.info("uaaId : "+ssoAuthenticationDetails.getId());
        LOGGER.info("token : "+ssoAuthenticationDetails.getAccessToken());
        LOGGER.info("Instance id : "+ssoAuthenticationDetails.getManagingServiceInstance());

        String username = ssoAuthenticationDetails.getUserId();
        String uaaid = ssoAuthenticationDetails.getId();
        String token = ssoAuthenticationDetails.getAccessToken().toString();
        //String serviceInstanceId = ssoAuthenticationDetails.getManagingServiceInstance();


        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User(username, "N/A", role);
        //user.setServiceInstanceId(ssoAuthenticationDetails.getManagingServiceInstance());
        //user.setOrganizationGuid(organization_guid);
        //user.setSpaceGuid(space_guid);
        return user;
    }


    public UserDetails loginByUsernameAndPassword(String username, String password) throws UsernameNotFoundException {

        Map<String, Object> resBody = new HashMap();

        resBody.put("id", username);
        resBody.put("password", password);
        Map result;


        try {
            result = cfJavaClientApiRest.postForObject("/login", resBody, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadCredentialsException(e.getMessage());
        }
        Map info = new HashMap();
        try {
            info = marketApiRest.getForObject("/v2/user/" + username, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map userInfo = (Map) info.get("User");

        List role = new ArrayList();
        if (userInfo.get("adminYn").toString().equals("Y")) {
            role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            role.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        User user = new User((String) result.get("id"), (String) result.get("password"), role);

        user.setToken((String) result.get("token"));
        user.setExpireDate((Long) result.get("expireDate"));
        user.setName((String) userInfo.get("name"));
        user.setImgPath((String) userInfo.get("imgPath"));

        return user;
    }

}
