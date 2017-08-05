package ebuy.action.customer;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import ebuy.dto.User;

/**
 * Servlet implementation class LoginServlet
 */

@Controller
public class BaseAction extends HttpServlet {
	
    @RequestMapping(value="/*.html")
	public void base(){
	    
	    System.out.println("---base()---跳转");
	}

}
