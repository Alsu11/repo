package ru.itis.usersservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.itis.usersservice.dto.EmailDto;

@FeignClient("email-service")
public interface EmailClient {
    @RequestMapping(value = "/email-sender", method = RequestMethod.POST)
    void sendEmail(EmailDto emailDto);
}
