package br.com.vr.domain.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransacaoRequestTest {

    @Test
    void testTransacaoRequestProperties() {
        final var request = new TransacaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenhaCartao("1234");
        request.setValor(100.0);
        assertEquals("6549873025634501", request.getNumeroCartao());
        assertEquals("1234", request.getSenhaCartao());
        assertEquals(100.0, request.getValor());
    }

    @Test
    void testTransacaoRequestEquality() {
        final var request1 = new TransacaoRequest();
        request1.setNumeroCartao("6549873025634501");
        final var request2 = new TransacaoRequest();
        request2.setNumeroCartao("6549873025634501");
        assertEquals(request1, request2);
    }

    @Test
    void testTransacaoRequestToString() {
        final var request = new TransacaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenhaCartao("1234");
        request.setValor(100.0);
        final var expectedString = "TransacaoRequest(numeroCartao=6549873025634501, senhaCartao=1234, valor=100.0)";
        assertEquals(expectedString, request.toString());
    }
}
