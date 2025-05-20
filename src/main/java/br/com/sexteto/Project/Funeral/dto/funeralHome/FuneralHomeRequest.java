package br.com.sexteto.Project.Funeral.dto.funeralHome;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record FuneralHomeRequest(@NotBlank @Size(min = 3, max = 100) String name,
        @NotBlank @Size(min = 10, max = 200) String address, @NotBlank @Size(min = 10, max = 20) String phone,
        @NotBlank @Size(min = 14, max = 18) String cnpj) {
}
