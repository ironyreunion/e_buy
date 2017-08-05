package ebuy.action.customer;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ebuy.common.WebConstant;
import ebuy.dao.UserDao;
import ebuy.dto.User;

/**
 * Servlet implementation class LoginServlet
 */

@Controller
@RequestMapping("/customer")
public class LoginAction{
	
    @ResponseBody
    @RequestMapping(value="/loginAjax")
	public Map<String,Object> login(String userId,String password,String vcode){
	    
	    System.out.println("---login()---" + userId + "--" + password + "--" + vcode);
	    
	    Map<String,Object> map = new HashMap<>();
	    
	    //获取Servlet API
	    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest(); 
	    
	    //从Session中获取验证码
	    String oldVcode =(String)request.getSession().getAttribute(AuthCodeAction.AUTH_CODE);
        
	    /*
	     * 进行验证码判断
         * 登陆成功 = 0
         * 验证码不正确 = 1
         * 账号密码不能为空 = 2
         * 账号密码格式不正确 = 3
         * 用户账号未激活 = 4
         */
        if(vcode == null || ! oldVcode.equals(vcode)){
            map.put("tip", "验证码不正确");
            map.put("status", 1);
        }else{
            //创建dao对象
            UserDao userDao = new UserDao();
            User u = new User();
            u.setUserId(userId);
            u.setPassword(password);
            //查询登录用户是否存在
            User user = userDao.getUserByUserIdAndByPassword(u);
            if(user != null){
                //存在将用户添加到session中方便后面使用
                request.getSession().setAttribute(WebConstant.SESSION_USER, user);
                map.put("tip", "登录成功");
                map.put("status", 0);
            }else{
                map.put("tip", "用户名或密码不正确！");
                map.put("status", 3);
            }
        }
        return map;
    }
    
    
    /**
     * 退出处理
     */
    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "home";
    }
}
