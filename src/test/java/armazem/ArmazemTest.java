package armazem;

import exceptions.IngredienteNaoEncontradoException;
import exceptions.QuantidadeInvalidaException;
import ingredientes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArmazemTest {


    private final String INGREDIENTE_NAO_ENCONTRADO = "Ingrediente nao encontrado";
    private final String QUANTIDADE_INVALIDA = "Quantidade invalida";
    private final String INGREDIENTE_JA_CADASTRADO = "Ingrediente ja cadastrado";
    private Armazem armazem;
    private Ingrediente ingrediente;

    @BeforeEach
    public void beforeEach() {
        armazem = new Armazem();
        ingrediente = new Base(TipoBase.SORVETE);
        armazem.cadastrarIngredienteEmEstoque(ingrediente);
    }


    @Test
    @DisplayName("Cadastrar Ingrediente: quando cadastrar um novo ingrediente, a quantidade deve ser zero")
    public void consultarIngredientecadastradoComQuantidadeZero() {
        assertEquals(0, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));
    }

    @Test
    @DisplayName("Cadastrar Ingrediente: quando cadastrar um novo ingrediente, caso ele já exista, jogar exceção")
    public void cadastrarIngredienteJaExistenteNoArmazem() {
        var ex = assertThrows(IllegalArgumentException.class, () -> armazem.cadastrarIngredienteEmEstoque(ingrediente));
        assertEquals(INGREDIENTE_JA_CADASTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("Descadastrar Ingrediente: quando remover um ingrediente cadastrado no estoque, ao consultar a quantidade, jogar exceção")
    public void descadastrarIngredienteExceptionNaoEncontradoDescadastrado() {
        armazem.descadastrarIngredienteEmEstoque(ingrediente);
        var ex = assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("Descadastrar Ingrediente: quando consultar a quantidade de um ingrediente nunca cadastrado, jogar exceção")
    public void descadastrarIngredienteExceptionNaoEncontradoInexistente() {
        var exception = assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.descadastrarIngredienteEmEstoque(new Fruta(TipoFruta.ABACATE)));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, exception.getMessage());
    }

    @Test
    @DisplayName("Consultar Quantidade: quando consultar um ingrediente não existente, jogar exceção")
    public void consultarQuantidadeDeIngredienteNaoExistente() {
        var ex = assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.IOGURTE)));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("Adicionar Quantidade: quando adicionar quantidade no estoque, quantidade deve ser atualizada, não importando o número de vezes que foi chamada")
    public void adicionarQuantidadeAtualizadaDeIngredientes() {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);

        Integer quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(5, quantity);

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 3);
        quantity = armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente);
        assertEquals(8, quantity);
    }


    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("Adicionar Quantidade: quando adicionar quantidade <= zero, jogar exceção")
    public void adicionarQuantidadeMenorOuIgualAZero(Integer quantity) {
        var ex = assertThrows(QuantidadeInvalidaException.class, () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, quantity));
        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());
    }
    @Test
    @DisplayName("Adicionar Quantidade: quando adicionar quantidade a um ingrediente que nao foi cadastrado anteriormente no estoque, jogar exceção")
    public void adicionarQuantidadeDeIngredienteNaoExistente() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> armazem.adicionarQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE), 3));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("Diminuir Quantidade: quando diminuir quantidade, a quantidade em estoque deveria ser atualizada")
    public void diminuirQuantidadeDeIngredienteCadastrado() {

        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 10);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 3);
        assertEquals(7, armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));
    }

    @Test
    @DisplayName("Diminuir Quantidade: quando diminuir quantidade, remover ingrediente se quantidade a ser diminuida for igual a do estoque.")
    public void diminuirQuantidadeERemoverIngredienteSeTodoEstoqueForRemovido() {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        var ex = assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.consultarQuantidadeDoIngredienteEmEstoque(ingrediente));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    @DisplayName("Diminuir Quantidade: quando ingrediente a ser removido não está em estoque, jogar exceção")
    public void diminuirQuantidadeDeIngredienteNãoExistenteNoEstoque() {
        var ex = assertThrows(IngredienteNaoEncontradoException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(new Base(TipoBase.LEITE), 5));
        assertEquals(INGREDIENTE_NAO_ENCONTRADO, ex.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("Diminuir Quantidade: quando a quantidade a ser removida for menor ou igual a zero, jogar exceção")
    public void diminuirQuantidadeRemoverZeroOuMenos(Integer quantity) {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 5);
        var ex = assertThrows(QuantidadeInvalidaException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, quantity));
        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());
    }

    @Test
    @DisplayName("Diminuir Quantidade: quando a quantidade a ser removida for maior que a quantidade em estoque, jogar exceção")
    public void diminuirQuantidadeRemoverMaisQueOEstoque() {
        armazem.adicionarQuantidadeDoIngredienteEmEstoque(ingrediente, 10);
        var ex = assertThrows(QuantidadeInvalidaException.class, () -> armazem.reduzirQuantidadeDoIngredienteEmEstoque(ingrediente, 20));
        assertEquals(QUANTIDADE_INVALIDA, ex.getMessage());
    }
}
