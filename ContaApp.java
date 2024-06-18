import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ContaApp extends JFrame {
    private Conta conta;
    private JTextField dataField;
    private JTextField valorField;
    private JComboBox<String> tipoBox;
    private JComboBox<String> categoriaBox;
    private JTextArea extratoArea;
    private JButton saldoButton;
    private JButton receitasButton;
    private JButton despesasButton;

    public ContaApp() {
        conta = new Conta();

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(5, 1)); // : )

        JLabel dataLabel = new JLabel("Data:");
        dataField = new JTextField(10);
        topPanel.add(dataLabel);
        topPanel.add(dataField);

        JLabel valorLabel = new JLabel("Valor:");
        valorField = new JTextField(10);
        topPanel.add(valorLabel);
        topPanel.add(valorField);

        JLabel tipoLabel = new JLabel("Tipo:");
        String[] tipos = {"Receita", "Despesa"};
        tipoBox = new JComboBox<>(tipos);
        topPanel.add(tipoLabel);
        topPanel.add(tipoBox);

        JLabel categoriaLabel = new JLabel("Categoria:");
        String[] categoriasReceita = {"SALARIO", "DECIMOTERCEIRO", "FERIAS", "OUTRO"};
        String[] categoriasDespesa = {"PAGAMENTO", "ALIMENTACAO", "TRANSPORTE", "SAUDE", "EDUCACAO", "ENTRETENIMENTO", "OUTRO"};
        categoriaBox = new JComboBox<>(categoriasReceita);
        topPanel.add(categoriaLabel);
        topPanel.add(categoriaBox);

        tipoBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (tipoBox.getSelectedItem().equals("Receita")) {
                    categoriaBox.removeAllItems();
                    for (String categoria : categoriasReceita) {
                        categoriaBox.addItem(categoria);
                    }
                } else {
                    categoriaBox.removeAllItems();
                    for (String categoria : categoriasDespesa) {
                        categoriaBox.addItem(categoria);
                    }
                }
            }
        });

        JButton adicionarButton = new JButton("Adicionar");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String dataText = dataField.getText();
                    LocalDate data = LocalDate.parse(dataText, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double valor = Double.parseDouble(valorField.getText());
                    Enum<?> categoria;
                    if (tipoBox.getSelectedItem().equals("Receita")) {
                        categoria = RecebimentoCategoria.valueOf(categoriaBox.getSelectedItem().toString());
                    } else {
                        categoria = GastoCategoria.valueOf(categoriaBox.getSelectedItem().toString());
                    }
                    if (tipoBox.getSelectedItem().equals("Receita")) {
                        conta.adicionarReceita(new Receita((RecebimentoCategoria) categoria, data, valor));
                    } else {
                        conta.adicionarDespesa(new Despesa((GastoCategoria) categoria, data, valor));
                    }
                    atualizarExtrato();
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(ContaApp.this, "Data inválida. Use o formato yyyy-MM-dd.");
                }
            }
        });
        topPanel.add(adicionarButton);

        saldoButton = new JButton("Saldo");
        saldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ContaApp.this, "Saldo atual: " + conta.saldoDataAtual());
            }
        });
        topPanel.add(saldoButton);

        receitasButton = new JButton("Receitas");
        receitasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Receita> receitas = conta.listarReceitasLancadas();
                String mensagem = "Receitas:\n";
                for (Receita receita : receitas) {
                    mensagem += receita.getCategoria() + " - " + receita.getValor() + "\n";
                }
                JOptionPane.showMessageDialog(ContaApp.this, mensagem);
            }
        });
        topPanel.add(receitasButton);

        despesasButton = new JButton("Despesas");
        despesasButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Despesa> despesas = conta.listarDespesasLancadas();
                String mensagem = "Despesas:\n";
                for (Despesa despesa : despesas) {
                    mensagem += despesa.getCategoria() + " - " + despesa.getValor()+ "\n";
                }
                JOptionPane.showMessageDialog(ContaApp.this, mensagem);
            }
        });
        topPanel.add(despesasButton);

        extratoArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(extratoArea);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        atualizarExtrato();

        setTitle("Conta App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void atualizarExtrato() {
        extratoArea.setText("");
        List<String> extrato = conta.extratoConta();
        for (String linha : extrato) {
            extratoArea.append(linha + "\n");
        }
    }

    public static void main(String[] args) {
        new ContaApp();
    }
}