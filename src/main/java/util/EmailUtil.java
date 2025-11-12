package util;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailUtil {

    // Hàm gửi email
    public static void sendEmail(String to, String subject, String content) {
        final String fromEmail = "maya56754321";  // 📌 Thay bằng Gmail của bạn
        final String appPassword = "vptm opxi lgax hgkp"; // 📌 Thay bằng App Password Gmail

        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP server
        props.put("mail.smtp.port", "587");            // TLS Port
        props.put("mail.smtp.auth", "true");           // Bật xác thực
        props.put("mail.smtp.starttls.enable", "true");// Bật STARTTLS

        // Tạo session với username + app password
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, appPassword);
                    }
                });

        try {
            // Tạo nội dung email
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail, "HRM System")); // Người gửi
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to)); // Người nhận
            message.setSubject(subject, "UTF-8");
            message.setText(content, "UTF-8");

            // Gửi mail
            Transport.send(message);
            System.out.println("✅ Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("❌ Lỗi gửi email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
