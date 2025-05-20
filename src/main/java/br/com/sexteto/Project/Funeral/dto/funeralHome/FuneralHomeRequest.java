package br.com.sexteto.Project.Funeral.dto.funeralHome;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO usado para criação de nova Casa Funerária.
 */
public record FuneralHomeRequest(@NotBlank @Size(min = 3, max = 100) String name, @NotBlank String cnpj,
    @NotBlank String address, @NotBlank String phone) {

}