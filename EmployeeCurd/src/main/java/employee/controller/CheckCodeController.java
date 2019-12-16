package employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Controller
@RequestMapping(value = "/checkCode")
public class CheckCodeController {

    @RequestMapping("/imgHandle")
    public void handleCheckCode(HttpServletResponse response, HttpSession session) throws IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");

        int width = 98;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);

        Graphics graphics = image.getGraphics();

        // 填充背景色
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, width, height);

        graphics.setColor(Color.yellow);
        //设置字体的小大
        graphics.setFont(new Font("黑体", Font.BOLD, 20));

        // 生成随机验证码
        StringBuilder builder = new StringBuilder();
        Random random = new Random();
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghigklmnopqrstuvwxyz0123456789";
        int str_length = str.length();
        for (int i = 1; i <= 4; i++) {
            int index = random.nextInt(str_length);
            char c = str.charAt(index);
            builder.append(c);
            graphics.drawString(c + "", (70 * i / 4), 20);
        }

        String checkCode = builder.toString();
        session.setAttribute("checkCode", checkCode);

//        画干扰线
        graphics.setColor(Color.red);
        for (int i = 0; i < 7; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics.drawLine(x1,y1 ,x2 ,y2 );
        }

        ImageIO.write(image, "jpg", response.getOutputStream());
        response.getOutputStream().flush();
    }
}
