package com.br.tmchickendistributor.ui.mvp.venda;

import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;
import com.br.tmchickendistributor.data.dao.ClienteDAO;
import com.br.tmchickendistributor.data.dao.EmpresaDAO;
import com.br.tmchickendistributor.data.dao.FuncionarioDAO;
import com.br.tmchickendistributor.data.dao.ImpressoraDAO;
import com.br.tmchickendistributor.data.dao.ItemPedidoDAO;
import com.br.tmchickendistributor.data.dao.ItemPedidoIDDAO;
import com.br.tmchickendistributor.data.dao.NucleoDAO;
import com.br.tmchickendistributor.data.dao.PedidoDAO;
import com.br.tmchickendistributor.data.dao.PrecoDAO;
import com.br.tmchickendistributor.data.dao.PrecoIDDAO;
import com.br.tmchickendistributor.data.dao.ProdutoDAO;
import com.br.tmchickendistributor.data.dao.UnidadeDAO;
import com.br.tmchickendistributor.data.model.Cliente;
import com.br.tmchickendistributor.data.model.Empresa;
import com.br.tmchickendistributor.data.model.Funcionario;
import com.br.tmchickendistributor.data.model.Impressora;
import com.br.tmchickendistributor.data.model.ItemPedido;
import com.br.tmchickendistributor.data.model.ItemPedidoID;
import com.br.tmchickendistributor.data.model.Nucleo;
import com.br.tmchickendistributor.data.model.Pedido;
import com.br.tmchickendistributor.data.model.Preco;
import com.br.tmchickendistributor.data.model.Produto;
import com.br.tmchickendistributor.data.model.Unidade;
import com.br.tmchickendistributor.data.realm.ClienteORM;
import com.br.tmchickendistributor.data.realm.EmpresaORM;
import com.br.tmchickendistributor.data.realm.FuncionarioORM;
import com.br.tmchickendistributor.data.realm.ImpressoraORM;
import com.br.tmchickendistributor.data.realm.ItemPedidoIDORM;
import com.br.tmchickendistributor.data.realm.ItemPedidoORM;
import com.br.tmchickendistributor.data.realm.NucleoORM;
import com.br.tmchickendistributor.data.realm.PedidoORM;
import com.br.tmchickendistributor.data.realm.PrecoIDORM;
import com.br.tmchickendistributor.data.realm.PrecoORM;
import com.br.tmchickendistributor.data.realm.ProdutoORM;
import com.br.tmchickendistributor.data.realm.UnidadeORM;
import io.realm.Sort;
import java.util.ArrayList;
import java.util.List;

public class Model implements IVendaMVP.IModel {

    ItemPedidoDAO itemPedidoDAO = ItemPedidoDAO.getInstace(ItemPedidoORM.class);

    ItemPedidoIDDAO mItemPedidoIDDAO = ItemPedidoIDDAO.getInstace(ItemPedidoIDORM.class);

    ClienteDAO mClienteDAO = ClienteDAO.getInstace(ClienteORM.class);

    PedidoDAO mPedidoDAO = PedidoDAO.getInstace(PedidoORM.class);

    PrecoIDDAO mPrecoIDDAO = PrecoIDDAO.getInstance(PrecoIDORM.class);

    PrecoDAO mPrecoDAO = PrecoDAO.getInstace(PrecoORM.class);

    ProdutoDAO mProdutoDAO = ProdutoDAO.getInstace(ProdutoORM.class);

    ProdutoDAO produtoDAO = ProdutoDAO.getInstace(ProdutoORM.class);

    PedidoDAO saleDAO = PedidoDAO.getInstace(PedidoORM.class);

    EmpresaDAO mEmpresaDAO = EmpresaDAO.getInstace(EmpresaORM.class);

    UnidadeDAO unidadeDAO = UnidadeDAO.getInstace(UnidadeORM.class);

    FuncionarioDAO mFuncionarioDAO = FuncionarioDAO.getInstace(FuncionarioORM.class);

    ImpressoraDAO impressoraDAO= ImpressoraDAO.getInstance(ImpressoraORM.class);

    NucleoDAO nucleoDAO = NucleoDAO.getInstace(NucleoORM.class);




    private com.br.tmchickendistributor.ui.mvp.venda.Presenter mPresenter;

    public Model(com.br.tmchickendistributor.ui.mvp.venda.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public long addItemPedido(final ItemPedido item) {
        return this.itemPedidoDAO.addItemPedido(item);
    }

    @Override
    public void atualizarChaveItemPedido(final ItemPedidoID chavesItemPedido) {
        this.mItemPedidoIDDAO.alterar(new ItemPedidoIDORM(chavesItemPedido));
    }

    @Override
    public void atualizarItemPedido(final ItemPedido item) {
        this.itemPedidoDAO.updateItem(new ItemPedidoORM(item));
    }

    @Override
    public Preco carregarPrecoPorProduto() {
        return this.mPrecoDAO.carregaPrecoProduto(mPresenter.getProdutoSelecionado(),mPresenter.getUnidadeSelecionada());
    }

    @Override
    public Preco carregarPrecoUnidadePorProduto(final String unityID) {
        String id =
                mPrecoIDDAO.findPrecoIDByUnidadeAndProdutoAndCliente(
                        mPresenter.getProdutoSelecionado(), unityID, mPresenter.getCliente());
        if (id.equals("")) {
            String padrao =
                    mPrecoIDDAO.findPrecoIDByUnidadeAndProdutoPadrao(
                            mPresenter.getProdutoSelecionado(), unityID);
            return this.mPrecoDAO.findPriceByPriceID(padrao);
        } else {

            return this.mPrecoDAO.findPriceByPriceID(id);
        }
    }

    @Override
    public ArrayList<String> carregarProdutoPorId(final List<Produto> produtos) {

        ArrayList<String> productIds = new ArrayList<>();
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            produtos.forEach(item -> productIds.add(String.valueOf(item.getId())));
        } else {
            for (Produto produto : produtos) {
                productIds.add(String.valueOf(produto.getId()));
            }
        }
        return productIds;
    }

