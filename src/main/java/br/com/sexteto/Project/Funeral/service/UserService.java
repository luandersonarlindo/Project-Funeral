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

    public UserModel createOrUpdate(UserModel userModel) {
        validateUniqueUsernameAndEmail(userModel);
        return userRepository.save(userModel);
    }

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

    public Optional<UserModel> findById(UUID id) {
        return userRepository.findById(id);
    }

    public UserModel findByIdOrThrow(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado!"));
    }

    public void delete(UserModel userModel) {
        userRepository.delete(userModel);
    }

    private void validateUniqueUsernameAndEmail(UserModel userModel) {
        boolean usernameExists = userRepository.findByUsername(userModel.getUsername())
                .filter(existingUser -> !existingUser.getId().equals(userModel.getId()))
                .isPresent();

        if (usernameExists) {
            throw new ConflictException("Nome de usuário já em uso.");
        }

        boolean emailExists = userRepository.findByEmail(userModel.getEmail())
                .filter(existingUser -> !existingUser.getId().equals(userModel.getId()))
                .isPresent();

        if (emailExists) {
            throw new ConflictException("Email já em uso.");
        }
    }
}
