package br.com.sexteto.Project.Funeral.dto.feedback;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para criação de um novo feedback.
 * Validações:
 * - comment: obrigatório, entre 3 e 500 caracteres.
 * - rating: obrigatório, entre 1 e 5.
 * - userId e funeralHomeId: não podem ser nulos.
 */
public record FeedbackRequest(@NotBlank @Size(min = 3, max = 500) String comment, @Min(1) @Max(5) int rating,
        @NotNull UUID userId, @NotNull UUID funeralHomeId) {

}
