package br.com.vr.controller.handler;

import br.com.vr.domain.exception.CartaoException;
import br.com.vr.domain.exception.TransacaoException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void testHandleCartaoException() {
        final var exception = new CartaoException("Cartão já existe", "6549873025634501", "1234");
        final var response = handler.handleRegraNegocioException(exception);
        assertEquals(422, response.getStatusCode().value());
        assertEquals("6549873025634501", ((HashMap<?, ?>) response.getBody()).get("numeroCartao"));
        assertEquals("1234", ((HashMap<?, ?>) response.getBody()).get("senha"));
    }

    @Test
    void testHandleTransacaoException() {
        final var exception = new TransacaoException("SALDO_INSUFICIENTE");
        final var response = handler.handleTransacaoException(exception);
        assertEquals(422, response.getStatusCode().value());
        assertEquals("SALDO_INSUFICIENTE", response.getBody());
    }
}
