package br.com.vr.domain.entitiy;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CartaoTest {

    @Test
    void testCartaoProperties() {
        final var cartao = new Cartao();
        cartao.setId(1L);
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);
        assertEquals(1L, cartao.getId());
        assertEquals("6549873025634501", cartao.getNumeroCartao());
        assertEquals("1234", cartao.getSenha());
        assertEquals(500.0, cartao.getSaldo());
    }

    @Test
    void testCartaoEquality() {
        final var cartao1 = new Cartao();
        cartao1.setNumeroCartao("6549873025634501");
        final var cartao2 = new Cartao();
        cartao2.setNumeroCartao("6549873025634501");
        assertEquals(cartao1, cartao2);
    }

    @Test
    void testCartaoToString() {
        final var cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);
        final var expectedString = "Cartao(id=null, numeroCartao=6549873025634501, senha=1234, saldo=500.0)";
        assertEquals(expectedString, cartao.toString());
    }
}
