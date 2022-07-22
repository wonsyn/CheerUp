package com.web.curation.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

//import com.web.curation.ssafit.util.JWTUtil;

@Component
public class JwtInterceptor implements HandlerInterceptor{
    private static final String HEADER_AUTH = "access-token"; // header에 넣을 때 저거 토큰을 넣어야 인증 됨
    
    @Autowired
    private JwtUtil jwtUtil; // 토큰 검증용

    //preHandle : 이동하기 전에
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        
        // 사용자가 요청시 헤더에 보내준 토큰 가져오기
        final String token = request.getHeader(HEADER_AUTH);
        System.out.println("token : " + token);
        if(token != null) { // null이면 잘못된 토큰
            jwtUtil.valid(token); // 검증 - 예외 없이 실행되면 정상
            return true; // 유효하면 다음
        }
        throw new Exception("토큰이 유효하지 않습니다.");
    }
}
