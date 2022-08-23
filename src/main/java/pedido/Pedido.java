package pedido;


import ingredientes.Ingrediente;

import java.util.ArrayList;
import java.util.Comparator;

public class Pedido {

    private final int id;
    private final ArrayList<ItemPedido> itens;
    private final Cliente cliente;

    public Pedido(int id, ArrayList<ItemPedido> itens, Cliente cliente) {
        this.id = id;
        this.itens = itens;
        this.cliente = cliente;
    }

    public ArrayList<ItemPedido> getItens() {
        return itens;
    }

    public int getId() {
        return this.id;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public boolean calcularTotal(Cardapio cardapio) {
        double total = 0;
        for (ItemPedido itemPedido : itens) {
            total += itemPedido.getShake().getTipoTamanho().atualizaValorTamanho(cardapio.buscarPreco(itemPedido.getShake().getBase())) * itemPedido.getQuantidade();
            for (Ingrediente adicional : itemPedido.getShake().getAdicionais()) {
                total += cardapio.buscarPreco(adicional) * itemPedido.getQuantidade();
            }
        }
        return true;
    }


    public void adicionarItemPedido(ItemPedido itemPedidoAdicionado) {
        sortAdicionais(itemPedidoAdicionado);
        boolean added = false;
        for (ItemPedido item : itens) {
            if (!added) {
                if (item.getShake().equals(itemPedidoAdicionado.getShake())) {
                    item.setQuantidade(item.getQuantidade() + itemPedidoAdicionado.getQuantidade());
                    added = true;
                }
            }
        }

        if (!added) {
            itens.add(itemPedidoAdicionado);
        }
    }

    public boolean removeItemPedido(ItemPedido itemPedidoRemovido) {
        sortAdicionais(itemPedidoRemovido);
        ItemPedido itemToRemove = null;
        boolean removed = false;
        for (ItemPedido item : itens) {
            if (!removed) {
                if (item.getShake().equals(itemPedidoRemovido.getShake())) {
                    if (item.getQuantidade() == 1) {
                        itemToRemove = item;
                        removed = true;
                    } else {
                        item.setQuantidade(item.getQuantidade() - 1);
                        removed = true;
                    }
                }
            }
        }
        if (itemToRemove != null)
            itens.remove(itemToRemove);
        if (!removed)
            throw new IllegalArgumentException("Item nao existe no pedido.");
        return false;
    }

    public void sortAdicionais(ItemPedido pedido) {
        if (pedido.getShake().getAdicionais() != null) {
            if (pedido.getShake().getAdicionais().size() > 0) {
                pedido.getShake().getAdicionais().sort((Comparator<Ingrediente>) (o1, o2) -> o1.obterTipo().toString().compareTo(o2.obterTipo().toString()));
            }
        }
    }


    @Override
    public String toString() {
        return this.itens + " " + this.cliente;
    }
}
