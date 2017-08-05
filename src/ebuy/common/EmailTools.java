package ebuy.common;

import java.util.Properties;

import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 邮件创建发送类
 * @author Administrator
 *
 */
public class EmailTools {
    /**
     * 邮件链接并发送方法
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     * @return 邮件发送成功还是失败
     */
    public static boolean send(String to,String subject,String content){
        /*
         * 第一步：创建Session
         */
        Properties props = new Properties();
        //邮件协议
        props.put("mail.transport.protocol", "smtp");
        //邮件端口
        props.put("mail.host", "smtp.163.com");
        //邮件发送人
        props.put("mail.from", "F940163229@163.com");
        //发送人与邮件服务器的连接对象
        Session session = Session.getDefaultInstance(props);
        //开启调试模式
        session.setDebug(true);
        
        try {
            /*
             * 第二步：获取邮件发送对象
             */
            Transport transport = session.getTransport();
            //连接邮件服务器
            //transport.connect("F940163229@163.com","kimi9964");
            transport.connect("F940163229@163.com","kimi9964");
            
            /*
             * 第三步：创建邮件消息体
             */
            MimeMessage message = new MimeMessage(session);
            message.setContent(content, "text/html;charset=UTF-8");
            message.setSubject(subject);
            
            /*
             * 第四步：发送邮件
             */
            transport.sendMessage(message, InternetAddress.parse(to));
            return true;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
