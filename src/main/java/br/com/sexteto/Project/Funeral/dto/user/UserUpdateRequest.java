package br.com.sexteto.Project.Funeral.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para atualização de dados do usuário.
 */
public record UserUpdateRequest(@NotBlank @Size(min = 3, max = 50) String username,
        @NotBlank @Email String email) {
}
