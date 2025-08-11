package br.com.sexteto.Project.Funeral.dto.funeralHome;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuneralHomeUpdateRequest(@NotBlank @Size(min = 3, max = 100) String name,
                @NotBlank @Size(min = 5, max = 200) String address,
                @NotBlank @Size(min = 10, max = 20) String phone) {
}
