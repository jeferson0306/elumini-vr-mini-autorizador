package br.com.vr.domain.exception.handler;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.request.TransacaoRequest;

import java.util.List;

public class TransacaoChain {

    private final List<TransacaoHandler> handlers;

    public TransacaoChain(List<TransacaoHandler> handlers) {
        this.handlers = handlers;
    }

    public void process(TransacaoRequest request, Cartao cartao) {
        handlers.forEach(handler -> handler.handle(request, cartao));
    }
}
