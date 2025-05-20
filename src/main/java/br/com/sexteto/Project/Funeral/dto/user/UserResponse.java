package br.com.sexteto.Project.Funeral.dto.user;

import java.util.UUID;

/**
 * DTO de resposta com dados públicos do usuário.
 */
public record UserResponse(
        UUID id,
        String username,
        String email
) {}
