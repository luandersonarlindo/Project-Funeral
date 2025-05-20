package br.com.sexteto.Project.Funeral.dto.funeralHome;

import java.util.UUID;

/**
 * DTO de resposta com dados públicos da Casa Funerária.
 */
public record FuneralHomeResponse(UUID id, String name, String address, String phone) {

}
