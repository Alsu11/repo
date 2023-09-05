package ru.itis.utils;

import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import reactor.core.publisher.Mono;
import ru.itis.dto.EmailDto;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
@Slf4j
public class EmailUtil {

    @Autowired
    private final JavaMailSender mailSender;

    @Autowired
    private FreeMarkerConfigurer freeMarkerConfiguration;

    @Value("${spring.mail.username}")
    private String from;

    public void sendMail(EmailDto emailDto) {
        Mono.fromCallable(() -> {
                            try {
                                String mailTemplate = FreeMarkerTemplateUtils.processTemplateIntoString(
                                        freeMarkerConfiguration.getConfiguration()
                                                .getTemplate(emailDto.getTemplateName() + ".ftl"),
                                        emailDto.getData());

                                MimeMessagePreparator preparator = mimeMessage -> {
                                    MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
                                    messageHelper.setSubject(emailDto.getSubject());
                                    messageHelper.setText(mailTemplate, true);
                                    messageHelper.setTo(emailDto.getTo());
                                    messageHelper.setFrom(from);
                                };
                                mailSender.send(preparator);
                                return true;
                            } catch (IOException | TemplateException e) {
                                log.error("Couldn't send email due to " + e.getMessage());
                                return false;
                            }

                        }

                )
                .subscribe(result -> log.info("Mail sending was {}", result));
    }
}
