package br.com.sexteto.Project.Funeral.dto.user;

import java.util.UUID;

public record UserResponse(
                UUID id,
                String username,
                String email) {
}
