package ru.meinone.tokinoi_gallery_app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Username is empty")
    private String username;

    @NotBlank(message = "Password is empty")
    private String password;
}
