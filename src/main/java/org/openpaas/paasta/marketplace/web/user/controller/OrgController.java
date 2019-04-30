package org.openpaas.paasta.marketplace.web.user.controller;

import org.openpaas.paasta.marketplace.web.user.common.Common;
import org.openpaas.paasta.marketplace.web.user.service.OrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @author hrjin
 * @version 1.0
 * @since 2019-03-25
 */
@Controller
@RequestMapping(value = "/market/orgs")
public class OrgController extends Common {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrgController.class);

    @Autowired
    OrgService orgService;

}
