package ebuy.common;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class AuthCode {
    
    static Random random = new Random();
    
    /**
     * 验证码产生类
     * @return
     */
    public static String getAuthCode(){
        StringBuilder code = new StringBuilder();
        for(int i = 0;i < 4;i++){
            code.append(random.nextInt(10));
        }
        System.out.println(code.toString());
        return code.toString();
    }
    
    /**
     * 获取验证码图片
     */
    public static BufferedImage getCodeImg(String code){
        //创建一个图片对象，并指定图片的宽高和类型
        BufferedImage img = new BufferedImage(88,28,BufferedImage.TYPE_INT_RGB);
        //通过图片获取一个绘图对象（理解为画笔）
        Graphics grap = img.getGraphics();
        //设置颜色(矩形区域颜色)
        grap.setColor(Color.YELLOW);
        //绘制一个矩形
        grap.fillRect(0, 0, 88,28);
        //设置颜色(验证码颜色)
        grap.setColor(Color.BLACK);
        //设置字体
        grap.setFont(new Font("宋体",Font.PLAIN,24));
        //不断的进行验证码状态绘制
        for(int i=0;i<code.length();i++){
            //从code对象中根据下标一个一个的获取需要绘制的字符
            char c = code.charAt(i);
            //绘制字符
            grap.drawString(c+"", i*20, 20);
        }
        //绘制干扰线
        for(int i=0;i<20; i++){
            //线条有开始和结束位置
            //获取干扰线x轴开始位置
            int x = random.nextInt(88);
            //获取干扰线x轴结束位置
            int x2 = random.nextInt(88);
            //获取干扰线y轴开始位置
            int y = random.nextInt(28);
            //获取干扰线y轴结束位置
            int y2 = random.nextInt(28);
            //绘制线条
            grap.drawLine(x, y, x+x2, y+y2);
        }
        //返回绘制好的图片对象
        return img;
    }
}
