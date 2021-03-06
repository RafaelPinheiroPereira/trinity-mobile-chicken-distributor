package com.br.tmchickendistributor.data.model;

import com.br.tmchickendistributor.data.realm.LocalidadeORM;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Localidade implements Serializable {



    private String nome;
    LocalidadeID pkLocalidade;
    private String id;




    public Localidade(LocalidadeORM localidadeORM) {

        this.id=localidadeORM.getId();
        this.nome = localidadeORM.getNome();

    }
}
