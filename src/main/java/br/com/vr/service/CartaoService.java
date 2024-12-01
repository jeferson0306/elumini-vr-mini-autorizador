package br.com.vr.service;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.exception.CartaoException;
import br.com.vr.domain.exception.TransacaoException;
import br.com.vr.domain.exception.handler.SaldoHandler;
import br.com.vr.domain.exception.handler.SenhaHandler;
import br.com.vr.domain.exception.handler.TransacaoChain;
import br.com.vr.domain.request.CartaoRequest;
import br.com.vr.domain.request.TransacaoRequest;
import br.com.vr.domain.response.CartaoResponse;
import br.com.vr.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoResponse criarNovoCartao(final CartaoRequest cartaoRequest) {

        cartaoRepository.findByNumeroCartao(cartaoRequest.getNumeroCartao()).ifPresent(cartao -> {
            throw new CartaoException("Este cartao ja foi criado anteriormente com os dados: ", cartao.getNumeroCartao(), cartao.getSenha());
        });

        final var cartao = new Cartao();
        cartao.setNumeroCartao(cartaoRequest.getNumeroCartao());
        cartao.setSenha(cartaoRequest.getSenha());

        log.info("Cartão criado com sucesso: {}", cartao);
        cartaoRepository.save(cartao);

        final var response = new CartaoResponse();
        response.setNumeroCartao(cartao.getNumeroCartao());
        response.setSenha(cartao.getSenha());
        return response;
    }

    public ResponseEntity<Double> consultarSaldo(final String numeroCartao) {
        return cartaoRepository.findByNumeroCartao(numeroCartao).map(cartao -> ok(cartao.getSaldo())).orElse(status(404).build());
    }

    @Transactional
    public void realizarTransacao(final TransacaoRequest transacaoRequest) {

        final var cartao = cartaoRepository.findByNumeroCartao(transacaoRequest.getNumeroCartao()).orElseThrow(() -> new TransacaoException("CARTAO_INEXISTENTE"));

        new TransacaoChain(List.of(new SenhaHandler(), new SaldoHandler())).process(transacaoRequest, cartao);
        cartao.setSaldo(cartao.getSaldo() - transacaoRequest.getValor());

        log.info("Transacao realizada com sucesso para o cartao: {} - Novo saldo: {}", cartao.getNumeroCartao(), cartao.getSaldo());
        cartaoRepository.save(cartao);
    }
}
