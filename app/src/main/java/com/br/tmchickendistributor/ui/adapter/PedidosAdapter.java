package com.br.tmchickendistributor.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.br.tmchickendistributor.data.model.Pedido;
import com.br.tmchickendistributor.data.realm.ClientePedido;
import com.br.tmchickendistributor.ui.listener.ClickSubItemListener;
import com.br.tmchickendistributor.ui.mvp.pedido.IPedidoMVP;
import com.br.tmchickendistributor.ui.views.ClientePedidoViewHolder;
import com.br.tmchickendistributor.ui.views.PedidosViewHolder;
import com.br.tmchickendistributor.util.ParentListItem;
import com.br.tmchickendristributor.R;
import java.util.List;

public class PedidosAdapter extends ExpandableRecyclerAdapter<ClientePedidoViewHolder, PedidosViewHolder>
        implements ClickSubItemListener {

    private LayoutInflater mInflator;

    private IPedidoMVP.IPresenter mPresenter;

    public PedidosAdapter(IPedidoMVP.IPresenter presenter, List<? extends ParentListItem> parentItemList) {
        super(parentItemList);
        mPresenter = presenter;
        mInflator = (LayoutInflater) mPresenter.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void onBindParentViewHolder(ClientePedidoViewHolder clientePedidoViewHolder, int position,
            ParentListItem parentListItem) {
        ClientePedido clientePedido = (ClientePedido) parentListItem;
        clientePedidoViewHolder.bind(clientePedido.getCliente());


    }

    @Override
    public void onClickSubitem(final View v, final Object o) {
        mPresenter.setPedido((Pedido) o);
        mPresenter.onShowBottoSheetDialog();

    }

    @Override
    public PedidosViewHolder onCreateChildViewHolder(ViewGroup childViewGroup) {
        View orderSalesView = mInflator.inflate(R.layout.child_layout, childViewGroup, false);
        return new PedidosViewHolder(orderSalesView);
    }

    @Override
    public ClientePedidoViewHolder onCreateParentViewHolder(ViewGroup parentViewGroup) {
        View clientView = mInflator.inflate(R.layout.header_layout, parentViewGroup, false);
        return new ClientePedidoViewHolder(clientView);
    }

    @Override
    public void onBindChildViewHolder(PedidosViewHolder pedidosViewHolder, int position, Object childListItem) {
        Pedido pedido = (Pedido) childListItem;
        pedidosViewHolder.bind(pedido);
        pedidosViewHolder.setClickSubItemListener(this);


    }

    @Override
    public void notifyChildItemChanged(final int parentPosition, final int childPosition) {
        super.notifyChildItemChanged(parentPosition, childPosition);
    }

}