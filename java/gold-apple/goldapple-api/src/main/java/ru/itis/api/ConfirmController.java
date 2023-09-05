package ru.itis.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itis.dto.UserDto;

@RequestMapping("/confirm")
public interface ConfirmController {

    @GetMapping("/{code}")
    ResponseEntity<UserDto> confirm(@PathVariable("code") String code);

}
