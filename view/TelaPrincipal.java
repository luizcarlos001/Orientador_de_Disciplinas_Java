package view;

import model.*;
import controller.GerenciadorDados;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Locale;

public class TelaPrincipal extends JFrame {

    private JTextField txtCodigo;
    private JTextField txtNome;
    private JTextField txtCreditos;
    private JComboBox<String> cbTipo;

    private JTable tabela;
    private DefaultTableModel tabelaModelo;

    private JTextField txtBusca;
    private JButton btnFiltroTodas;
    private JButton btnFiltroObrigatorias;
    private JButton btnFiltroEspecificas;
    private JLabel lblResumo;
    private JTextField txtPeriodo;
    private JTextField txtPreRequisitos;
    private JButton btnRecomendacoes;

    private ArrayList<Disciplina> listaFiltrada = new ArrayList<>();
    private String modoFiltro = "TODAS"; // TODAS | OBRIGATORIA | ESPECIFICA

    private GerenciadorDados gerenciador;

    public TelaPrincipal() {
        gerenciador = new GerenciadorDados();


        setTitle("Orientador de Disciplinas - BICT para Eng. Comp");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null); 

        // Linha 1 - Código e Nome
        JLabel lblCod = new JLabel("Código (8 dígitos):");
        lblCod.setBounds(20, 20, 150, 25);
        add(lblCod);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(170, 20, 140, 25);
        add(txtCodigo);

        JLabel lblNome = new JLabel("Nome da Disciplina:");
        lblNome.setBounds(330, 20, 130, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(470, 20, 390, 25);
        add(txtNome);

        // Linha 2 - Créditos, Tipo, Período, Pré-requisitos
        JLabel lblCred = new JLabel("Créditos:");
        lblCred.setBounds(20, 60, 80, 25);
        add(lblCred);

        txtCreditos = new JTextField();
        txtCreditos.setBounds(170, 60, 80, 25);
        add(txtCreditos);

        cbTipo = new JComboBox<>(new String[]{"OBRIGATORIA", "OPTATIVA"});
        cbTipo.setBounds(260, 60, 160, 25);
        add(cbTipo);

        JLabel lblPeriodo = new JLabel("Período (num):");
        lblPeriodo.setBounds(430, 60, 120, 25);
        add(lblPeriodo);

        txtPeriodo = new JTextField();
        txtPeriodo.setBounds(550, 60, 80, 25);
        add(txtPeriodo);

        JLabel lblPreReq = new JLabel("Pré-requisitos:");
        lblPreReq.setBounds(640, 60, 110, 25);
        add(lblPreReq);

        txtPreRequisitos = new JTextField();
        txtPreRequisitos.setBounds(750, 60, 110, 25);
        add(txtPreRequisitos);

        // Linha 3 - Botões de ação
        JButton btnAdd = new JButton("Cadastrar");
        btnAdd.setBounds(20, 105, 140, 30);
        add(btnAdd);

        JButton btnAlt = new JButton("Alterar");
        btnAlt.setBounds(170, 105, 140, 30);
        add(btnAlt);

        JButton btnRem = new JButton("Excluir");
        btnRem.setBounds(320, 105, 140, 30);
        add(btnRem);

        // Linha 4 - Filtros e recomendações
        JLabel lblBusca = new JLabel("Buscar (código/nome):");
        lblBusca.setBounds(20, 150, 150, 25);
        add(lblBusca);

        txtBusca = new JTextField();
        txtBusca.setBounds(170, 150, 180, 25);
        add(txtBusca);

        btnFiltroTodas = new JButton("Todas");
        btnFiltroTodas.setBounds(370, 145, 100, 30);
        add(btnFiltroTodas);

        btnFiltroObrigatorias = new JButton("Obrigatórias BICT");
        btnFiltroObrigatorias.setBounds(480, 145, 150, 30);
        add(btnFiltroObrigatorias);

        btnFiltroEspecificas = new JButton("Específicas Eng. Comp.");
        btnFiltroEspecificas.setBounds(640, 145, 150, 30);
        add(btnFiltroEspecificas);

        btnRecomendacoes = new JButton("Recomendações Eng. Comp");
        btnRecomendacoes.setBounds(20, 185, 210, 30);
        add(btnRecomendacoes);

        tabelaModelo = new DefaultTableModel(
                new Object[]{"Código", "Nome da Disciplina", "Créditos", "Tipo", "Carga Horária"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabela = new JTable(tabelaModelo);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getColumnModel().getColumn(0).setPreferredWidth(80);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(280);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(70);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(120);

        JScrollPane spTabela = new JScrollPane(tabela);
        spTabela.setBounds(20, 230, 840, 320);
        add(spTabela);

        lblResumo = new JLabel("Resumo: 0 disciplinas | 0 créditos | 0h");
        lblResumo.setBounds(20, 560, 840, 20);
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

        btnRecomendacoes.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirDialogRecomendacoes();
            }
        });

        tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    carregarCampos();
                }
            }
        });

        btnFiltroTodas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modoFiltro = "TODAS";
                aplicarFiltro();
            }
        });

        btnFiltroObrigatorias.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modoFiltro = "OBRIGATORIA";
                aplicarFiltro();
            }
        });

        btnFiltroEspecificas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modoFiltro = "ESPECIFICA";
                aplicarFiltro();
            }
        });

        txtBusca.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { aplicarFiltro(); }

            @Override
            public void removeUpdate(DocumentEvent e) { aplicarFiltro(); }

            @Override
            public void changedUpdate(DocumentEvent e) { aplicarFiltro(); }
        });

        atualizarTabelaCompleta();
    }

    private void cadastrar() {
        try {
            if (validarCampos()) {
                Disciplina d = criarObjeto();
                if (existeCodigoDuplicado(d.getCodigo(), null)) {
                    JOptionPane.showMessageDialog(this, "Código já cadastrado. Escolha outro.", "Validação", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                gerenciador.adicionar(d);
                JOptionPane.showMessageDialog(this, "Disciplina cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limpar();
                atualizarTabelaCompleta();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar: verifique os dados informados.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alterar() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina na tabela para alterar.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (validarCampos()) {
            Disciplina nova = criarObjeto();
            Disciplina original = listaFiltrada.get(selectedRow);
            if (existeCodigoDuplicado(nova.getCodigo(), original)) {
                JOptionPane.showMessageDialog(this, "Código já cadastrado em outra disciplina.", "Validação", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indexMaster = gerenciador.getLista().indexOf(original);
            if (indexMaster >= 0) {
                gerenciador.atualizar(indexMaster, nova);
                JOptionPane.showMessageDialog(this, "Disciplina atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                atualizarTabelaCompleta();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível localizar a disciplina original.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void excluir() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow >= 0) {
            int confirmacao = JOptionPane.showConfirmDialog(
                    this,
                    "Confirma a exclusão da disciplina selecionada?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (confirmacao == JOptionPane.YES_OPTION) {
                Disciplina alvo = listaFiltrada.get(selectedRow);
                int indexMaster = gerenciador.getLista().indexOf(alvo);
                if (indexMaster >= 0) {
                    gerenciador.remover(indexMaster);
                    JOptionPane.showMessageDialog(this, "Disciplina excluída com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    limpar();
                    atualizarTabelaCompleta();
                } else {
                    JOptionPane.showMessageDialog(this, "Não foi possível localizar a disciplina para exclusão.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma disciplina na tabela.", "Atenção", JOptionPane.WARNING_MESSAGE);
        }
    }

    private boolean validarCampos() {
        String codigo = txtCodigo.getText().trim();
        String nome = txtNome.getText().trim();
        String credStr = txtCreditos.getText().trim();
        String periodoStr = txtPeriodo.getText().trim();

        if (codigo.length() < 8) {
            JOptionPane.showMessageDialog(this, "O código deve ter pelo menos 8 caracteres.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode ficar vazio.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (credStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a quantidade de créditos.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        try {
            int cred = Integer.parseInt(credStr);
            if (cred <= 0) {
                JOptionPane.showMessageDialog(this, "Os créditos devem ser um número positivo.", "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Créditos inválidos: utilize apenas números.", "Validação", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        if (!periodoStr.isEmpty()) {
            try {
                Integer.parseInt(periodoStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Período recomendado inválido: utilize apenas números.", "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }

        return true;
    }

    private Disciplina criarObjeto() {
        String cod = txtCodigo.getText();
        String nome = txtNome.getText();
        int cred = Integer.parseInt(txtCreditos.getText());
        String tipo = cbTipo.getSelectedItem().toString();

        if (tipo.equals("OBRIGATORIA")) {
            Disciplina d = new DisciplinaObrigatoria(cod, nome, cred);
            aplicarCamposExtras(d);
            return d;
        } else {
            Disciplina d = new DisciplinaOptativa(cod, nome, cred);
            aplicarCamposExtras(d);
            return d;
        }
    }

    private void aplicarCamposExtras(Disciplina d) {
        String periodoStr = txtPeriodo.getText().trim();
        int periodo = 0;
        if (!periodoStr.isEmpty()) {
            try {
                periodo = Integer.parseInt(periodoStr);
            } catch (NumberFormatException ex) {
                // validação será feita em validarCampos()
            }
        }
        d.setPeriodoRecomendado(periodo);
        String prereq = txtPreRequisitos.getText().trim();
        d.setPreRequisitos(prereq.isEmpty() ? "-" : prereq);
    }

    private void carregarCampos() {
        int selectedRow = tabela.getSelectedRow();
        if (selectedRow >= 0) {
            Object cod = tabelaModelo.getValueAt(selectedRow, 0);
            Object nome = tabelaModelo.getValueAt(selectedRow, 1);
            Object cred = tabelaModelo.getValueAt(selectedRow, 2);
            Object tipoExibido = tabelaModelo.getValueAt(selectedRow, 3);

            txtCodigo.setText(cod != null ? cod.toString() : "");
            txtNome.setText(nome != null ? nome.toString() : "");
            txtCreditos.setText(cred != null ? cred.toString() : "");

            String selecionaTipo = "OBRIGATORIA";
            if (tipoExibido != null) {
                String t = tipoExibido.toString().toUpperCase(Locale.ROOT);
                if (t.contains("ESPECÍFICA") || t.contains("ESPECIFICA") || t.contains("OPTATIVA")) {
                    selecionaTipo = "OPTATIVA";
                } else {
                    selecionaTipo = "OBRIGATORIA";
                }
            }
            cbTipo.setSelectedItem(selecionaTipo);
            // preencher extras a partir do objeto listado
            if (selectedRow < listaFiltrada.size()) {
                Disciplina d = listaFiltrada.get(selectedRow);
                txtPeriodo.setText(String.valueOf(d.getPeriodoRecomendado()));
                txtPreRequisitos.setText(d.getPreRequisitos());
            }
        }
    }

    private void atualizarTabelaCompleta() {
        // Reinicia filtro para refletir dados atuais
        listaFiltrada.clear();
        listaFiltrada.addAll(gerenciador.getLista());
        aplicarFiltro();
    }

    private void aplicarFiltro() {
        String termo = txtBusca != null ? txtBusca.getText().trim().toLowerCase(Locale.ROOT) : "";

        tabelaModelo.setRowCount(0);

        listaFiltrada.clear();
        for (Disciplina d : gerenciador.getLista()) {
            boolean passaModo;
            if ("OBRIGATORIA".equals(modoFiltro)) {
                passaModo = "OBRIGATORIA".equals(d.getTipo());
            } else if ("ESPECIFICA".equals(modoFiltro)) {
                passaModo = "OPTATIVA".equals(d.getTipo());
            } else {
                passaModo = true; // TODAS
            }

            boolean passaBusca = true;
            if (!termo.isEmpty()) {
                passaBusca = d.getCodigo().toLowerCase(Locale.ROOT).contains(termo)
                        || d.getNome().toLowerCase(Locale.ROOT).contains(termo);
            }

            if (passaModo && passaBusca) {
                listaFiltrada.add(d);
                tabelaModelo.addRow(new Object[]{
                        d.getCodigo(),
                        d.getNome(),
                        d.getCreditos(),
                        d.getCategoria(),
                        d.getCargaHoraria()
                });
            }
        }
        atualizarResumo();
    }

    private void atualizarResumo() {
        int qtd = listaFiltrada.size();
        int somaCred = 0;
        int somaCH = 0;
        for (Disciplina d : listaFiltrada) {
            somaCred += d.getCreditos();
            somaCH += d.getCargaHoraria();
        }
        lblResumo.setText(String.format("Resumo: %d disciplinas | %d créditos | %dh", qtd, somaCred, somaCH));
    }

    private void limpar() {
        txtCodigo.setText("");
        txtNome.setText("");
        txtCreditos.setText("");
        cbTipo.setSelectedIndex(0);
        tabela.clearSelection();
    }

    private boolean existeCodigoDuplicado(String codigo, Disciplina ignorar) {
        for (Disciplina d : gerenciador.getLista()) {
            if (d == ignorar) continue;
            if (d.getCodigo() != null && d.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    private void abrirDialogRecomendacoes() {
        JDialog dialog = new JDialog(this, "Recomendações Eng. Computação", true);
        dialog.setLayout(null);
        dialog.setSize(700, 400);
        dialog.setLocationRelativeTo(this);

        DefaultTableModel modeloRec = new DefaultTableModel(
                new Object[]{"Código", "Nome", "Créditos", "Carga Horária", "Período", "Pré-requisitos"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable tabelaRec = new JTable(modeloRec);
        JScrollPane spRec = new JScrollPane(tabelaRec);
        spRec.setBounds(20, 20, 650, 290);
        dialog.add(spRec);

        ArrayList<Disciplina> optativas = new ArrayList<>();
        for (Disciplina d : gerenciador.getLista()) {
            if ("OPTATIVA".equals(d.getTipo())) {
                optativas.add(d);
            }
        }
        optativas.sort((a, b) -> {
            int pa = a.getPeriodoRecomendado();
            int pb = b.getPeriodoRecomendado();
            if (pa == 0 && pb == 0) return 0;
            if (pa == 0) return 1; // 0 vai para o final
            if (pb == 0) return -1;
            return Integer.compare(pa, pb);
        });

        int somaCred = 0;
        int somaCH = 0;
        for (Disciplina d : optativas) {
            somaCred += d.getCreditos();
            somaCH += d.getCargaHoraria();
            modeloRec.addRow(new Object[]{
                    d.getCodigo(), d.getNome(), d.getCreditos(), d.getCargaHoraria(), d.getPeriodoRecomendado(), d.getPreRequisitos()
            });
        }

        JLabel lblResumoRec = new JLabel(String.format("Total recomendadas: %d | Créditos: %d | Carga Horária: %d", optativas.size(), somaCred, somaCH));
        lblResumoRec.setBounds(20, 320, 650, 25);
        dialog.add(lblResumoRec);

        dialog.setVisible(true);
    }
}