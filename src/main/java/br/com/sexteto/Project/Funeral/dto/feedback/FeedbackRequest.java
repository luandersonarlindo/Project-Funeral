package br.com.sexteto.Project.Funeral.dto.feedback;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record FeedbackRequest(@NotBlank @Size(min = 3, max = 500) String comment, @Min(1) @Max(5) int rating,
                @NotNull UUID userId, @NotNull UUID funeralHomeId) {

}
