package hu.vodafone.OIT.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;

public class LostDataMail {
    public void sendMail() {
        try {
            // Recipient's email ID needs to be mentioned.

            String to = "robert.fekete2@vodafone.com";
            String cc = "zoltan.melis2@vodafone.com";
            String bcc = "viktorbalazs.nyilas1@vodafone.com";
            // Sender's email ID needs to be mentioned
            String from = "team.ofretailmanagement@gmail.com";

            // Assuming you are sending email from through gmails smtp
            String host = "smtp.gmail.com";

            // Get system properties
            Properties properties = System.getProperties();

            // Setup mail server
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "465");
            properties.put("mail.smtp.ssl.enable", "true");
            properties.put("mail.smtp.auth", "true");

            // Get the Session object.// and pass
            Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

                protected PasswordAuthentication getPasswordAuthentication() {

                    return new PasswordAuthentication("team.ofretailmanagement@gmail.com", "tvrtkagerhuixhqd");

                }

            });
            //session.setDebug(true);
            try {
                // Create a default MimeMessage object.
                MimeMessage message = new MimeMessage(session);

                // Set From: header field of the header.
                message.setFrom(new InternetAddress(from));

                // Set To: header field of the header.
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.addRecipient(Message.RecipientType.BCC, new InternetAddress(bcc));
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));

                // Set Subject: header field
                message.setSubject("Retail Lost Device Export: "+ LocalDate.now());

                Multipart multipart = new MimeMultipart();

                MimeBodyPart attachmentPart = new MimeBodyPart();

                MimeBodyPart textPart = new MimeBodyPart();

                try {

                    File f = new File("/home/retailupdateserer/Vodafone/lostdevices.xlsx");

                    attachmentPart.attachFile(f);
                    textPart.setText("Lost device lista elk??sz??lt");
                    multipart.addBodyPart(textPart);
                    multipart.addBodyPart(attachmentPart);

                } catch (IOException e) {

                    e.printStackTrace();

                }

                message.setContent(multipart);

                System.out.println("sending...");
                // Send message
                Transport.send(message);
                System.out.println("Sent message successfully....");
            } catch (MessagingException mex) {
                mex.printStackTrace();
            }

        }catch (Exception ex)
        {
            System.out.println("hiba"+ex);
        }
    }
}
