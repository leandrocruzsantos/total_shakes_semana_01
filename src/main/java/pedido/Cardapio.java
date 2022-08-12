package pedido;

import ingredientes.Ingrediente;

import produto.Shake;

import java.util.Collections;

import java.util.TreeMap;



public class Cardapio {
    private TreeMap<Ingrediente,Double> precos;

    public Cardapio()this.precos= new TreeMap<>(Collections.reverseOrder());

    public TreeMap<Ingrediente, Double> getPrecos(){
        return this.precos;
    }

    public void adicionarIngrediente(Ingrediente ingrediente,Double preco){
        //TODO
        validaPreco(preco);
        this.precos.put(ingrediente, preco);
    }

    public Cardapio(TreeMap<Ingrediente, Double> precos) {
        this.precos = precos;
    }

    public void atualizarIngrediente(Ingrediente ingrediente,Double preco){
            if(precos.containsKey(ingrediente)){
                validaPreco(preco);
                this.precos.replace(ingrediente,preco);
            } else {
                throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
            }
    }

    public void removerIngrediente(Ingrediente ingrediente){
            if(precos.containsKey(ingrediente)){
                this.precos.remove(ingrediente);
            } else {
                throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
            }
    }

    public Double buscarPreco(Ingrediente ingrediente){
        //TODO
        if(precos.containsKey(ingrediente)){
            return this.precos.get(ingrediente);
        } else {
            throw new IllegalArgumentException("Ingrediente nao existe no cardapio.");
        }
        public void validaPreco(Double preco){
            if(preco <= 0){
                throw new IllegalArgumentException("Preco invalido.");
            }
    }

    @Override
    public String toString() {
        return this.precos.toString();
    }

}
