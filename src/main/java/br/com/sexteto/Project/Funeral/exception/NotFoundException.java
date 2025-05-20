package br.com.sexteto.Project.Funeral.exception;

/**
 * Exceção lançada quando uma entidade não é encontrada (ex: usuário, funerária, etc.).
 */

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}