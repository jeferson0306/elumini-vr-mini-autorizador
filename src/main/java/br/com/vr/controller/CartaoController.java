package br.com.vr.controller;

import br.com.vr.domain.request.CartaoRequest;
import br.com.vr.domain.request.TransacaoRequest;
import br.com.vr.domain.response.CartaoResponse;
import br.com.vr.service.CartaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService cartaoService;

    @PostMapping
    public ResponseEntity<CartaoResponse> criarNovoCartao(@RequestBody final CartaoRequest cartaoRequest) {
        log.info("Inicia o fluxo para criar um novo cartao com a requisicao: {}", cartaoRequest);
        var response = cartaoService.criarNovoCartao(cartaoRequest);
        return status(201).body(response);
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<Double> consultarSaldo(@PathVariable final String numeroCartao) {
        log.info("Inicia o fluxo para consultar saldo do cartao: {}", numeroCartao);
        return cartaoService.consultarSaldo(numeroCartao);
    }

    @PostMapping("/transacoes")
    public ResponseEntity<String> realizarTransacao(@RequestBody final TransacaoRequest transacaoRequest) {
        log.info("Inicia o fluxo para realizar transacao com a requisicao: {}", transacaoRequest);
        cartaoService.realizarTransacao(transacaoRequest);
        return status(201).body("OK");
    }
}
