package br.com.sexteto.Project.Funeral.controller;

import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeRequest;
import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeResponse;
import br.com.sexteto.Project.Funeral.dto.funeralHome.FuneralHomeUpdateRequest;
import br.com.sexteto.Project.Funeral.model.FuneralHomeModel;
import br.com.sexteto.Project.Funeral.service.FuneralHomeService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funeral-homes")
public class FuneralHomeController {

    @Autowired
    private FuneralHomeService funeralHomeService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid FuneralHomeRequest funeralHomeRequest) {
        // Converte o DTO de requisição em um modelo de domínio
        var model = new FuneralHomeModel();
        BeanUtils.copyProperties(funeralHomeRequest, model);

        // Salva a funerária no banco
        funeralHomeService.createOrUpdate(model);
        return ResponseEntity.status(HttpStatus.CREATED).build(); // 201 Created
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Garante que a funerária existe antes de remover
        var funeralHome = funeralHomeService.findByIdOrThrow(id);

        // Deleta a funerária
        funeralHomeService.delete(funeralHome);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
