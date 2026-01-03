package view;

import model.Disciplina;
import model.DisciplinaObrigatoria;
import model.DisciplinaOptativa;
import controller.GerenciadorDisciplinas;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class TelaPrincipal extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtCreditos;
    private JTextField txtPeriodo;
    private JTextField txtBusca;
    private JComboBox<String> cbTipo;
    private JTable tabela;
    private DefaultTableModel tabelaModelo;
    private JLabel lblResumo;
    private GerenciadorDisciplinas gerenciador;

    public TelaPrincipal() {
        gerenciador = new GerenciadorDisciplinas();

        setTitle("Orientador de Disciplinas");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        JLabel lblCod = new JLabel("Código:");
        lblCod.setBounds(20, 20, 60, 25);
        add(lblCod);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(80, 20, 120, 25);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(220, 20, 50, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(270, 20, 590, 25);
        add(txtNome);

        JLabel lblCred = new JLabel("Créditos:");
        lblCred.setBounds(20, 55, 60, 25);
        add(lblCred);

        txtCreditos = new JTextField();
        txtCreditos.setBounds(80, 55, 60, 25);
        add(txtCreditos);

        cbTipo = new JComboBox<String>(new String[] { "OBRIGATORIA", "OPTATIVA" });
        cbTipo.setBounds(160, 55, 140, 25);
        add(cbTipo);

        JLabel lblPer = new JLabel("Semestre:");
        lblPer.setBounds(320, 55, 70, 25);
        add(lblPer);

        txtPeriodo = new JTextField();
        txtPeriodo.setBounds(390, 55, 60, 25);
        add(txtPeriodo);

        JButton btnAdd = new JButton("Cadastrar");
        btnAdd.setBounds(20, 100, 110, 30);
        add(btnAdd);

        JButton btnAlt = new JButton("Alterar");
        btnAlt.setBounds(140, 100, 110, 30);
        add(btnAlt);

        JButton btnRem = new JButton("Excluir");
        btnRem.setBounds(260, 100, 110, 30);
        add(btnRem);

        JLabel lblB = new JLabel("Buscar:");
        lblB.setBounds(400, 102, 50, 25);
        add(lblB);

        txtBusca = new JTextField();
        txtBusca.setBounds(455, 102, 405, 25);
        add(txtBusca);

        JButton btnTodas = new JButton("Todas");
        btnTodas.setBounds(20, 145, 100, 25);
        add(btnTodas);

        JButton btnBict = new JButton("Obrigatorias BICT");
        btnBict.setBounds(130, 145, 160, 25);
        add(btnBict);

        JButton btnEng = new JButton("Especificas Eng. Comp");
        btnEng.setBounds(300, 145, 200, 25);
        add(btnEng);

        tabelaModelo = new DefaultTableModel(
                new Object[] { "Cod", "Disciplina", "Créditos", "Semestre", "Categoria", "Carga Horária" }, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tabela = new JTable(tabelaModelo);
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(20, 185, 840, 300);
        add(scroll);

        lblResumo = new JLabel("Total: 0");
        lblResumo.setBounds(20, 500, 840, 20);
        lblResumo.setFont(new Font("SansSerif", Font.BOLD, 12));
        add(lblResumo);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastrar();
            }
        });

        btnAlt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                alterar();
            }
        });

        btnRem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluir();
            }
        });

        btnTodas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela(gerenciador.getLista());
            }
        });

        btnBict.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela(gerenciador.filtrarPorCategoria("BICT"));
            }
        });

        btnEng.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                atualizarTabela(gerenciador.filtrarPorCategoria("Computação"));
            }
        });

        txtBusca.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                atualizarTabela(gerenciador.buscar(txtBusca.getText()));
            }
        });

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    carregarCampos();
                }
            }
        });

        atualizarTabela(gerenciador.getLista());
    }

    private boolean validarCampos() {
        try {
            if (txtCodigo.getText().length() != 8) {
                JOptionPane.showMessageDialog(this, "O código deve ter 8 dígitos.");
                return false;
            }
            Integer.parseInt(txtCreditos.getText());
            if (!txtPeriodo.getText().isEmpty()) {
                Integer.parseInt(txtPeriodo.getText());
            }
            return true;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Créditos e Semestre aceitam apenas números.");
            return false;
        }
    }

    private void cadastrar() {
        if (!validarCampos()) {
            return;
        }
        Disciplina d = criarObjeto();
        gerenciador.adicionar(d);
        limpar();
        atualizarTabela(gerenciador.getLista());
    }

    private void alterar() {
        int sel = tabela.getSelectedRow();
        if (sel < 0 || !validarCampos()) {
            return;
        }

        String codOriginal = tabela.getValueAt(sel, 0).toString();
        for (int i = 0; i < gerenciador.getLista().size(); i++) {
            if (gerenciador.getLista().get(i).getCodigo().equals(codOriginal)) {
                gerenciador.atualizar(i, criarObjeto());
                break;
            }
        }
        atualizarTabela(gerenciador.getLista());
    }

    private void excluir() {
        int sel = tabela.getSelectedRow();
        if (sel >= 0 && JOptionPane.showConfirmDialog(this, "Excluir?") == JOptionPane.YES_OPTION) {
            String cod = tabela.getValueAt(sel, 0).toString();
            for (int i = 0; i < gerenciador.getLista().size(); i++) {
                if (gerenciador.getLista().get(i).getCodigo().equals(cod)) {
                    gerenciador.remover(i);
                    break;
                }
            }
            limpar();
            atualizarTabela(gerenciador.getLista());
        }
    }

    private Disciplina criarObjeto() {
        String c = txtCodigo.getText();
        String n = txtNome.getText();
        int cr = Integer.parseInt(txtCreditos.getText());
        int p = txtPeriodo.getText().isEmpty() ? 0 : Integer.parseInt(txtPeriodo.getText());

        Disciplina d;
        if (cbTipo.getSelectedItem().equals("OBRIGATORIA")) {
            d = new DisciplinaObrigatoria(c, n, cr);
        } else {
            d = new DisciplinaOptativa(c, n, cr);
        }
        d.setPeriodoRecomendado(p);
        return d;
    }

    private void carregarCampos() {
        int idx = tabela.getSelectedRow();
        if (idx >= 0) {
            String cod = tabela.getValueAt(idx, 0).toString();
            for (Disciplina d : gerenciador.getLista()) {
                if (d.getCodigo().equals(cod)) {
                    txtCodigo.setText(d.getCodigo());
                    txtNome.setText(d.getNome());
                    txtCreditos.setText(String.valueOf(d.getCreditos()));
                    txtPeriodo.setText(String.valueOf(d.getPeriodoRecomendado()));
                    cbTipo.setSelectedItem(d.getTipo());
                    break;
                }
            }
        }
    }

    private void atualizarTabela(List<Disciplina> lista) {
        tabelaModelo.setRowCount(0);
        int total = 0;
        for (Disciplina d : lista) {
            tabelaModelo.addRow(new Object[] {
                    d.getCodigo(),
                    d.getNome(),
                    d.getCreditos(),
                    d.getPeriodoRecomendado(),
                    d.getCategoria(),
                    d.getCargaHoraria()
            });
            total += d.getCreditos();
        }
        lblResumo.setText("Disciplinas: " + lista.size() + " | Total Créditos: " + total);
    }

    private void limpar() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtCreditos.setText("");
        txtPeriodo.setText("");
        txtBusca.setText("");
        tabela.clearSelection();
    }
}
