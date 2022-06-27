package com.lecuong.java09springboot.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecuong.java09springboot.modal.AccountInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;

/**
 * author CuongLM
 */
@Order(2)
@Component
public class TransactionFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    AccountInfo accountInfo;

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        /*
            Với các project tự sinh token thì cần 1 trường hợp if nữa để check các api không có token mà được config ở SecurityConfig thì cho qua
			Còn với các project có tích hợp bên thứ 3 như keycloak thì không cần trường hợp if này
         */
//        if ("POST".equals(request.getMethod()) && request.getHeader("X-AUTH-TOKEN") == null) {
//            chain.doFilter(request, response);
//        } else {
//
//            //Get token from header
//            String token = request.getHeader("X-AUTH-TOKEN");
//
//            //Transform token to array by "."
//            String[] chunks = token.split("\\.");
//            Base64.Decoder decoder = Base64.getUrlDecoder();
//
//            //Convert payload to json from token
//            String payload = new String(decoder.decode(chunks[1]));
//
//            //Parse json to object
//            AccountInfo accountInfoObject = objectMapper.readValue(payload, AccountInfo.class);
//            accountInfo.setRole(accountInfoObject.getRole());
//            accountInfo.setFullName(accountInfoObject.getFullName());
//            accountInfo.setId(accountInfoObject.getId());
//            accountInfo.setUserName(accountInfoObject.getUserName());
//
//            chain.doFilter(req, res);
//        }

        if ("OPTIONS".equals(request.getMethod()) && request.getHeader("X-AUTH-TOKEN") != null) {
            //Get token from header
            String token = request.getHeader("X-AUTH-TOKEN");

            //Transform token to array by "."
            String[] chunks = token.split("\\.");
            Base64.Decoder decoder = Base64.getUrlDecoder();

            //Convert payload to json from token
            String payload = new String(decoder.decode(chunks[1]));

            //Parse json to object
            AccountInfo accountInfoObject = objectMapper.readValue(payload, AccountInfo.class);
            accountInfo.setRole(accountInfoObject.getRole());
            accountInfo.setFullName(accountInfoObject.getFullName());
            accountInfo.setId(accountInfoObject.getId());
            accountInfo.setUserName(accountInfoObject.getUserName());

            chain.doFilter(req, res);
        } else {
            chain.doFilter(req, res);
        }

    }
}
