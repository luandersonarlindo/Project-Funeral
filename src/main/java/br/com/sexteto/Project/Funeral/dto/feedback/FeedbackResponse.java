package br.com.sexteto.Project.Funeral.dto.feedback;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO de resposta com os dados públicos de um feedback.
 * Contém as informações que serão retornadas nas respostas da API.
 */
public record FeedbackResponse(UUID id, String comment, int rating, LocalDateTime createdAt, UUID userId,
                UUID funeralHomeId) {
}
