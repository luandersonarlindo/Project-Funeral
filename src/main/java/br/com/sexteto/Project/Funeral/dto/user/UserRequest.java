package br.com.sexteto.Project.Funeral.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequest(@NotBlank @Size(min = 3, max = 50) String username,
                @NotBlank @Email String email, @NotBlank @Size(min = 6, max = 20) String password) {
}
