package ebuy.action.admin;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import ebuy.action.customer.AuthCodeAction;
import ebuy.common.EmailTools;
import ebuy.common.WebConstant;
import ebuy.dao.UserDao;
import ebuy.dto.Goods;
import ebuy.dto.User;

/**
 * 后台处理控制器
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/admin")
public class AdminAction {
    @RequestMapping(value="/login")
    public String login(){
        System.out.println("--admin--login()--");
        return "logins";
    }
    
    @RequestMapping(value="/main")
    public String main(){
        System.out.println("--admin--main()--");
        return "main";
    }
    
    
    
    @ResponseBody
    @RequestMapping(value="/adminLoginAjax")
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
            //判断权限。1是普通用户，2是管理员
            u.setRole(2);
            //查询登录用户是否存在
            User user = userDao.getAdmin(u);
            if(user != null){
                //存在将用户添加到session中方便后面使用
                request.getSession().setAttribute(WebConstant.SESSION_USER, user);
                map.put("tip", "管理员登录成功");
                map.put("status", 0);
            }else{
                map.put("tip", "用户名或密码不正确！");
                map.put("status", 3);
            }
        }
        return map;
    }
    
    
    /**
     * 管理员退出处理
     */
    @RequestMapping(value="/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "logins";
    }
}
