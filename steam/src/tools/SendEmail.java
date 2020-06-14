package tools;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;

import model.sql.Sql;
public class SendEmail {
	private Sql sql;
	private String securityCode;
	public SendEmail(Sql sql) {
		this.sql=sql;
	}
	//生成验证码
	public void makeSecurityCode() {
		securityCode = sql.getUsers_sql().md5(Integer.toString((int) (10000*Math.random()))).substring(0, 5);
	}
	//获得上一次生成的验证码
	public String getSecurityCode() {
		return securityCode;
	}
    public void sendSecurityCode(String to) throws GeneralSecurityException {
        // 发件人电子邮箱
        String from = "993608769@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.auth", "true");
       
        //设置SSL身份验证
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);									//对所有主机信任
        properties.put("mail.smtp.ssl.enable", "true");				//开启身份验证
        properties.put("mail.smtp.ssl.socketFactory", sf);			
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("993608769@qq.com", "xkpyhxhmbyovbeji"); 		//发件人邮件用户名、授权码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("您的验证码是");

            // 设置消息体
            message.setText(securityCode);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}