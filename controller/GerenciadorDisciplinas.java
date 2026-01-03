package controller;

import model.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDisciplinas {

    private ArrayList<Disciplina> lista = new ArrayList<>();
    private final String DISCIPLINA = "dados_disciplinas.csv";

    public GerenciadorDisciplinas() {
        carregarDados();
    }

    public void adicionar(Disciplina d) {
        lista.add(d);
        salvarDados();
    }

    public void remover(int index) {
        if (index >= 0 && index < lista.size()) {
            lista.remove(index);
            salvarDados();
        }
    }

    public void atualizar(int index, Disciplina nova) {
        if (index >= 0 && index < lista.size() && nova != null) {
            lista.set(index, nova);
            salvarDados();
        }
    }

    public ArrayList<Disciplina> getLista() {
        return lista;
    }

    public List<Disciplina> buscar(String texto) {
        List<Disciplina> filtrada = new ArrayList<>();
        String busca = texto.toLowerCase();

        for (Disciplina d : lista) {
            if (d.getNome().toLowerCase().contains(busca)
                    || d.getCodigo().toLowerCase().contains(busca)) {
                filtrada.add(d);
            }
        }
        return filtrada;
    }

    public List<Disciplina> filtrarPorCategoria(String categoria) {
        List<Disciplina> filtrada = new ArrayList<>();

        for (Disciplina d : lista) {
            if (d.getCategoria() != null
                    && d.getCategoria().toLowerCase().contains(categoria.toLowerCase())) {
                filtrada.add(d);
            }
        }
        return filtrada;
    }

    private void salvarDados() {
        try (PrintWriter out = new PrintWriter(new FileWriter(DISCIPLINA, false))) {
            for (Disciplina d : lista) {
                out.println(
                        d.getCodigo() + ";" +
                        d.getNome() + ";" +
                        d.getCreditos() + ";" +
                        d.getTipo() + ";" +
                        d.getPeriodoRecomendado()
                );
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar: " + e.getMessage());
        }
    }

    private void carregarDados() {
        File arq = new File(DISCIPLINA);
        if (!arq.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arq))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] partes = linha.split(";");
                if (partes.length >= 5) {
                    String cod = partes[0];
                    String nome = partes[1];
                    int cred = Integer.parseInt(partes[2]);
                    String tipo = partes[3];
                    int p = Integer.parseInt(partes[4]);

                    Disciplina d;
                    if (tipo.equalsIgnoreCase("OBRIGATORIA")) {
                        d = new DisciplinaObrigatoria(cod, nome, cred);
                    } else {
                        d = new DisciplinaOptativa(cod, nome, cred);
                    }

                    d.setPeriodoRecomendado(p);
                    lista.add(d);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}
