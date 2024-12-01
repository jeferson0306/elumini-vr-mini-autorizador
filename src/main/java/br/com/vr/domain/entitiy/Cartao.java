package br.com.vr.domain.entitiy;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String numeroCartao;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private Double saldo = 500.0;

}
