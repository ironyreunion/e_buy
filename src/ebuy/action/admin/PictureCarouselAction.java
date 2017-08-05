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
import ebuy.dao.UserDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.PictureCarousel;
import ebuy.dto.User;

@Controller
@RequestMapping("/admin/pictureCarousel")
//轮播图 相关操作 控制器
public class PictureCarouselAction {
    /**
     * 页面请求到处理器
     * @param data
     * @return
     */
    @RequestMapping("/pictureCarouselList")
    public String showPictureCarouselList(Model data){
        //创建轮播图Dao
        PictureCarouselDao PictureCarousel = new PictureCarouselDao();
        List<PictureCarousel> pictureCarouselList = PictureCarousel.getPictureCarousel();
        data.addAttribute("pictureCarouselList", pictureCarouselList);
        return "pic/picList";
    }
    
    @RequestMapping("/toAddPic")
    public String toAddPic(){
        return "pic/addPic";
    }
    
    @RequestMapping("/addPic")
    public String addPic(@RequestParam(name="pic")MultipartFile file,HttpSession session,PictureCarousel pictureCarousel,Model data) throws Exception{
        String url = "/images/pictureCarouselImgs";
        //文件上传地址,获取文件在服务器的存储路径
        String path = session.getServletContext().getRealPath(url);
        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        //进行文件存储
        file.transferTo(new File(path,fileName));
        
        PictureCarouselDao pictureCarouselDao = new PictureCarouselDao();
        pictureCarousel.setImageUrl(url+File.separator+fileName);
        boolean flag = pictureCarouselDao.savePictureCarousel(pictureCarousel);
        
        return "redirect:/admin/pictureCarousel/pictureCarouselList";
    }
}
