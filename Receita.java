import java.time.LocalDate;

public class Receita extends Lancamento {

    private RecebimentoCategoria categoria;

    public Receita(RecebimentoCategoria categoria, LocalDate data, double valor) {
        super(data, valor);
        this.categoria = categoria;
    }

    @Override
    public RecebimentoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(RecebimentoCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public double impactoNoSaldo() {
        return getValor();
    }

}
