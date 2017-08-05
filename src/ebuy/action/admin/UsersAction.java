package ebuy.action.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import ebuy.dao.UserDao;
import ebuy.dto.User;

@Controller
@RequestMapping("/admin/user")
public class UsersAction {
    /**
     * 查询所有或者部分用户操作
     * @param user
     * @param data
     * @return
     */
    @RequestMapping("/userList")
    public String getUserList(User user,Model data){
        UserDao userDao = new UserDao();
        List<User> userList = userDao.getUserList(user);
        data.addAttribute("userList", userList);
        return "user/userList";
    }
    
    /**
     * 删除数据
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public String delete(int id){
        UserDao userDao = new UserDao();
        userDao.deleteUser(id);
        return "forward:/admin/user/userList";
    }
    
    /**
     * 进入更新用户数据【页面】
     * @param id
     * @return
     */
    @RequestMapping("/showUpdate")
    public String showUpdate(int id,Model data){
        UserDao userDao = new UserDao();
        User user = userDao.getUserById(id);
        data.addAttribute("user", user);
        return "user/updateUser";
    }
    
    /**
     * 提交更新用户数据
     * @param id
     * @return
     */
    @RequestMapping("/update")
    public String update(User user){
        UserDao userDao = new UserDao();
        userDao.updateUser(user);
        return "redirect:/admin/user/userList";
    }
}
