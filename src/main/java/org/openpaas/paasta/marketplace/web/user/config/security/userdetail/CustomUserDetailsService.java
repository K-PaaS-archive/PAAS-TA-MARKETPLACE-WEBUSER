package org.openpaas.paasta.marketplace.web.user.config.security.userdetail;

import org.openpaas.paasta.marketplace.web.user.security.SsoAuthenticationDetails;
import org.openpaas.paasta.marketplace.web.user.service.OrgService;
import org.openpaas.paasta.marketplace.web.user.service.QuotaService;
import org.openpaas.paasta.marketplace.web.user.service.SpaceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${market.place.org.name}")
    private String marketOrgName;

    @Value("${market.place.space.name}")
    private String marketSpaceName;

    @Value("${market.place.quota.name}")
    private String marketQuotaName;

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailsService.class);

    private String token;

    public void setToken(String token){
        this.token = token;
    }

    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;

    @Resource(name = "marketApiRest")
    RestTemplate marketApiRest;

    @Autowired
    public OrgService orgService;

    @Autowired
    public QuotaService quotaService;

    @Autowired
    public SpaceService spaceService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List role = new ArrayList();

        role.add(new SimpleGrantedAuthority("ROLE_ADMIN"));

        User user = new User("admin", "admin", role);
        return user;
    }

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

//        String space_guid = "";
//        String organization_guid = "";
//
//        String orgName = marketOrgName;
//        String spaceName = marketSpaceName;
//        String quotaName = marketQuotaName;
//        String quotaGuid;
//        String existQuotaGuid = "";
//
//
//        // 로그인 한 각자의 토큰을 interceptor 에 넣어준다.
//        cfJavaClientApiRest.getInterceptors().add(new UaaAccessTokenInterceptor(token));
//
//        if(username.equals("admin")) {
//            // 확인 후 없으면 org & space 생성 cf 호출. 생성 후 리턴으로 orgGuid & spaceGuid 전역변수로 추출할 것.
//            if (!orgService.isExistOrgByOrgName(orgName)) {
//                LOGGER.info("룰루~ org 가 없대여~~~~~~ 만들어야된대여~~~");
//
//                // Org 에 대한 Quota 는 최대치로 부여한다.
//                Quota quota = new Quota();
//                quota.setName(quotaName);
//                quota.setNonBasicServicesAllowed(true);
//                quota.setTotalServices(-1);
//                quota.setTotalRoutes(-1);
//                quota.setTotalReservedRoutePorts(-1);
//                quota.setMemoryLimit(102400);
//                quota.setInstanceMemoryLimit(-1);
//
//                Quota orgQuota = new Quota();
//
//                int count = 0;
//
//                // Todo ::: quota 유의사항 (CF V3 로 가게되면 Quota 가 사라짐...롸?)
//                QuotaList quotaList = quotaService.getOrgQuotas();
//                LOGGER.info("쿼타 리스트 ::: " + quotaList.toString());
//
//
//
//                for (Quota q : quotaList.getResources()) {
//                    // quota 이름이 같은 것이 있을 때
//                    if (quota.getName().equals(q.getEntity().get("name"))) {
//                        LOGGER.info("쿼타 여기 잉네!!! " + q.getMetadata());
//                        existQuotaGuid = (String) q.getMetadata().get("guid");
//                        count++;
//                    }
//                }
//
//                if (count > 0) {
//                    LOGGER.info("쿼타 안 만들어도 됨~~~");
//                    orgQuota.setOrgQuotaGuid(existQuotaGuid);
//                    LOGGER.info("쿼타 guid ::: " + orgQuota.getOrgQuotaGuid());
//
//                } else {
//                    orgQuota = quotaService.createOrgQuota(quota);
//                    LOGGER.info("쿼타 쿼타 ::: " + orgQuota.toString());
//                    quotaGuid = (String) orgQuota.getMetadata().get("id");
//                    orgQuota.setOrgQuotaGuid(quotaGuid);
//                }
//
//                Org org = new Org();
//                Space space = new Space();
//
//                // orgQuota 가 만들어졌으니 org 생성 준비!!!
//                org.setOrgName(orgName);
//                org.setQuotaGuid(orgQuota.getOrgQuotaGuid());
//
//                // org 생성
//                //Org createdOrg = orgService.createOrg(org);
//                Map createdOrg = orgService.createOrgV2(org);
//
//                LOGGER.info("내가 알게된 첫번째~~~ " + createdOrg.toString());
//
//                // Org List 가져온다. 해당 이름 있는 Org 골라서 거기의 orgGuid 를 가져와서 아래 로직을 처리한다.
//                ListOrganizationsResponse orgList = orgService.getOrgsListV3();
//
//                for (OrganizationResource o : orgList.getResources()){
//                    // space 이름이 같은 것이 있을 때 space guid 를 부여한다.
//                    if (org.getOrgName().equals(o.getName())) {
//                        organization_guid = o.getId();
//                    }
//                }
//
//                System.out.println("orgGuid :::::::: " + organization_guid);
//
//
//                if(organization_guid != null && !organization_guid.equals("")){
//                    // CF API 에서 필요로 하는 임시 guid
//                    space.setGuid(UUID.randomUUID());
//                    space.setSpaceName(spaceName);
//                    space.setOrgGuid(organization_guid);
//                    space.setUserId(uaaid);
//
//                    // space 생성
//                    Map createdSpace = spaceService.createSpaceV2(space);
//                    LOGGER.info("내가 알게된 두번째~~~ " + createdSpace.toString());
//
//                    ListSpacesResponse spaceList = spaceService.getSpacesListV3(organization_guid);
//
//                    for (SpaceResource s : spaceList.getResources()){
//                        // space 이름이 같은 것이 있을 때 space guid 를 부여한다.
//                        if (space.getSpaceName().equals(s.getName())) {
//                            space_guid = s.getId();
//                        }
//                    }
//                    System.out.println("space_guid :::::::: " + space_guid);
//                }else{
//                    LOGGER.info("org guid 만들고 와라잉~~~~~~~~~~");
//                }
//            } else {
//                LOGGER.info("org 있대여~~~~~~~~~~");
//            }
//        }
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
