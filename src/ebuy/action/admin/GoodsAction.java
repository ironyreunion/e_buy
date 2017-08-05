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
import ebuy.dao.UserDao;
import ebuy.dto.Goods;
import ebuy.dto.GoodsType;
import ebuy.dto.User;

@Controller
@RequestMapping("/admin/goods")
//商品 相关操作 控制器
public class GoodsAction {
    
    GoodsDao goodsDao = null;
    GoodsTypeDao goodsTypeDao = null;
    
    /*
     * 分页模型对象
     */
    PageModel pageModel = new PageModel();
    
    /**
     * 查询所有或者部分商品
     * @param user
     * @param data
     * @return
     */
    @RequestMapping("/goodsList")
    public String getGoodsList(Goods goods,@RequestParam(name="pageIndex",defaultValue="0")int pageIndex,Model data){
        /**
         * 得到产品列表
         */
        goodsDao = new GoodsDao();
        goodsTypeDao = new GoodsTypeDao();
        
        //取得所有商品类型
        List<GoodsType> goodsTypeList = goodsTypeDao.getGoodsTypeList();
        data.addAttribute("goodsTypeList", goodsTypeList);
        
        //创建一个Map对象，将pageModel分页模型和页面传入的goods查询条件包装并传给Dao调用
        Map<String,Object> params = new HashMap<>();
        params.put("goods",goods);
        //查看是否有产品
        int recordCount = goodsDao.count(params);
        //如果有产品，则进行分页
        if(recordCount != 0){
            /*
             * 对分页模型进行初始化设置
             */
            //设置总共有多少条记录，方便进行分页计算
            pageModel.setRecordCount(recordCount);
            //设置当前页
            pageModel.setPageIndex(pageIndex);
            
            params.put("pageModel",pageModel);
            //创建List对象接收数据库传回来的产品数据
            List<Goods> goodsList = goodsDao.getPageGoods(params);
            
            //在页面取到和迭代goodsList
            data.addAttribute("pageModel", pageModel);
            data.addAttribute("goodsList", goodsList);
            
        }
        return "goods/goodsList";
        
    }
    
    /**
     * 文件Ajax上传
     * 此处需要文件上传jar包
     * @param file 将页面中name为pic的input赋予file对象
     * @param session
     * @return
     * @throws IOException 
     * @throws IllegalStateException 
     */
    @RequestMapping("/imgUpload")
    @ResponseBody
    public String fileUpload(@RequestParam(name="pic")MultipartFile file,HttpSession session) throws IllegalStateException, IOException{
        //文件上传地址,获取文件在服务器的存储路径
        String path = session.getServletContext().getRealPath("/images/goodsDescImgs");
        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        //进行文件存储
        file.transferTo(new File(path,fileName));
       
        return fileName;
    }
    
    
    /**
     * 展示新增页面的数据准备
     * @param data
     * @return
     */
    @RequestMapping("/toAddGoods")
    public String toAddGoods(Model data){
        goodsTypeDao = new GoodsTypeDao();
        //取得所有商品类型
        List<GoodsType> goodsTypeList = goodsTypeDao.getGoodsTypeList();
        data.addAttribute("goodsTypeList", goodsTypeList);
        
        return "goods/addGoods";
    }
    
    /**
     * 商品添加界面
     * @param goods
     * @param data
     * @return
     * @throws IOException 
     */
    @RequestMapping("/addGoods")
    public String addGoods(@RequestParam(name="pic")MultipartFile file,HttpSession session,Goods goods,Model data) throws IOException{
        //文件上传地址,获取文件在服务器的存储路径
        String path = session.getServletContext().getRealPath("/images/goodsDescImgs");
        //获取上传文件的名称
        String fileName = file.getOriginalFilename();
        //进行文件存储
        file.transferTo(new File(path,fileName));
        
        goodsDao = new GoodsDao();
        goods.setImage(fileName);
        goods.setCreateDate(new Date());
        boolean flag = goodsDao.saveGoods(goods);
        
        return "redirect:/admin/goods/goodsList";
    }
    
    /**
     * 根据商品的类型获取商品，返回商品列表（新增抢购页面使用）
     * @param code
     * @return
     */
    @RequestMapping("/goodsListAjax")
    @ResponseBody
    public List<Goods> goodsListAjax(String code){
        GoodsDao goodsDao = new GoodsDao();
        List<Goods> goodsList = goodsDao.getGoodsByCode(code);
        
        return goodsList;
    }
}
