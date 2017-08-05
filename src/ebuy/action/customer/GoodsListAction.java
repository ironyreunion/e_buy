package ebuy.action.customer;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ebuy.common.GoodsListPageModel;
import ebuy.common.PageModel;
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
 * 首页搜索框
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/customer")
public class GoodsListAction extends HttpServlet {
    
    GoodsDao goodsDao = null;
    GoodsTypeDao goodsTypeDao = null;
    
    /*
     * 分页模型对象
     */
    GoodsListPageModel pageModel = new GoodsListPageModel();
    
    /**
     * 搜索操作返回商品列表页面
     * @param goods
     * @param pageIndex
     * @param data
     * @return
     */
    @RequestMapping(value="/searchs")
    public String searchs(Goods goods,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,Model data){
        //创建商品类型Dao对象
        GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
        //取得所有商品类型
        List<GoodsType> goodstypeList = goodsTypeDao.getGoodsTypeList();
        data.addAttribute("goodstypeList", goodstypeList);
        
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("goods",goods);
        //分页
        GoodsDao goodsDao = new GoodsDao();
        int recordCount = goodsDao.count(params);
        if(recordCount != 0){
            //设置总共有多个条记录（方便进行分页计算）
            pageModel.setRecordCount(recordCount);
            //设置当前页
            pageModel.setPageIndex(pageIndex);
            
            params.put("pageModel", pageModel);
            List<Goods> goodsList = goodsDao.getPageGoods(params);
            
            data.addAttribute("pageModel", pageModel);
            data.addAttribute("goodsList", goodsList);  
        }
        
        return "goodsList";
    }
}
