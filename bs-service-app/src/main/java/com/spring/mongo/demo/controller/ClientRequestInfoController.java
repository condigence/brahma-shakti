package com.spring.mongo.demo.controller;

import javax.servlet.http.HttpServletRequest;

import com.spring.mongo.demo.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ClientRequestInfoController {

    @Autowired
    private RequestService requestService;

    @RequestMapping("/get-requester-ip")
    public ModelAndView index(HttpServletRequest request) {
        ModelAndView model = new ModelAndView("index");
        String clientIp = requestService.getClientIp(request);
        model.addObject("clientIp", clientIp);
        return model;
    }

}
