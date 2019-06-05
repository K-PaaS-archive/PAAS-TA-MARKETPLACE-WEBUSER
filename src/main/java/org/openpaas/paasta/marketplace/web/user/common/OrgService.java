package org.openpaas.paasta.marketplace.web.user.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Org 서비스
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-03-27
 */
@Service
public class OrgService {

    @Autowired
    private RestTemplateService cfApiRest;


    /**
     * 조직명 중복검사를 실행한다.
     *
     * @param orgName the org name
     * @return boolean
     *
     * 권한 : 사용자
     */
    public boolean isExistOrgByOrgName(String orgName, String token) {
        return cfApiRest.send(UserConstants.TARGET_API_CF, "/v3/orgs/" + orgName + "/exist", token, HttpMethod.GET, null, Boolean.class);
    }

//
//
//    /**
//     * Org 생성 (-> 쿼터 지정 부분이 있음.)
//     *
//     * @param org the org
//     * @return Map
//     */
//    public Map createOrg(Org org, String token){
//        return marketApiRest.send(AdminConstants.TARGET_API_CF, "/v3/orgs", token, HttpMethod.POST, org, Map.class);
//    }
//
//    /**
//     * 관리자 권한으로 Org 목록 조회
//     *
//     * @return ListOrganizationsResponse
//     */
//    public ListOrganizationsResponse getOrgsList() {
//        return marketApiRest.send(AdminConstants.TARGET_API_CF, "/v3/orgs-admin", null, HttpMethod.GET, null, ListOrganizationsResponse.class);
//    }
}
