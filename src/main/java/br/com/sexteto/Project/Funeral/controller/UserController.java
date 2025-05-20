package br.com.sexteto.Project.Funeral.controller;

import br.com.sexteto.Project.Funeral.dto.user.UserRequest;
import br.com.sexteto.Project.Funeral.dto.user.UserResponse;
import br.com.sexteto.Project.Funeral.dto.user.UserUpdateRequest;
import br.com.sexteto.Project.Funeral.model.UserModel;
import br.com.sexteto.Project.Funeral.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequest userRequest) {
        // Converte o DTO de entrada para o modelo de domínio
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRequest, userModel);

        // Salva o novo usuário no sistema
        userService.createOrUpdate(userModel);
        return new ResponseEntity<>(HttpStatus.CREATED); // Retorna status 201 (Created)
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        // Busca todos os usuários e converte para o formato de resposta
        var users = userService.findAll();
        var responseList = users.stream()
                .map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseList); // Retorna status 200 (OK) com a lista
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable UUID id) {
        // Busca um único usuário ou lança exceção se não encontrado
        var user = userService.findByIdOrThrow(id);
        var response = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response); // Retorna status 200 (OK)
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id,
            @RequestBody @Valid UserUpdateRequest request) {
        // Garante que o usuário existe antes de atualizar
        var userModel = userService.findByIdOrThrow(id);
        BeanUtils.copyProperties(request, userModel);

        // Atualiza os dados do usuário
        userService.createOrUpdate(userModel);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 (No Content)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Garante que o usuário existe antes de deletar
        var user = userService.findByIdOrThrow(id);

        // Remove o usuário do sistema
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 (No Content)
    }
}
