package br.com.vr.service;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.exception.CartaoException;
import br.com.vr.domain.exception.TransacaoException;
import br.com.vr.domain.request.CartaoRequest;
import br.com.vr.domain.request.TransacaoRequest;
import br.com.vr.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CartaoServiceTest {

    @Mock
    private CartaoRepository cartaoRepository;

    @InjectMocks
    private CartaoService cartaoService;

    private Cartao cartao;
    private CartaoRequest cartaoRequest;
    private TransacaoRequest transacaoRequest;

    @BeforeEach
    void setUp() {
        openMocks(this);
        cartaoRequest = new CartaoRequest();
        cartaoRequest.setNumeroCartao("6549873025634501");
        cartaoRequest.setSenha("1234");
        transacaoRequest = new TransacaoRequest();
        transacaoRequest.setNumeroCartao("6549873025634501");
        transacaoRequest.setSenhaCartao("1234");
        transacaoRequest.setValor(100.0);
        cartao = new Cartao();
        cartao.setNumeroCartao("6549873025634501");
        cartao.setSenha("1234");
        cartao.setSaldo(500.0);
    }

    @Test
    void testCriarNovoCartao_Success() {
        when(cartaoRepository.findByNumeroCartao(cartaoRequest.getNumeroCartao())).thenReturn(Optional.empty());
        when(cartaoRepository.save(any(Cartao.class))).thenReturn(cartao);
        final var response = cartaoService.criarNovoCartao(cartaoRequest);
        assertNotNull(response);
        assertEquals("6549873025634501", response.getNumeroCartao());
        assertEquals("1234", response.getSenha());
        verify(cartaoRepository, times(1)).save(any(Cartao.class));
    }

    @Test
    void testCriarNovoCartao_CartaoJaExiste() {
        when(cartaoRepository.findByNumeroCartao(cartaoRequest.getNumeroCartao())).thenReturn(Optional.of(cartao));
        assertThrows(CartaoException.class, () -> cartaoService.criarNovoCartao(cartaoRequest));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    @Test
    void testConsultarSaldo_Success() {
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(Optional.of(cartao));
        final var response = cartaoService.consultarSaldo(cartao.getNumeroCartao());
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(500.0, response.getBody());
    }

    @Test
    void testConsultarSaldo_CartaoNaoExiste() {
        when(cartaoRepository.findByNumeroCartao(cartao.getNumeroCartao())).thenReturn(Optional.empty());
        final var response = cartaoService.consultarSaldo(cartao.getNumeroCartao());
        assertNotNull(response);
        assertEquals(404, response.getStatusCode().value());
        assertNull(response.getBody());
    }

    @Test
    void testRealizarTransacao_Success() {
        when(cartaoRepository.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(Optional.of(cartao));
        cartaoService.realizarTransacao(transacaoRequest);
        final var cartaoCaptor = ArgumentCaptor.forClass(Cartao.class);
        verify(cartaoRepository, times(1)).save(cartaoCaptor.capture());
        final var updatedCartao = cartaoCaptor.getValue();
        assertEquals(400.0, updatedCartao.getSaldo());
    }

    @Test
    void testRealizarTransacao_CartaoNaoExiste() {
        when(cartaoRepository.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(Optional.empty());
        assertThrows(TransacaoException.class, () -> cartaoService.realizarTransacao(transacaoRequest));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    @Test
    void testRealizarTransacao_SenhaInvalida() {
        cartao.setSenha("wrong-password");
        when(cartaoRepository.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(Optional.of(cartao));
        assertThrows(TransacaoException.class, () -> cartaoService.realizarTransacao(transacaoRequest));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }

    @Test
    void testRealizarTransacao_SaldoInsuficiente() {
        transacaoRequest.setValor(600.0);
        when(cartaoRepository.findByNumeroCartao(transacaoRequest.getNumeroCartao())).thenReturn(Optional.of(cartao));
        assertThrows(TransacaoException.class, () -> cartaoService.realizarTransacao(transacaoRequest));
        verify(cartaoRepository, never()).save(any(Cartao.class));
    }
}
