package br.com.sexteto.Project.Funeral.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para atualização de um feedback existente.
 * Validações:
 * - comment: obrigatório, até 500 caracteres.
 * - rating: deve estar entre 1 e 5.
 */
public record FeedbackUpdateRequest(@NotBlank @Size(max = 500) String comment, @Min(1) @Max(5) int rating) {
}
