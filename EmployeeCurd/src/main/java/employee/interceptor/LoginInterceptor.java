package employee.interceptor;


import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
       /* String uri = request.getRequestURI();
        if (uri.indexOf("login_bubble.html") > 0 || uri.indexOf("imgHandle") > 0 || uri.indexOf("static") > 0) {
            return true;
        }*/
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username != null && username.trim().length() > 0) {
            return true;
        } else {
            session.setAttribute("isLogin",'N' );
            request.getRequestDispatcher("/login_bubble.html").forward(request,response );
//            response.sendRedirect("/ssm1/login_bubble.html");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
