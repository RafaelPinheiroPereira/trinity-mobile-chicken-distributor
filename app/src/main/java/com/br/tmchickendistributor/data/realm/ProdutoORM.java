package com.br.tmchickendistributor.data.realm;

import com.br.tmchickendistributor.data.model.Produto;
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
public class ProdutoORM extends RealmObject implements Serializable {

  @PrimaryKey private long id;

  private String nome;

  private double quantidade;
  private long idEmpresa;
  private long idNucleo;

  public ProdutoORM(Produto produto) {
    this.id = produto.getId();
    this.nome = produto.getNome();

    this.quantidade = produto.getQuantidade();
    this.idEmpresa = produto.getIdEmpresa();
    this.idNucleo = produto.getIdNucleo();
  }
}
