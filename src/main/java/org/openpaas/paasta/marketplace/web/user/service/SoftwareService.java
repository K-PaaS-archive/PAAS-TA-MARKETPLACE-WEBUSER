package org.openpaas.paasta.marketplace.web.user.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.openpaas.paasta.marketplace.api.domain.Category;
import org.openpaas.paasta.marketplace.api.domain.CustomPage;
import org.openpaas.paasta.marketplace.api.domain.Software;
import org.openpaas.paasta.marketplace.api.domain.SoftwarePlan;
import org.openpaas.paasta.marketplace.api.domain.SoftwareSpecification;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SoftwareService {

    private final RestTemplate paasApiRest;

    @SneakyThrows
    public void createSoftware(Software software) {
        Object response = paasApiRest.postForObject("http://localhost:8777/softwares", software, Void.class);
    }

    @SneakyThrows
    public List<Category> getCategories() {
        return paasApiRest.getForObject("/categories", List.class);
    }

    //Page
    public CustomPage<Software> getSoftwareList(String queryParamString) {
        ResponseEntity<CustomPage<Software>> responseEntity = paasApiRest.exchange("/softwares/page" + queryParamString, HttpMethod.GET, null, new ParameterizedTypeReference<CustomPage<Software>>() {});
        CustomPage<Software> customPage = responseEntity.getBody();
        Page<Software> page = customPage.toPage();

        System.out.println("getContent ::: " + customPage.getContent());
        System.out.println("getTotalElements ::: " + customPage.getTotalElements());
        return customPage;
    }


    public Software getSoftware(Long id) {
        String url = UriComponentsBuilder.newInstance().path("/softwares/{id}")
                .build()
                .expand(id)
                .toString();

        return paasApiRest.getForObject(url, Software.class);
    }
    
    public List<SoftwarePlan> getSoftwarePlanList(Long id) {
    	String url = UriComponentsBuilder.newInstance().path("/softwares/plan/{id}/list?sort=id,asc")
    			.build()
    			.expand(id)
    			.toString();
    	return paasApiRest.getForObject(url, List.class);
    }
//
//    public void updateSoftware(Software software) {
//        String url = UriComponentsBuilder.newInstance().path("/softwares/{id}")
//                .build()
//                .expand(software.getId())
//                .toString();
//
//        paasApiRest.put(url, software);
//    }
}
