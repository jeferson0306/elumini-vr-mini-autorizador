package br.com.vr.domain.request;

import lombok.Data;

@Data
public class TransacaoRequest {

    private String numeroCartao;
    private String senhaCartao;
    private Double valor;

}
