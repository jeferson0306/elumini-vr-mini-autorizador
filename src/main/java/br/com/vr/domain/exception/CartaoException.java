package br.com.vr.domain.exception;

import lombok.Getter;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

@Getter
@ResponseStatus(UNPROCESSABLE_ENTITY)
public class CartaoException extends RuntimeException {

    private final String numeroCartao;
    private final String senha;

    public CartaoException(String mensagem, String numeroCartao, String senha) {
        super(mensagem);
        this.numeroCartao = numeroCartao;
        this.senha = senha;
    }

}
