package com.br.tmchickendistributor.data.realm;

import com.br.tmchickendistributor.data.model.Conta;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContaORM  extends RealmObject implements Serializable {


    @PrimaryKey
    private String id;
    private String descricao;
    private String agencia;
    private String nunmeroConta;

    public ContaORM(Conta conta) {
        this.id = conta.getId();
        this.descricao = conta.getDescricao();
        this.agencia = conta.getAgencia();
        this.nunmeroConta = conta.getNumeroConta();

    }

}
