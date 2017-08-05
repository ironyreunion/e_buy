package ebuy.action.customer;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ebuy.dao.GoodsDao;
import ebuy.dao.GoodsTypeDao;
import ebuy.dao.PictureCarouselDao;
import ebuy.dao.TimeLimitedDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.PictureCarousel;
import ebuy.dto.TimeLimited;
import ebuy.dto.User;

/**
 * Servlet implementation class LoginServlet
 */

@Controller
@RequestMapping("/customer")
public class HomeAction extends HttpServlet {
	/**
	 * 在首页迭代轮播和抢购的数据
	 * @param data
	 * @return
	 */
    @RequestMapping(value="/home")
	public String home(Model data){
        /*
         * 获取轮播数据
         */
        //创建轮播图Dao
        PictureCarouselDao pictureCarouselDao = new PictureCarouselDao();
        //取得所有轮播图数据，并用List对象接收
        List<PictureCarousel> pictureCarouselList = pictureCarouselDao.getPictureCarousel();
        //发送到界面上
        data.addAttribute("pictureCarouselList", pictureCarouselList);
        
        /*
         * 获取限时抢购数据
         */
        //创建限时抢购Dao
        TimeLimitedDao timeLimitedDao = new TimeLimitedDao();
        //取得所有限时抢购数据，并用List对象接收
        List<TimeLimited> timeLimitedList = timeLimitedDao.getTimeLimitedList();
        //发送到界面上
        data.addAttribute("timeLimitedList", timeLimitedList);
        
	    return "home";
	}

    /**
     * 显示轮播、限时抢购 进入详情
     * @param id
     * @param data
     * @return
     */
    @RequestMapping(value="/detail")
    public String detail(int id,Model data){
        //根据id获取商品
        GoodsDao goodsDao = new GoodsDao();
        Goods goods = goodsDao.getGoodsById(id);
        //将小类型上 - 进行替换
        goods.getGoodsType().setName(goods.getGoodsType().getName().replace("-", ""));
        data.addAttribute("goods", goods);

        //根据具体的商品类型获取商品的大类型
        GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
        GoodsType maxGoodsType = goodsTypeDao.findGoodsTypeByCode(goods.getGoodsType().getCode().substring(0, 4));
        data.addAttribute("maxGoodsType", maxGoodsType);

        return "details";
    }
    
    /**
     * 首页获取大类型和小类型数据给导航栏显示
     * @return
     */
    @RequestMapping(value="/getGoodsTypeAjax")
    @ResponseBody
    public List<GoodsType.GoodsTypes> getGoodsTypeAjax(){
        GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
        List<GoodsType.GoodsTypes> goodsTypes = goodsTypeDao.getGoodsTypes();
        return goodsTypes;
    }
}
