package armazem;

import exceptions.IngredienteJaCadastradoException;
import exceptions.IngredienteNaoEncontradoException;
import exceptions.QuantidadeInvalidaException;
import ingredientes.Ingrediente;

import java.util.TreeMap;

public class Armazem {

    private final TreeMap<Ingrediente, Integer> estoque;

    public Armazem() {
        estoque = new TreeMap<>();
    }

    public void cadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        if (!estoque.containsKey(ingrediente)) {
            this.estoque.put(ingrediente, 0);
        } else {
            throw new IngredienteJaCadastradoException();
        }
    }

    public Integer consultarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente) {
        checkIfIngredientExists(ingrediente);
        return estoque.get(ingrediente);

    }

    public void descadastrarIngredienteEmEstoque(Ingrediente ingrediente) {
        checkIfIngredientExists(ingrediente);
        this.estoque.remove(ingrediente);
    }
    public void adicionarQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantity) {
        checkIfQuantityIsvalid(quantity);
        checkIfIngredientExists(ingrediente);
        estoque.put(ingrediente, estoque.get(ingrediente) + quantity);
    }
    public void reduzirQuantidadeDoIngredienteEmEstoque(Ingrediente ingrediente, Integer quantity) {
        checkIfIngredientExists(ingrediente);
        checkIfQuantityIsvalid(quantity);
        Integer actualQualtity = estoque.get(ingrediente);
        if (actualQualtity < quantity)
            throw new QuantidadeInvalidaException();
        else if (actualQualtity - quantity == 0)
            estoque.remove(ingrediente);
        else
            estoque.put(ingrediente, estoque.get(ingrediente) - quantity);
    }
    private void checkIfIngredientExists(Ingrediente ingrediente) {
        if (!estoque.containsKey(ingrediente))
            throw new IngredienteNaoEncontradoException();
    }
    private void checkIfQuantityIsvalid(Integer qtd) {
        if (qtd <= 0)
            throw new QuantidadeInvalidaException();
    }
}
