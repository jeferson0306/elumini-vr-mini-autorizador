package br.com.vr.domain.exception.handler;

import br.com.vr.domain.entitiy.Cartao;
import br.com.vr.domain.exception.TransacaoException;
import br.com.vr.domain.request.TransacaoRequest;

public class SaldoHandler implements TransacaoHandler {

    @Override
    public void handle(TransacaoRequest request, Cartao cartao) {
        validate(cartao.getSaldo() >= request.getValor());
    }

    private void validate(boolean condition) {
        if (!condition) {
            throw new TransacaoException("SALDO_INSUFICIENTE");
        }
    }
}
