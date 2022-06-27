package com.lecuong.java09springboot.intercepter;

import com.lecuong.java09springboot.httplog.HttpLoggingService;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpLoggingInterceptor implements HandlerInterceptor {

    private final HttpLoggingService httpLoggingService;

    public HttpLoggingInterceptor(HttpLoggingService httpLoggingService) {
        this.httpLoggingService = httpLoggingService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (DispatcherType.REQUEST.name().equals(request.getDispatcherType().name()) && request.getMethod().equals(HttpMethod.GET.name())) {
            httpLoggingService.logRequest(request, null);
        }

        if (isLogin(request)) {
            return true;
        }


        return true;
    }

    private boolean isLogin(HttpServletRequest request) {
        return request.getRequestURI().contains("/login")
                || request.getRequestURI().contains("/users/login")
                && request.getMethod().equalsIgnoreCase(HttpMethod.POST.toString());
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
