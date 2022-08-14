package ru.itis.utils.confirm;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class EmailUtil {

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(String to, String subject, String templateName, Map<String, Object> data) throws IOException, TemplateException, MessagingException {

        Configuration configuration = freeMarkerConfigurer.getConfiguration();

        Template template = configuration.getTemplate(templateName + ".ftlh");
        String mailText = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(mailText);
        mailSender.send(message);
    }
}

