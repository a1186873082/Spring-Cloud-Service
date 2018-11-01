package com.lc.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelloController {

    private Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        List<String> serviceIds = discoveryClient.getServices();
        if (!serviceIds.isEmpty()){
            for (String s : serviceIds) {
                logger.info("serviceId" + s);

                List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(s);
                if (!serviceInstanceList.isEmpty()) {
                    for (ServiceInstance serviceInstance : serviceInstanceList) {
                        logger.info("/hello, host:" + serviceInstance.getHost() + ", service_id:" + serviceInstance.getServiceId());
                    }
                } else {
                    logger.info("no services");
                }
            }
        }

        return "HELLO World";
    }
}
