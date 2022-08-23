package exceptions;


public class IngredienteNaoEncontradoException extends IllegalArgumentException{
    public IngredienteNaoEncontradoException() {
        super("Ingrediente nao encontrado");
    }

}
