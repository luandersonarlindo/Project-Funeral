package br.com.sexteto.Project.Funeral.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeRequest;
import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeResponse;
import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeUpdateRequest;
import br.com.sexteto.Project.Funeral.model.FuneralHomeModel;
import br.com.sexteto.Project.Funeral.service.FuneralHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/funeral-homes")
public class FuneralHomeController {

    @Autowired
    private FuneralHomeService funeralHomeService;

    @Operation(description = "Cria uma nova funerária")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Funerária criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar funerária")
    })
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid FuneralHomeRequest funeralHomeRequest) {
        // Converte o DTO de requisição em um modelo de domínio
        var model = new FuneralHomeModel();
        BeanUtils.copyProperties(funeralHomeRequest, model);

        // Salva a funerária no banco
        funeralHomeService.createOrUpdate(model);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

    @Operation(description = "Retorna todas as funerárias")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de funerárias retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar funerárias")
    })
    @GetMapping
    public ResponseEntity<List<FuneralHomeResponse>> findAll() {
        // Busca todas as funerárias e converte para DTO de resposta
        var responseList = funeralHomeService.findAll()
                .stream()
                .map(funeralHome -> new FuneralHomeResponse(
                        funeralHome.getId(),
                        funeralHome.getName(),
                        funeralHome.getAddress(),
                        funeralHome.getPhone()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList); // 200 OK com a lista
    }

    @Operation(description = "Retorna uma funerária por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funerária encontrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Funerária não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<FuneralHomeResponse> getOne(@PathVariable UUID id) {
        // Busca uma funerária por ID ou lança exceção se não encontrar
        var funeralHome = funeralHomeService.findByIdOrThrow(id);
        var response = new FuneralHomeResponse(
                funeralHome.getId(),
                funeralHome.getName(),
                funeralHome.getAddress(),
                funeralHome.getPhone());
        return ResponseEntity.ok(response); // 200 OK
    }

    @Operation(description = "Atualiza uma funerária existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funerária atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar funerária")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
            @RequestBody @Valid FuneralHomeUpdateRequest request) {
        // Garante que a funerária existe antes de atualizar
        var funeralHomeModel = funeralHomeService.findByIdOrThrow(id);
        BeanUtils.copyProperties(request, funeralHomeModel);

        // Atualiza os dados da funerária
        funeralHomeService.createOrUpdate(funeralHomeModel);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @Operation(description = "Remove uma funerária existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funerária removida com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao remover funerária")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Garante que a funerária existe antes de remover
        var funeralHome = funeralHomeService.findByIdOrThrow(id);

        // Deleta a funerária
        funeralHomeService.delete(funeralHome);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
