package com.agustini.app.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;

@Component
public class TenantInterceptor implements WebRequestInterceptor {
    @Override
    public void preHandle(WebRequest webRequest) throws Exception {
        String uri = ((ServletWebRequest)webRequest).getRequest().getRequestURI();
        String tenant = uri.split("/")[1];
        ThreadTenantStorage.setTenantId(tenant);
    }

    @Override
    public void postHandle(WebRequest webRequest, ModelMap modelMap) throws Exception {}

    @Override
    public void afterCompletion(WebRequest webRequest, Exception e) throws Exception {}
}
