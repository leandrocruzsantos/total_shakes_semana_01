package exceptions;

public class QuantidadeInvalidaException extends IllegalArgumentException{
    public QuantidadeInvalidaException() {
        super("Quantidade invalida");
    }
}

