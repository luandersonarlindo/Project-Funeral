package br.com.sexteto.Project.Funeral.controller;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import br.com.sexteto.Project.Funeral.dto.user.UserRequest;
import br.com.sexteto.Project.Funeral.dto.user.UserResponse;
import br.com.sexteto.Project.Funeral.dto.user.UserUpdateRequest;
import br.com.sexteto.Project.Funeral.model.UserModel;
import br.com.sexteto.Project.Funeral.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(description = "Cria um novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao criar usuário")
    })
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid UserRequest userRequest) {
        // Converte o DTO de entrada para o modelo de domínio
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRequest, userModel);

        // Salva o novo usuário no sistema
        userService.createOrUpdate(userModel);
        return new ResponseEntity<>(HttpStatus.CREATED); // Retorna status 201 (Created)
    }

    @Operation(description = "Retorna todos os usuários")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao buscar usuários")
    })
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAll() {
        // Busca todos os usuários e converte para o formato de resposta
        Page<UserModel> users = userService.findAll();
        Page<UserResponse> responsePage = users.map(user -> new UserResponse(user.getId(), user.getUsername(), user.getEmail()));

        return ResponseEntity.ok(responsePage); // Retorna status 200 (OK) com a página
    }

    @Operation(description = "Retorna um usuário específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "400", description = "Usuário não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getOne(@PathVariable UUID id) {
        // Busca um único usuário ou lança exceção se não encontrado
        var user = userService.findByIdOrThrow(id);
        var response = new UserResponse(user.getId(), user.getUsername(), user.getEmail());
        return ResponseEntity.ok(response); // Retorna status 200 (OK)
    }

    @Operation(description = "Atualiza um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao atualizar usuário")
    })
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

    @Operation(description = "Remove um usuário existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro ao remover usuário")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        // Garante que o usuário existe antes de deletar
        var user = userService.findByIdOrThrow(id);

        // Remove o usuário do sistema
        userService.delete(user);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Retorna status 204 (No Content)
    }
}
