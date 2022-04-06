package com.algaworks.moneyapi.model;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class Endereco {

    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String cidade;
    private String estado;

}
