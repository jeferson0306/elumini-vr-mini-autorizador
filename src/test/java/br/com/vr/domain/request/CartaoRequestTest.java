package br.com.vr.domain.request;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartaoRequestTest {

    @Test
    void testCartaoRequestProperties() {
        final var request = new CartaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenha("1234");
        assertEquals("6549873025634501", request.getNumeroCartao());
        assertEquals("1234", request.getSenha());
    }

    @Test
    void testCartaoRequestEquality() {
        final var request1 = new CartaoRequest();
        request1.setNumeroCartao("6549873025634501");
        final var request2 = new CartaoRequest();
        request2.setNumeroCartao("6549873025634501");
        assertEquals(request1, request2);
    }

    @Test
    void testCartaoRequestToString() {
        final var request = new CartaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenha("1234");
        final var expectedString = "CartaoRequest(numeroCartao=6549873025634501, senha=1234)";
        assertEquals(expectedString, request.toString());
    }
}
