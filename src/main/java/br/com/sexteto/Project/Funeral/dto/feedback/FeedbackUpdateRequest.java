package br.com.sexteto.Project.Funeral.dto.feedback;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record FeedbackUpdateRequest(@NotBlank @Size(max = 500) String comment, @Min(1) @Max(5) int rating) {
}
