package br.com.vr.controller;

import br.com.vr.domain.request.CartaoRequest;
import br.com.vr.domain.request.TransacaoRequest;
import br.com.vr.domain.response.CartaoResponse;
import br.com.vr.service.CartaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.http.ResponseEntity.ok;

class CartaoControllerTest {

    @Mock
    private CartaoService cartaoService;

    private final CartaoController cartaoController;

    public CartaoControllerTest() {
        openMocks(this);
        cartaoController = new CartaoController(cartaoService);
    }

    @Test
    void testCriarNovoCartao() {
        final var request = new CartaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenha("1234");
        final var response = new CartaoResponse();
        response.setNumeroCartao("6549873025634501");
        response.setSenha("1234");
        when(cartaoService.criarNovoCartao(request)).thenReturn(response);
        final var result = cartaoController.criarNovoCartao(request);
        assertEquals(201, result.getStatusCode().value());
        assertEquals("6549873025634501", requireNonNull(result.getBody()).getNumeroCartao());
        assertEquals("1234", result.getBody().getSenha());
        verify(cartaoService, times(1)).criarNovoCartao(request);
    }

    @Test
    void testConsultarSaldo() {
        final var numeroCartao = "6549873025634501";
        when(cartaoService.consultarSaldo(numeroCartao)).thenReturn(ok(500.0));
        final var result = cartaoController.consultarSaldo(numeroCartao);
        assertEquals(200, result.getStatusCode().value());
        assertEquals(500.0, result.getBody());
        verify(cartaoService, times(1)).consultarSaldo(numeroCartao);
    }

    @Test
    void testRealizarTransacao() {
        final var request = new TransacaoRequest();
        request.setNumeroCartao("6549873025634501");
        request.setSenhaCartao("1234");
        request.setValor(100.0);
        doNothing().when(cartaoService).realizarTransacao(request);
        final var result = cartaoController.realizarTransacao(request);
        assertEquals(201, result.getStatusCode().value());
        assertEquals("OK", result.getBody());
        verify(cartaoService, times(1)).realizarTransacao(request);
    }
}
