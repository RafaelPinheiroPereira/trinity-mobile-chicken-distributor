package com.br.tmchickendistributor.ui.mvp.venda;

import android.app.Activity;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import androidx.appcompat.app.AlertDialog;
import com.br.tmchickendistributor.data.model.Cliente;
import com.br.tmchickendistributor.data.model.Empresa;
import com.br.tmchickendistributor.data.model.Funcionario;
import com.br.tmchickendistributor.data.model.Impressora;
import com.br.tmchickendistributor.data.model.ItemPedido;
import com.br.tmchickendistributor.data.model.Nucleo;
import com.br.tmchickendistributor.data.model.Pedido;
import com.br.tmchickendistributor.data.model.Preco;
import com.br.tmchickendistributor.data.model.Produto;
import com.br.tmchickendistributor.data.model.Unidade;
import com.br.tmchickendistributor.data.realm.PedidoORM;
import com.br.tmchickendistributor.ui.mvp.venda.IVendaMVP.IView;
import com.br.tmchickendistributor.util.DateUtils;
import com.br.tmchickendistributor.util.DriveServiceHelper;
import com.br.tmchickendistributor.util.ImpressoraUtil;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Presenter implements IVendaMVP.IPresenter {

    Cliente cliente;

    ItemPedido itemPedido;

    List<ItemPedido> itens = new ArrayList<>();

    IVendaMVP.IModel mModel;

    IVendaMVP.IView mView;

    Pedido pedido;

    Preco preco;

    Produto produtoSelecionado;

    BigDecimal quantidadeProdutos;



    BigDecimal totalDaVenda;

    BigDecimal totalProductValue;

    Unidade unidadeSelecionada;

    ImpressoraUtil mImpressoraUtil;

    int bicos;

    String loteSelecionado;

    private AlertDialog mAlertDialog;

    private DriveServiceHelper mDriveServiceHelper;





    public Presenter(final IView view) {
        mView = view;
        mModel = new Model(this);
        mImpressoraUtil = new ImpressoraUtil((Activity) getContext());
    }

    @Override
    public void atualizarActCodigoProduto() {
        this.mView.updateActCodigoProduto();
    }

    @Override
    public void atualizarProdutoSelecionadoView() {
        this.mView.atualizarViewsDoProdutoSelecionado();
    }

    @Override
    public void exibirBotaoFotografar() {
        this.mView.exibirBotaoFotografar();
    }

    @Override
    public void atualizarRecyclerItens() {
        this.mView.updateRecyclerItens();
    }

    @Override
    public void atualizarSpinnerLotes() {
        this.mView.atualizarSpinnerLotes();
    }

    @Override
    public long configurarSequenceDoPedido() {
        return this.mModel.configurarSequenceDoPedido();
    }

    @Override
    public int getBicos() {
        return bicos;
    }

    @Override
    public Empresa pesquisarEmpresaRegistrada() {
        return this.mModel.pesquisarEmpresaRegistrada();
    }

    @Override
    public DriveServiceHelper getDriveServiceHelper() {
        return mDriveServiceHelper;
    }

    @Override
    public void setDriveServiceHelper(final DriveServiceHelper driveServiceHelper) {
        mDriveServiceHelper = driveServiceHelper;
    }

    @Override
    public void error(final String msg) {
        this.mView.error(msg);
    }

    @Override
    public void esperarPorConexao() {
        if (this.mImpressoraUtil.esperarPorConexao(this.getImpressora())) {
            this.mView.exibirBotaoImprimir();
        }
    }



    @Override
    public void setBicos(final int bicos) {
        this.bicos = bicos;
    }

    @Override
    public void dissmis() {
        this.mView.dissmiss();
    }

    @Override
    public void atualizarPedido(final Pedido orderSale) {
        this.mModel.copyOrUpdateSaleOrder(orderSale);
    }

    @Override
    public AlertDialog getAlertDialog() {
        return mAlertDialog;
    }

    @Override
    public Double calcularTotalDaVenda() {
        if (VERSION.SDK_INT >= VERSION_CODES.N) {

            return new BigDecimal(getItens().stream().mapToDouble(ItemPedido::getValorTotal).sum())
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                    .doubleValue();
        } else {
            double valorTotalVenda = 0.0;
            for (ItemPedido itemPedido : getItens()) {
                valorTotalVenda += itemPedido.getValorTotal();
            }
            return new BigDecimal(valorTotalVenda)
                    .setScale(2, BigDecimal.ROUND_HALF_DOWN)
                    .doubleValue();
        }
    }

    @Override
    public void carregarVenda() throws Throwable {
        this.mView.carregarVenda();
    }

    @Override
    public void setAlertDialog(final AlertDialog alertDialog) {
        mAlertDialog = alertDialog;
    }

    @Override
    public ArrayList<String> carregarIdProdutos(final List<Produto> products) {
        return this.mModel.carregarProdutoPorId(products);
    }

    @Override
    public List<Produto> carregarProdutos() {
        return this.mModel.getAllProducts();
    }

    @Override
    public Context getContext() {
        return (Context) this.mView;
    }

    public void setItemPedido(final ItemPedido itemPedido) {
        this.itemPedido = itemPedido;
    }

    @Override
    public ArrayList<String> carregarProdutosPorNome(final List<Produto> produtoS) {
        return this.mModel.carregarProdutoPorNome(produtoS);
    }

    @Override
    public void setItens(final List<ItemPedido> itens) {
        this.itens = itens;
    }

    @Override
    public List<Unidade> carregarUnidades() {
        return this.mModel.getAllUnitys();
    }

    @Override
    public void fecharConexaoAtiva() {
        this.mImpressoraUtil.fecharConexaoAtiva();
    }

    @Override
    public ItemPedido getItemPedido() {
        return itemPedido;
    }

    @Override
    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public Preco getPreco() {
        return preco;
    }

    @Override
    public void setPreco(final Preco preco) {
        this.preco = preco;
    }

    @Override
    public ArrayList<String> carregarUnidadesEmString(final List<Unidade> unitys) {
        return this.mModel.converterUnidadeParaString(unitys);
    }

    @Override
    public Unidade carregarUnidadePadraoDoProduto() {
        return this.mModel.pesquisarUnidadePadraoDoProduto();
    }

    @Override
    public void desabilitarBotaoSalvarPedido() {
        this.mView.desabilitarCliqueBotaoSalvarVenda();
    }

    @Override
    public void exibirDialogAlterarItemPedido(final int position) {
        this.mView.exibirDialogAlterarItemPedido(position);
    }

    @Override
    public Cliente getCliente() {
        return cliente;
    }

    @Override
    public void setCliente(final Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public List<ItemPedido> getItens() {
        return this.itens;
    }

    @Override
    public Unidade getUnidadeSelecionada() {
        return unidadeSelecionada;
    }

    @Override
    public void setUnidadeSelecionada(final Unidade unidadeSelecionada) {
        this.unidadeSelecionada = unidadeSelecionada;
    }

    @Override
    public void getParametros() {
        this.mView.getParametros();
    }

    @Override
    public void imprimirComprovante() {

        this.mImpressoraUtil.imprimirComprovantePedido(getPedido(), getCliente(),this.getNucleo(),this.getFuncionario());
    }

    @Override
    public Pedido getPedido() {
        return this.pedido;
    }

    @Override
    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    @Override
    public void setProdutoSelecionado(final Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

    @Override
    public BigDecimal getQuantidadeProdutos() {
        return quantidadeProdutos;
    }

    @Override
    public Preco pesquisarPrecoPorProduto() {
        return this.mModel.carregarPrecoPorProduto();
    }

    @Override
    public void setQuantidadeProdutos(final BigDecimal quantidadeProdutos) {
        this.quantidadeProdutos = quantidadeProdutos;
    }

    @Override
    public BigDecimal getTotalDaVenda() {
        return totalDaVenda;
    }

    @Override
    public Produto pesquisarProdutoPorNome(final String productName) {
        return this.mModel.pesquisarProdutoPorNome(productName);
    }

    @Override
    public void setTotalDaVenda(final BigDecimal totalDaVenda) {
        this.totalDaVenda = totalDaVenda;
    }

    @Override
    public BigDecimal getValorTotalProduto() {
        return getQuantidadeProdutos().multiply(new BigDecimal(getPreco().getValor()));
    }

    @Override
    public Cliente pesquisarClientePorId(final long codigoCliente) {
        return this.mModel.pesquisarClientePorId(codigoCliente);
    }

    @Override
    public Preco pesquisarPrecoDaUnidadePorProduto(final String unityID) {

        return this.mModel.carregarPrecoUnidadePorProduto(unityID);
    }

    @Override
    public Produto pesquisarProdutoPorId(final long id) {
        return this.mModel.pesquisarProdutoPorId(id);
    }

    @Override
    public Pedido pesquisarVendaPorId(final long keyPedido) {
        return this.mModel.pesquisarVendaPorId(keyPedido);
    }

    @Override
    public Pedido salvarVenda(final long sequencePedido) throws ParseException {

        Pedido pedido = new Pedido();
        pedido.setDataPedido(  DateUtils.formatarDateParaddMMyyyyhhmm(
                new Date(System.currentTimeMillis())));
        // Agora setar o id definitivo do item do pedido

        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            getItens()
                    .forEach(
                            item -> {
                                this.mModel.criarChaveItemPedido(item.getChavesItemPedido());
                                this.mModel.addItemPedido(item);
                            });
        } else {
            for (ItemPedido itemPedido : getItens()) {
                this.mModel.criarChaveItemPedido(itemPedido.getChavesItemPedido());
                this.mModel.addItemPedido(itemPedido);
            }
        }


        pedido.setIdEmpresa(this.mModel.pesquisarEmpresaRegistrada().getId());
        pedido.setCodigoFuncionario(this.getFuncionario().getId());

        pedido.setCodigoCliente(getCliente().getId());
        pedido.setValorTotal(calcularTotalDaVenda());
        pedido.setIdNucleo(this.getNucleo().getId());

        pedido.setIdVenda(sequencePedido);
        PedidoORM pedidoORM = new PedidoORM(pedido);

        // Seta a chave composta do item pedidoORM com o id da venda
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            getItens()
                    .forEach(
                            item -> {
                                item.getChavesItemPedido().setIdVenda(sequencePedido);
                                this.mModel.atualizarChaveItemPedido(item.getChavesItemPedido());
                            });
        } else {
            for (ItemPedido itemPedido : getItens()) {
                itemPedido.getChavesItemPedido().setIdVenda(sequencePedido);
                this.mModel.atualizarChaveItemPedido(itemPedido.getChavesItemPedido());
            }
        }
        pedido.setItens(getItens());
        pedido.setIdVenda(pedidoORM.getId());

        this.mModel.copyOrUpdateSaleOrder(pedido);


        this.mModel.atualizarIdMaximoDeVenda( sequencePedido);
        return pedido;
    }

    @Override
    public void setSpinnerProdutoSelecionado() {
        this.mView.setSpinnerProductSelected();
    }



    @Override
    public void setTotalProductValue(final BigDecimal totalProductValue) {
        this.totalProductValue = totalProductValue;
    }

    @Override
    public void updateTxtAmountOrderSale() {
        this.mView.atualizarTextViewTotalVenda();
    }

    @Override
    public void updateTxtAmountProducts() {
        this.mView.atualizarTextViewValorTotalProduto();
    }

    @Override
    public boolean validarCamposAntesDeAdicionarItem() {
        return this.mView.validarCamposAntesDeAdicionarItem();
    }

    @Override
    public void setLoteSelecionado(final String loteSelecionado) {
        this.loteSelecionado = loteSelecionado;
    }

    @Override
    public String getLoteSelecionado() {
        return this.loteSelecionado;
    }

    @Override
    public Impressora getImpressora() {
        return this.mModel.pesquisarImpressoraAtiva();
    }

    @Override
    public Nucleo getNucleo() {
        return this.mModel.pesquisarNucleoAtivo();
    }

    @Override
    public void salvarNomeFoto(final Pedido pedido) {
        this.mModel.salvarPedido(new PedidoORM(pedido));
    }

    @Override
    public Funcionario getFuncionario() {
        return this.mModel.pesquisarFuncionarioAtivo();
    }


}
