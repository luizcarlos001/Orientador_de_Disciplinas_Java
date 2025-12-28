package view;

import model.*;
import controller.GerenciadorDados;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaPrincipal extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtCreditos;
    private JComboBox<String> cbTipo;
    private JList<String> listaComponente;
    private DefaultListModel<String> modelo;

    private GerenciadorDados gerenciador;

    public TelaPrincipal() {
        gerenciador = new GerenciadorDados();


        setTitle("Orientador de Disciplinas - BICT para Eng. Comp");
        setSize(550, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); 

        JLabel lblCod = new JLabel("Código (8 dígitos):");
        lblCod.setBounds(20, 20, 120, 25);
        add(lblCod);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(150, 20, 100, 25);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome da Disciplina:");
        lblNome.setBounds(20, 50, 120, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 50, 250, 25);
        add(txtNome);

        JLabel lblCred = new JLabel("Créditos:");
        lblCred.setBounds(20, 80, 80, 25);
        add(lblCred);

        txtCreditos = new JTextField();
        txtCreditos.setBounds(150, 80, 50, 25);
        add(txtCreditos);

        cbTipo = new JComboBox<>(new String[]{"OBRIGATORIA", "OPTATIVA"});
        cbTipo.setBounds(220, 80, 130, 25);
        add(cbTipo);

        JButton btnAdd = new JButton("Cadastrar");
        btnAdd.setBounds(20, 120, 120, 30);
        add(btnAdd);

        JButton btnAlt = new JButton("Alterar");
        btnAlt.setBounds(160, 120, 120, 30);
        add(btnAlt);

        JButton btnRem = new JButton("Excluir");
        btnRem.setBounds(300, 120, 120, 30);
        add(btnRem);

        modelo = new DefaultListModel<>();
        listaComponente = new JList<>(modelo);
        JScrollPane sp = new JScrollPane(listaComponente);
        sp.setBounds(20, 170, 490, 210);
        add(sp);

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

        listaComponente.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                carregarCampos();
            }
        });

        atualizarLista();
    }

    private void cadastrar() {
        try {
            if (validarCampos()) {
                Disciplina d = criarObjeto();
                gerenciador.adicionar(d);
                atualizarLista();
                limpar();
                JOptionPane.showMessageDialog(this, "Disciplina salva com sucesso!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: Digite apenas números nos créditos.");
        }
    }

    private void alterar() {
        int index = listaComponente.getSelectedIndex();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina na lista para alterar.");
            return;
        }

        if (validarCampos()) {
            Disciplina d = criarObjeto();
            gerenciador.remover(index); 
            gerenciador.adicionar(d);    
            atualizarLista();
            JOptionPane.showMessageDialog(this, "Registro atualizado!");
        }
    }

    private void excluir() {
        int index = listaComponente.getSelectedIndex();
        if (index >= 0) {
            int confirmacao = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir esta disciplina?");
            if (confirmacao == JOptionPane.YES_OPTION) {
                gerenciador.remover(index);
                atualizarLista();
                limpar();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina primeiro.");
        }
    }

    private boolean validarCampos() {
        if (txtCodigo.getText().length() != 8) {
            JOptionPane.showMessageDialog(this, "O código deve ter 8 caracteres!");
            return false;
        }
        if (txtNome.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode ficar vazio!");
            return false;
        }
        if (txtCreditos.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a quantidade de créditos!");
            return false;
        }
        return true;
    }

    private Disciplina criarObjeto() {
        String cod = txtCodigo.getText();
        String nome = txtNome.getText();
        int cred = Integer.parseInt(txtCreditos.getText());
        String tipo = cbTipo.getSelectedItem().toString();

        if (tipo.equals("OBRIGATORIA")) {
            return new DisciplinaObrigatoria(cod, nome, cred);
        } else {
            return new DisciplinaOptativa(cod, nome, cred);
        }
    }

    private void carregarCampos() {
        int index = listaComponente.getSelectedIndex();
        if (index >= 0) {
            Disciplina d = gerenciador.getLista().get(index);
            txtCodigo.setText(d.getCodigo());
            txtNome.setText(d.getNome());
            txtCreditos.setText(String.valueOf(d.getCreditos()));
            cbTipo.setSelectedItem(d.getTipo());
        }
    }

    private void atualizarLista() {
        modelo.clear();
        for (Disciplina d : gerenciador.getLista()) {
            String info = String.format("%s - %s (%dh) | %s", 
                            d.getCodigo(), d.getNome(), d.getCargaHoraria(), d.getCategoria());
            modelo.addElement(info);
        }
    }

    private void limpar() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtCreditos.setText("");
        cbTipo.setSelectedIndex(0);
    }
}