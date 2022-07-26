package com.kk.service.impl;

import com.kk.service.SendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class SendMimeMessageImpl implements SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    private String from = "x@x.com";
    private String to = "x@x.com";

    @Override
    public void sendMail() {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(from);
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject("测试第二封高级邮件");
            //setText 默认是无法解析 HTML 的，如果想要解析需要设置解析为 true
            //发送图片 + 发送链接
            mimeMessageHelper.setText("高级邮件的正文内容" + "<img src='https://th.bing.com/th/id/R.901f8ebdab22d065baefeae6c2701cc0?rik=Z3Hew18zFaF%2bLQ&riu=http%3a%2f%2fwww.pp3.cn%2fuploads%2f20120418lw%2f13.jpg&ehk=Es5ZGH90h%2foCghvlIwdKfUiqpO05gLSgOEBU2i0Mwok%3d&risl=&pid=ImgRaw&r=0'/>" + "<a href = https://www.baidu.com>点开有惊喜</a>", true);
            //添加附件
            File file1 = new File("C:\\Users\\Lucky\\Desktop\\R-C.png");
            File file2 = new File("C:\\Users\\Lucky\\Desktop\\Desktop.rar");
            mimeMessageHelper.addAttachment("李德胜爷爷.png", file1);
            mimeMessageHelper.addAttachment("李嬴.zip", file2);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
