package br.com.sexteto.Project.Funeral.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.sexteto.Project.Funeral.Repository.UserRepository;
import br.com.sexteto.Project.Funeral.exception.ConflictException;
import br.com.sexteto.Project.Funeral.exception.NotFoundException;
import br.com.sexteto.Project.Funeral.model.UserModel;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Cria ou atualiza um usuário, garantindo que nome de usuário e e-mail sejam
     * únicos.
     * 
     * @param userModel Instância com dados do usuário
     * @return Usuário persistido no banco
     */
    public UserModel createOrUpdate(UserModel userModel) {
        validateUniqueUsernameAndEmail(userModel);
        return userRepository.save(userModel);
    }

    /**
     * Retorna todos os usuários cadastrados.
     */
    public Page<UserModel> findAll() {
        int page = 0;
        int size = 10;
        PageRequest pageRequest = PageRequest.of(
                page,
                size,
                Sort.Direction.ASC,
                "username");
        return new PageImpl<>(
                userRepository.findAll(),
                pageRequest, size);
    }

    /**
     * Busca um usuário por ID, retornando Optional vazio se não encontrado.
     * 
     * @param id ID do usuário
     * @return Optional<UserModel>
     */
    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    /**
     * Busca um usuário por ID ou lança exceção se não existir.
     * 
     * @param id ID do usuário
     * @return Usuário encontrado
     */
    public UserModel findByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }

    /**
     * Remove um usuário do banco de dados.
     * 
     * @param userModel Usuário a ser removido
     */
    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    /**
     * Valida se o username e email são únicos, considerando updates.
     * 
     * @param userModel Usuário a ser validado
     */
    private void validateUniqueUsernameAndEmail(UserModel userModel) {
        // Verifica se o username já está em uso por outro usuário
        boolean usernameExists = userRepository.findByUsername(userModel.getUsername())
                .filter(existingUser -> !existingUser.getId().equals(userModel.getId()))
                .isPresent();

        if (usernameExists) {
            throw new ConflictException("Nome de usuário já em uso.");
        }

        // Verifica se o email já está em uso por outro usuário
        boolean emailExists = userRepository.findByEmail(userModel.getEmail())
                .filter(existingUser -> !existingUser.getId().equals(userModel.getId()))
                .isPresent();

        if (emailExists) {
            throw new ConflictException("Email já em uso.");
        }
    }
}
