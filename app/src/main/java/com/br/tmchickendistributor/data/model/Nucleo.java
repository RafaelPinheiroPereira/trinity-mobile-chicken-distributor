package com.br.tmchickendistributor.data.model;

import com.br.tmchickendistributor.data.realm.NucleoORM;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Nucleo implements Serializable {

    private long id;

    private String nome;

    private String cnpj;

    private Date dataInicio;

    private Date dataFim;

    private String nomeEmpresa;
    private String telefone;
    private String endereco;

    private boolean ativo=false;

    public Nucleo(NucleoORM nucleoORM){
        this.id=nucleoORM.getId();
        this.nome=nucleoORM.getNome();
        this.cnpj=nucleoORM.getCnpj();
        this.dataInicio=nucleoORM.getDataInicio();
        this.dataInicio=nucleoORM.getDataFim();
        this.nomeEmpresa=nucleoORM.getNomeEmpresa();
        this.telefone=nucleoORM.getTelefone();
        this.endereco=nucleoORM.getEndereco();
        this.ativo=nucleoORM.isAtivo();
    }

    public Nucleo(final int id, final String nome) {
        this.id=id;
        this.nome=nome;
    }

    @Override
    public String toString() {
        return this.getNome();
    }
}
