package br.com.sexteto.Project.Funeral.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Estrutura de resposta padronizada para mensagens de erro.
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    
    public String message;
    public String code;

}
