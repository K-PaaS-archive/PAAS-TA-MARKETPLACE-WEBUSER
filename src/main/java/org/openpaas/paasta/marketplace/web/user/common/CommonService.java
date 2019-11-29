package org.openpaas.paasta.marketplace.web.user.common;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Common Service 클래스
 */
@Service
public class CommonService {

    public String setParameters(HttpServletRequest httpServletRequest) {
        Map<String, String[]> parametersMap = httpServletRequest.getParameterMap();
        String[] parametersObject;
        String parametersKey;
        String resultString = "";

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < ParametersList.values().length; i++) {
            parametersKey = ParametersList.values()[i].actualValue;
            parametersObject = parametersMap.get(parametersKey);

            if (parametersObject != null && !"".equals(parametersObject[0])) {
                stringBuilder.append("&").append(parametersKey).append("=").append(parametersObject[0]);
            }
        }

        if (stringBuilder.length() > 0) {
            resultString = "?" + stringBuilder.substring(1);
        }

        return resultString;
    }

    /**
     * Sets path variables.
     *
     * @param httpServletRequest the http servlet request
     * @param viewName           the view name
     * @param mv                 the mv
     * @return the path variables
     */
    public ModelAndView setPathVariables(HttpServletRequest httpServletRequest, String viewName, ModelAndView mv) {
        Map pathVariablesMap = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object pathVariablesObject;
        String pathVariablesKey;

        Map<String, String[]> parametersMap = httpServletRequest.getParameterMap();
        String[] parametersObject;
        String parametersKey;

        for (int i = 0; i < PathVariablesList.values().length; i++) {
            pathVariablesKey = PathVariablesList.values()[i].actualValue;
            pathVariablesObject = pathVariablesMap.get(pathVariablesKey);

            if (pathVariablesObject != null) {
                mv.addObject(pathVariablesKey, String.valueOf(pathVariablesObject));
            }
        }

        for (int i = 0; i < ParametersList.values().length; i++) {
            parametersKey = ParametersList.values()[i].actualValue;
            parametersObject = parametersMap.get(parametersKey);

            if (parametersObject != null && !"".equals(parametersObject[0])) {
                mv.addObject(parametersKey, parametersObject[0]);
            }
        }

        mv.setViewName(viewName);

        return mv;
    }


    /**
     * The enum Path variables list.
     */
    enum PathVariablesList {
        /**
         * Path variables id path variables list.
         */
        PATH_VARIABLES_ID("id");

        private String actualValue;

        PathVariablesList(String actualValue) {
            this.actualValue = actualValue;
        }
    }


    /**
     * The enum Parameters list.
     */
    enum ParametersList {
        /**
         * Parameters id parameters list.
         */
        PARAMETERS_ID("categoryId"),
        /**
         * Parameters name parameters list.
         */
        PARAMETERS_NAME1("nameLike"),
        /**
         * Parameters name parameters list.
         */
        PARAMETERS_NAME2("softwareNameLike"),
        /**
         * Parameters status parameters list.
         */
        PARAMETERS_STATUS("status"),
        /**
         * Parameters useYn parameters list.
         */
        PARAMETERS_INUSE("inUse"),
        /**
         * Parameters sort parameters list.
         */
        PARAMETERS_CREATE_DATE("createdBy"),
        /**
         * Parameters size parameters list.
         */
        PARAMETERS_PAGING_SIZE("size"),
        /**
         * Parameters sort parameters list.
         */
        PARAMETERS_SORT("sort"),
        /**
         * Parameters page parameters list.
         */
        PARAMETERS_PAGING_PAGE("page"),
        /**
         * Parameters start date parameters list.
         */
        PARAMETERS_START_DATE("createdDateAfter"),
        /**
         * Parameters end date parameters list.
         */
        PARAMETERS_END_DATE("createdDateBefore"),
        /**
         * Parameters start date parameters list.
         */
        PARAMETERS_APPROVAL_START_DATE("statusModifiedDateAfter"),
        /**
         * Parameters end date parameters list.
         */
        PARAMETERS_APPROVAL_END_DATE("statusModifiedDateBefore"),
        /**
         * Parameters start date parameters list.
         */
        PARAMETERS_USAGE_START_DATE("usageStartDate"),
        /**
         * Parameters start date parameters list.
         */
        PARAMETERS_USAGE_END_DATE("usageEndDate"),
    	
    	PARAMETERS_SEARCH_START_DATE("srchStartDate"),
    	PARAMETERS_SEARCH_END_DATE("srchEndDate"),
    	PARAMETERS_SEARCH_SOFTWARE_NAME("srchSoftwareName");

        private String actualValue;

        ParametersList(String actualValue) {
            this.actualValue = actualValue;
        }
    }

}
