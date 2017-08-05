package ebuy.action.customer;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ebuy.common.AuthCode;

/**
 * 验证码处理器
 * @author Administrator
 *
 */
@Controller
public class AuthCodeAction{
    
    public static final String AUTH_CODE = "AUTH_CODE";
    private static final long serialVersionUID = 1L;
    
    @RequestMapping("verify")
    public void getAuthCodeServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取验证码
	    String code = AuthCode.getAuthCode();
		//将验证码存储到Session中
	    HttpSession session = request.getSession();
	    session.setAttribute(AUTH_CODE, code);
	    
	    //获取验证码图片
	    BufferedImage img = AuthCode.getCodeImg(code);
	    ImageIO.write(img, "JPEG", response.getOutputStream());
	}

}
