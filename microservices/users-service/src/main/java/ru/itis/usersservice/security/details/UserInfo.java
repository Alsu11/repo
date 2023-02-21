package ru.itis.usersservice.security.details;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.usersservice.dto.enums.Role;
import ru.itis.usersservice.dto.enums.State;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfo {
    private String email;
    private String password;
    private State state;
    private Role role;
}
