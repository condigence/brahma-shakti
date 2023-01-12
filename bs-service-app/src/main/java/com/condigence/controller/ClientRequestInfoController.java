package com.condigence.controller;

import com.condigence.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
