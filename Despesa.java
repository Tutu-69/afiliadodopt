import java.time.LocalDate;

public class Despesa extends Lancamento {
    private GastoCategoria categoria;

    public Despesa(GastoCategoria categoria, LocalDate data, double valor) {
        super(data, valor);
        this.categoria = categoria;
    }

    @Override
    public GastoCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(GastoCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public double impactoNoSaldo() {
        return -getValor();
    }

}
