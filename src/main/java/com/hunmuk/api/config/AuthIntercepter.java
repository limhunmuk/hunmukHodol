package com.hunmuk.api.config;

import com.hunmuk.api.exception.UnAuthorized;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
public class AuthIntercepter implements HandlerInterceptor {

    // 요기서는 안씀
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       //log.info(">>>>>>>>>>>> preHandle 컨트롤러전");

        String accessToken = request.getParameter("accessToken");
        //if (accessToken != null && accessToken.equals("hunmuk")) {
        if (accessToken != null && !accessToken.isEmpty()) {
            request.setAttribute("userName", accessToken);
            return true;
        }
        throw new UnAuthorized();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

       // log.info(">>>>>>>>>>>> postHandle 컨트롤러후");

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
      //  log.info(">>>>>>>>>>>> afterCompletion 뷰반환후 ");
    }
}
