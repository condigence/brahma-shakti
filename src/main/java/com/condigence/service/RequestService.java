package com.condigence.service;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {

    String getClientIp(HttpServletRequest request);

}
