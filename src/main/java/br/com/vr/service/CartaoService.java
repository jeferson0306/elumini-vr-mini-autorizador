package br.com.vr.service;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.exception.CartaoException;
import br.com.vr.domain.exception.TransacaoException;
import br.com.vr.domain.request.CartaoRequest;
import br.com.vr.domain.request.TransacaoRequest;
import br.com.vr.domain.response.CartaoResponse;
import br.com.vr.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository cartaoRepository;

    public CartaoResponse criarNovoCartao(final CartaoRequest cartaoRequest) {

        final var optionalCartao = cartaoRepository.findByNumeroCartao(cartaoRequest.getNumeroCartao());

        if (optionalCartao.isPresent()) {
            final var cartaoExistente = optionalCartao.get();
            throw new CartaoException("Este cartao ja foi criado anteriormente com os dados: ", cartaoExistente.getNumeroCartao(), cartaoExistente.getSenha());
        }

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

        if (!cartao.getSenha().equals(transacaoRequest.getSenhaCartao())) {
            log.error("STATUS CODE: 422 - MENSAGEM: Senha invalida para o cartao {}", transacaoRequest.getNumeroCartao());
            throw new TransacaoException("SENHA_INVALIDA");
        }

        if (cartao.getSaldo() < transacaoRequest.getValor()) {
            log.error("STATUS CODE: 422 - MENSAGEM: Saldo insuficiente para o cartao {}", transacaoRequest.getNumeroCartao());
            throw new TransacaoException("SALDO_INSUFICIENTE");
        }

        cartao.setSaldo(cartao.getSaldo() - transacaoRequest.getValor());
        log.info("Transacao realizada com sucesso para o cartao: {} - Novo saldo: {}", cartao.getNumeroCartao(), cartao.getSaldo());
        cartaoRepository.save(cartao);
    }

}
