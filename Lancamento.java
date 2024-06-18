import java.time.LocalDate;

public abstract class Lancamento {
    private LocalDate data;
    private double valor;

    public Lancamento(LocalDate data, double valor) {
        setData(data);
        setValor(valor);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public abstract Enum<?> getCategoria();

    public abstract double impactoNoSaldo();

}
