package br.com.vr.domain.request;

import lombok.Data;

@Data
public class CartaoRequest {

    private String numeroCartao;
    private String senha;
}
