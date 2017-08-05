package ebuy.action.customer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ebuy.common.EmailTools;
import ebuy.dao.UserDao;
import ebuy.dto.User;

@Controller
@RequestMapping("/customer")
public class UserAction {
    @RequestMapping("/register")
    public String register(User user){
        //创建激活码（以时间为变量创建出随机激活码）
        String activeCode = String.valueOf(System.currentTimeMillis());
        //激活地址链接，并拼装页面语句
        StringBuilder content = new StringBuilder();
        String activeUrl = "http://localhost:8080/E_BuysMVC/customer/active?useId=" + user.getUserId() + "&activeCode=" + activeCode;
        content.append(user.getUserId() + "，您好，请点击下面的链接进行账号激活：<br/>");
        content.append("<a href='" + activeUrl + "'>" + activeUrl + "</a>");
        //String content = user.getUserId() + "，您好，请点击下面的链接进行账号激活：<br/>" + "<a href='" + activeUrl + "'>" + activeUrl + "</a>";
        boolean flag = EmailTools.send(user.getEmail(), "E购通商城-注册激活", content.toString());
        if(flag){
            UserDao userDao = new UserDao();
            user.setActiveCode(activeCode);
            user.setCreateDate(new Date());
            userDao.save(user);
            
        }else{
            return "register";
        }
        return "home";
    }
    
    /**
     * 用户激活
     * @param userId
     * @param activeCode
     * @return
     */
    @RequestMapping("/active")
    public String active(String userId,String activeCode,Model data){
        UserDao userDao = new UserDao();
        Map<String,String> map = new HashMap<>();
        map.put("userId", userId);
        map.put("activeCode", activeCode);
        
        int count = userDao.activeUser(map);
        String tip = count>0?"账号激活成功":"账号激活失败";
        data.addAttribute("tip",tip);
        
        return "login";
    }
    
    @RequestMapping("/userIdCheck")
    @ResponseBody
    public Map<String,Object> userIdCheck(String userId){
        UserDao userDao = new UserDao();
        boolean flag = userDao.getUserByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        /*
         * flag为true表示查到人，用1来表示；
         * flag为false表示查不到人，用0来表示；
         */
        map.put("status", (flag?1:0));
        return map;
    }
}
