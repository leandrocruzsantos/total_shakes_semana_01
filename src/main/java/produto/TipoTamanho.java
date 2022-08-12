package produto;

public enum TipoTamanho {
    //IMPLEMENTE A LOGICA DO ENUM
    //TODO
    P(1.0),
    M(1.3),
    G(1.5);

    public final double multiplicador;

    TipoTamanho(double multiplicador) {
        this.multiplicador = multiplicador;
    }

    public double getMultiplicador() {
        return multiplicador;
    }

    public double atualizaValorTamanho(Double valorBase){
        return valorBase * this.multiplicador;
    }
}

