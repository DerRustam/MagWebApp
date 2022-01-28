package com.rustam.magbackend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestPackage {
    @NotEmpty
    @Size(min = 6, max = 32)
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9_$?!<>]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String password;

    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", flags = Pattern.Flag.UNICODE_CASE)
    private String firstName;

    @NotNull
    @Size(max = 1)
    private String gender;
}
