package br.com.vr.controller.handler;

import br.com.vr.domain.exception.CartaoException;
import br.com.vr.domain.exception.TransacaoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoException.class)
    public ResponseEntity<Object> handleRegraNegocioException(final CartaoException cartaoException) {
        final var response = new HashMap<>();
        response.put("numeroCartao", cartaoException.getNumeroCartao());
        response.put("senha", cartaoException.getSenha());
        return status(422).body(response);
    }

    @ExceptionHandler(TransacaoException.class)
    public ResponseEntity<String> handleTransacaoException(final TransacaoException transacaoException) {
        log.error("Erro na transacao: {}", transacaoException.getMessage());
        return status(422).body(transacaoException.getMessage());
    }

}
