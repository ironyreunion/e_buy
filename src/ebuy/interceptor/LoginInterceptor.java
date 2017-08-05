package ebuy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import ebuy.common.WebConstant;
import ebuy.dto.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在Controller类调用之前执行
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        //获取Session
        HttpSession session = request.getSession();
        //取得Session中存放的用户，并用User对象接收
        User user = (User)session.getAttribute(WebConstant.SESSION_USER);
        //判断（检测）用户是否存在
        if(user != null){
            //继续执行Controller请求
            return true;
        }else{
            System.out.println("-------用户  不  存在-------");
            String toURL = "/login.html";
            if(!request.getRequestURI().contains("customer")){
                toURL = "/admin";
            }
            //用户没有登录，重新登录
            response.sendRedirect(request.getContextPath()+toURL);
            //不执行Controller请求
            return false;
            //去springmvc-servlet.xml中配置拦截器
        }
    }
}
