package com.wenjun.seckill.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 生成图形验证码
 * @Author: wenjun
 * @Date: 2020/1/1 19:41
 */
public class CodeUtil {
    private static int width = 80;//定义图片的width
    private static int height = 30;//定义图片的height
    private static int codeCount = 4;//定义图片上显示验证码的个数
    private static int codeX = 15;//验证码X坐标起始地点
    private static int codeY = 23;//验证码Y坐标起始地点
    private static int fontHeight = 18;//验证码字体大小
    private static char[] codeSequence = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N',
            'O','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};

    /**
     * 生成一个Map集合
     * code为生成的验证码
     * codePic为生成的验证码BufferedImage对象
     * @return
     */
    public static Map<String,Object> generateCodeAndPic() {
        //定义图像buffer
        BufferedImage buffImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics graphics = buffImg.getGraphics();
        //创建一个随机数生成器
        Random random = new Random();
        //将图像填充为白色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0,0,width,height);
        //创建字体，字体的大小应该根据图片的高度来定
        Font font = new Font("Fixedsys",Font.BOLD,fontHeight);
        //设置字体
        graphics.setFont(font);
        //画边框
        graphics.setColor(Color.BLACK);
        graphics.drawRect(0,0,width-1,height-1);

        //随机产生40条干扰线，使图像中的认证码不易被其他程序探测到
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < 40; i++) {
            int x  = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            graphics.drawLine(x,y,x+xl,y+yl);
        }

        //randomCode用于保存随机产生的验证码，以便用户登录后进行验证
        StringBuffer randomCode = new StringBuffer();
        int red = 0,green = 0,blue = 0;

        //随机产生codeCount数字的验证码
        for (int i = 0; i < codeCount; i++) {
            //得到随机产生的验证码数字
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            //产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            //用随机产生的颜色将验证码绘制到图像中
            graphics.setColor(new Color(red,green,blue));
            graphics.drawString(code,(i+1) * codeX,codeY);
            //将产生的四个随机数组合在一起
            randomCode.append(code);
        }

        Map<String,Object> map = new HashMap<>();
        //存放验证码
        map.put("code",randomCode);
        //存放生成的验证码BufferedImage对象
        map.put("codePic",buffImg);
        return map;
    }

    public static void main(String[] args) throws IOException {
        //创建文件输出流对象
        OutputStream outputStream = new FileOutputStream("src/" + System.currentTimeMillis() + ".jpg");
        Map<String,Object> map = generateCodeAndPic();
        ImageIO.write((RenderedImage) map.get("codePic"),"jpeg",outputStream);
        System.out.println("验证码的值为：" + map.get("code"));
    }
}
