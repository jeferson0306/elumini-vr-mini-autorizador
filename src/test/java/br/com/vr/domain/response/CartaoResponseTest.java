package br.com.vr.domain.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartaoResponseTest {

    @Test
    void testCartaoResponseProperties() {
        final var response = new CartaoResponse();
        response.setNumeroCartao("6549873025634501");
        response.setSenha("1234");
        assertEquals("6549873025634501", response.getNumeroCartao());
        assertEquals("1234", response.getSenha());
    }

    @Test
    void testCartaoResponseEquality() {
        final var response1 = new CartaoResponse();
        response1.setNumeroCartao("6549873025634501");
        final var response2 = new CartaoResponse();
        response2.setNumeroCartao("6549873025634501");
        assertEquals(response1, response2);
    }

    @Test
    void testCartaoResponseToString() {
        final var response = new CartaoResponse();
        response.setNumeroCartao("6549873025634501");
        response.setSenha("1234");
        final var expectedString = "CartaoResponse(numeroCartao=6549873025634501, senha=1234)";
        assertEquals(expectedString, response.toString());
    }
}
