package org.openpaas.paasta.marketplace.web.user.service;

import org.cloudfoundry.client.v3.spaces.ListSpacesResponse;

import org.openpaas.paasta.marketplace.web.user.common.Common;
import org.openpaas.paasta.marketplace.web.user.model.Space;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Space Service
 *
 * @author hrjin
 * @version 1.0
 * @since 2019-04-03
 */
@Service
public class SpaceService extends Common {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpaceService.class);

//    @Autowired
//    RestTemplateService restTemplateService;

    @Resource(name = "cfJavaClientApiRest")
    RestTemplate cfJavaClientApiRest;


    /**
     * Space 생성 V3
     *
     * @param spaceName the space name
     * @return Space
     */
    public Space createSpaceV3(String spaceName, String orgGuid) {
        //return marketApiRest.postForObject("/spaces", space, Space.class);
        //return restTemplateService.send("/spaces", getToken(), HttpMethod.POST, space, Map.class);
        Map map = new HashMap();
        map.put("name", spaceName);
        map.put("guid", orgGuid);

        return cfJavaClientApiRest.postForObject("/v3/spaces", map, Space.class);
    }


    /**
     * Space 생성 V2
     *
     * @param space the space
     * @return Map
     */
    public Map createSpaceV2(Space space) {
        //return marketApiRest.postForObject("/spaces", space, Space.class);
        //return restTemplateService.send("/spaces", getToken(), HttpMethod.POST, space, Map.class);
        return cfJavaClientApiRest.postForObject("/v2/spaces", space, Map.class);
    }


    /**
     * 해당 Org 에 속한 Space 목록 조회 V3
     *
     * @param orgGuid the org guid
     * @return ListSpacesResponse
     */
    public ListSpacesResponse getSpacesListV3(String orgGuid) {
        return cfJavaClientApiRest.getForObject("/v3/spaces-admin/" + orgGuid, ListSpacesResponse.class);
    }
}
