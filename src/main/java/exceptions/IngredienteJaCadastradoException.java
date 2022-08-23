package exceptions;

public class IngredienteJaCadastradoException extends IllegalArgumentException{
    public IngredienteJaCadastradoException() {
        super("Ingrediente ja cadastrado");
    }
}
