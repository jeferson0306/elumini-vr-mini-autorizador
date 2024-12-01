package br.com.vr.domain.exception.handler;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.request.TransacaoRequest;

public interface TransacaoHandler {

    void handle(TransacaoRequest request, Cartao cartao);

}
