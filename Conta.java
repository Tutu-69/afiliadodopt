
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Conta {
    private List<Lancamento> lancamentos;
    private String soundFilePath = "C:Consulta\\src\\Metal-pipe-falling-sound-effect-but-it’s-more-violent-_320_.wav";

    public Conta() {
        this.lancamentos = new ArrayList<>();
    }

    public void adicionarReceita(Receita r) throws IllegalArgumentException {
        if (r.getValor() < 0) {
            playErrorSound();
            throw new IllegalArgumentException("Saldo Negativo");

        }
        lancamentos.add(r);

    }

    public void adicionarDespesa(Despesa d) {
        lancamentos.add(d);
    }

    public double saldoDataAtual() {
        double saldo = 0;
        LocalDate data = LocalDate.now();

        for (Lancamento l : lancamentos) {
            if (l.getData().isBefore(data) || l.getData().isEqual(data)) {
                saldo += l.impactoNoSaldo();
            }

        }

        return saldo;
    }

    public double saldoDisponivel() {
        double saldo = 0;

        for (Lancamento l : lancamentos) {
            saldo += l.impactoNoSaldo();
        }

        return saldo;
    }

    public List<Receita> listarReceitasLancadas() {
        List<Receita> receitas = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            if (l instanceof Receita) {
                receitas.add((Receita) l);
            }
        }
        return receitas;
    }

    public List<Despesa> listarDespesasLancadas() {
        List<Despesa> despesas = new ArrayList<>();
        for (Lancamento l : lancamentos) {
            if (l instanceof Despesa) {
                despesas.add((Despesa) l);
            }
        }
        return despesas;
    }

    private void playErrorSound() {
        try {

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(getClass().getResource(soundFilePath));

            Clip clip = AudioSystem.getClip();

            clip.open(audioIn);

            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> extratoConta() {
        List<Lancamento> lancamentosSorted = new ArrayList<>(lancamentos);
        Collections.sort(lancamentosSorted, (l1, l2) -> l1.getData().compareTo(l2.getData()));

        double saldo = 0;
        List<String> extrato = new ArrayList<>();
        for (Lancamento l : lancamentosSorted) {
            saldo += l.impactoNoSaldo();
            String linhaExtrato = l.getData() + " - " + l.getClass().getSimpleName() + ": R$" + l.getValor()
                    + " - Saldo acumulado: R$" + saldo;
            extrato.add(linhaExtrato);
        }

        return extrato;
    }

}
