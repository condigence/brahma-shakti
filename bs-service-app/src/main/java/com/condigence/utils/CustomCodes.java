package com.condigence.utils;

import com.google.common.collect.ImmutableMap;

public class CustomCodes {

    private ImmutableMap<String, String> errorCodesMap = ImmutableMap.<String, String>builder()
            .put("200","I0200")
            .put("400","E0400")
            .put("401","E0401")
            .put("403","E0403")
            .put("404","E0404")
            .put("500","E0500")
            .build();

}
