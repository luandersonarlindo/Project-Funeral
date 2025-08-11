package br.com.sexteto.Project.Funeral.dto.feedback;

import java.time.LocalDateTime;
import java.util.UUID;

public record FeedbackResponse(UUID id, String comment, int rating, LocalDateTime createdAt, UUID userId,
        UUID funeralHomeId) {
}
