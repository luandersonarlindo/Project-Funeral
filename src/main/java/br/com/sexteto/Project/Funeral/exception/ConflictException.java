package br.com.sexteto.Project.Funeral.exception;

/**
 * Exceção lançada quando há conflitos de dados (ex: duplicidade de email ou CNPJ).
 */

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
