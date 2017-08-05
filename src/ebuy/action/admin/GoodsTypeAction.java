package ebuy.action.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import ebuy.dao.GoodsTypeDao;
import ebuy.dao.UserDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.User;

@Controller
@RequestMapping("/admin/goodsType")
//商品类型 相关操作 控制器
public class GoodsTypeAction {
    /**
     * 查询获取所有商品类型
     * @param data
     * @return
     */
    @RequestMapping("/goodsTypeList")
    public String getGoodsTypeList(Model data){
        GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
        List<GoodsType> goodsTypeList = goodsTypeDao.getGoodsTypeList();
        data.addAttribute("goodsTypeList", goodsTypeList);
        return "goodsType/goodsTypeList";
    }
    
    /**
     * Ajax获取商品类型数据，返回列表类型（新增抢购页面使用）
     * @return
     */
    @RequestMapping("/goodsTypeListAjax")
    @ResponseBody
    public List<GoodsType> goodsTypeList(){
        GoodsTypeDao goodsTypeDao = new GoodsTypeDao();
        List<GoodsType> goodsTypeList = goodsTypeDao.getGoodsTypeList();
        return goodsTypeList;
    }
    
}
