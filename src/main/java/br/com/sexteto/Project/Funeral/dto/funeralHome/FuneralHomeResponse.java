package br.com.sexteto.Project.Funeral.dto.funeralHome;

import java.util.UUID;

public record FuneralHomeResponse(UUID id, String name, String address, String phone) {
}
