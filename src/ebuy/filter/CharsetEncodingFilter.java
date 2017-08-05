package ebuy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 编码处理过滤器
 * @author Administrator
 *
 */
public class CharsetEncodingFilter implements Filter{

    String charset = null;
            
    @Override
    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub
        System.out.println("---init()--初始化参数---");
        //获取过滤器初始化参数
        charset = config.getInitParameter("charset");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        System.out.println("---doFilter()--处理过程---");
        //设置请求编码
        req.setCharacterEncoding(charset);
        System.out.println("---doFilter()--预过程---");
        //传递请求到下一个Filter或者目标Servlet进行处理
        filterChain.doFilter(req, resp);
        //设置响应编码
        resp.setContentType("text/html;charset=" + charset);
        System.out.println("---doFilter()--后过程---");
    }

    
    @Override
    public void destroy() {
        // TODO Auto-generated method stub
        System.out.println("---destroy()--资源释放---");
    }
}
