package br.com.vr.domain.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@ResponseStatus(UNPROCESSABLE_ENTITY)
public class TransacaoException extends RuntimeException {

    public TransacaoException(String mensagem) {
        super(mensagem);
    }
}
