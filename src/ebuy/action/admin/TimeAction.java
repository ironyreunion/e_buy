package ebuy.action.admin;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import ebuy.common.PageModel;
import ebuy.dao.GoodsDao;
import ebuy.dao.GoodsTypeDao;
import ebuy.dao.PictureCarouselDao;
import ebuy.dao.TimeLimitedDao;
import ebuy.dao.UserDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.PictureCarousel;
import ebuy.dto.TimeLimited;
import ebuy.dto.User;

/**
 * 限时抢购 相关操作 控制器
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/admin/timeLimited")
public class TimeAction {
    /**
     * 显示限时抢购的所有商品
     * @param data
     * @return
     */
    @RequestMapping("/timeLimitedList")
    public String showTimeLimitedList(Model data){
        TimeLimitedDao timeLimitedDao = new TimeLimitedDao();
        List<TimeLimited> timeLimitedList = timeLimitedDao.getTimeLimitedList();
        
        data.addAttribute("timeLimitedList", timeLimitedList);
        return "time/timeList";
    }
    
    /**
     * 跳转到添加页面
     * @return
     */
    @RequestMapping("/toAddTime")
    public String toAddTime(){

        return "time/addTime";
    }
    
    /**
     * 添加商品 提交
     * @return
     */
    @RequestMapping("/addTime")
    public String addTime(TimeLimited timeLimited){
        TimeLimitedDao timeLimitedDao = new TimeLimitedDao();
        timeLimitedDao.save(timeLimited);
        return "redirect:/admin/timeLimited/timeLimitedList";
    }
}