    @Override
    public long configurarSequenceDoPedido() {

        Funcionario funcionarioPesquisado= mFuncionarioDAO.pesquisarFuncionarioAtivo();

        PedidoORM pedidoORM = mPedidoDAO.where().findFirst();
        /**Já houve pedido salvo no tablet*/
        if(pedidoORM!=null){
            PedidoORM pedidoORMRecente = mPedidoDAO.where().sort("id", Sort.DESCENDING).findAll().first();
            Pedido pedidoMaisRecente=new Pedido(pedidoORMRecente);

            if(funcionarioPesquisado.getMaxIdVenda()<pedidoMaisRecente.getIdVenda() && funcionarioPesquisado.getMaxIdVenda()==0){
                /**Houve delete no banco */
                return -1;

            }else{
                /** Segue fluxo normal**/
                return pedidoMaisRecente.getIdVenda()+1;
            }

        }
        /**Pode nao ter pedido no tablet , mas ja existiu vendas para aquele funcionario em outra versao*/
        else if (funcionarioPesquisado.getMaxIdVenda()>0){

            return funcionarioPesquisado.getMaxIdVenda()+1;

        }
        /** A primeira venda do funcionario*/
        else if (funcionarioPesquisado.getMaxIdVenda()==0){

                 return funcionarioPesquisado.getMaxIdVenda()+1;
        }



      return -1;
    }



    @Override
    public ArrayList<String> carregarProdutoPorNome(final List<Produto> produtos) {
        ArrayList<String> productNames = new ArrayList<String>();
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            produtos.forEach(item -> productNames.add(item.getNome()));
        } else {
            for (Produto produto : produtos) {
                productNames.add(produto.getNome());
            }
        }
        return productNames;
    }

    @Override
    public void copyOrUpdateSaleOrder(final Pedido pedido) {
        PedidoORM pedidoORM = new PedidoORM(pedido);
        pedidoORM.setItens(PedidoORM.converterListModelParaListRealm(pedido.getItens()));
        mPedidoDAO.alterar(pedidoORM);

    }

    @Override
    public ArrayList<String> converterUnidadeParaString(final List<Unidade> unidades) {
        ArrayList<String> unityNames = new ArrayList<>();
        if (VERSION.SDK_INT >= VERSION_CODES.N) {
            unidades.forEach(
                    item -> {
                        String[] unitID = item.getId().split("-");
                        unityNames.add(unitID[0]);
                    });
        } else {
            for (Unidade unidade : unidades) {
                String[] unitID = unidade.getId().split("-");
                unityNames.add(unitID[0]);
            }
        }
        return unityNames;
    }

    @Override
    public void criarChaveItemPedido(final ItemPedidoID chavesItemPedido) {
        this.mItemPedidoIDDAO.inserir(chavesItemPedido);
    }

    @Override
    public Cliente pesquisarClientePorId(final Long id) {
        return new Cliente(this.mClienteDAO.findById(id));
    }

    @Override
    public long pesquisarCodigoMaximoDeVendaDoFuncionario(final int idUsuario) {

        Funcionario funcionarioPequisado = mFuncionarioDAO.pesquisarPorId(Long.valueOf(idUsuario));

        return funcionarioPequisado.getMaxIdVenda();
    }

    @Override
    public Empresa pesquisarEmpresaRegistrada() {
        return mEmpresaDAO.pesquisarEmpresaRegistradaNoDispositivo();
    }

    @Override
    public Funcionario pesquisarFuncionarioAtivo() {
        return mFuncionarioDAO.pesquisarFuncionarioAtivo();
    }

    @Override
    public Impressora pesquisarImpressoraAtiva() {
        return impressoraDAO.pesquisarImpressoraAtiva();
    }

    @Override
    public Nucleo pesquisarNucleoAtivo() {
        return this.nucleoDAO.pesquisarNucleoAtivo();
    }

    @Override
    public Produto pesquisarProdutoPorId(final long id) {
        return new Produto(mProdutoDAO.findById(id));
    }

    @Override
    public Produto pesquisarProdutoPorNome(final String productName) {
        return mProdutoDAO.findByName(productName);
    }

    @Override
    public List<Produto> getAllProducts() {
        return this.produtoDAO.getAll();
    }

    @Override
    public List<Unidade> getAllUnitys() {
        return this.unidadeDAO.getAll();
    }

    @Override
    public Unidade pesquisarUnidadePadraoDoProduto() {
        return this.unidadeDAO.findUnityPattenByProduct(mPresenter.getProdutoSelecionado());
    }

    @Override
    public Pedido pesquisarVendaPorId(final Long id) {
        PedidoORM pedidoORM = saleDAO.findById(id);
        Pedido pedido = new Pedido(pedidoORM);
        pedido.setItens(PedidoORM.converterListRealmParaModel(pedidoORM.getItens()));
        return pedido;
    }

    @Override
    public void salvarPedido(final PedidoORM saleOrderToSave) {
         this.mPedidoDAO.salvarPedido(saleOrderToSave);
    }

    @Override
    public void atualizarIdMaximoDeVenda( final long idVendaMaxima) {
        Funcionario funcionarioPesquisado =this.mFuncionarioDAO.pesquisarFuncionarioAtivo();
        funcionarioPesquisado.setMaxIdVenda(idVendaMaxima);
        this.mFuncionarioDAO.alterar(new FuncionarioORM(funcionarioPesquisado));
    }
}
