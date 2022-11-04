package com.naekang.wealgo.dto;

import com.naekang.wealgo.type.UserRole;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDTO {

    @NotNull(message = "이메일을 입력해주세요.")
    private String email;

    @NotNull(message = "Solved.ac 닉네임을 입력해주세요.")
    private String username;

    @NotNull(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,30}$",
        message = "비밀번호는 8-30 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
    private String password;

    @NotNull(message = "다시 한번 비밀번호를 입력해주세요.")
    private String checkedPassword;

    private UserRole role;

}
