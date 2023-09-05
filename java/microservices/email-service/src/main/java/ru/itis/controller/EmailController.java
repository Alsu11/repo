package ru.itis.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.controller.api.EmailApi;
import ru.itis.dto.EmailDto;
import ru.itis.utils.EmailUtil;

@RestController
@RequiredArgsConstructor
public class EmailController implements EmailApi {

    private final EmailUtil emailUtil;

    @Override
    public void sendEmail(EmailDto emailDto) {

        emailUtil.sendMail(emailDto);
    }
}
