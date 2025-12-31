package controller; 

import model.*;
import java.io.*;
import java.util.ArrayList;

public class GerenciadorDados {

    private ArrayList<Disciplina> lista = new ArrayList<>();
    private final String CAMINHO_ARQUIVO = "dados_disciplinas.csv";

    public GerenciadorDados() {
        carregarDoArquivo();
    }

    public void adicionar(Disciplina d) {
        lista.add(d);
        salvarNoArquivo();
    }

    public void remover(int index) {
        if (index >= 0 && index < lista.size()) {
            lista.remove(index);
            salvarNoArquivo();
        }
    }

    public void atualizar(int index, Disciplina nova) {
        if (index >= 0 && index < lista.size() && nova != null) {
            lista.set(index, nova);
            salvarNoArquivo();
        }
    }

    public ArrayList<Disciplina> getLista() {
        return lista;
    }

    private void salvarNoArquivo() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Disciplina d : lista) {
                String linha = d.getCodigo() + ";" +
                               d.getNome() + ";" +
                               d.getCreditos() + ";" +
                               d.getTipo() + ";" +
                               d.getCargaHoraria() + ";" +
                               d.getPeriodoRecomendado() + ";" +
                               d.getPreRequisitos();
                writer.println(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo CSV.");
        }
    }

    // Carrega os dados salvos quando o programa inicia
    private void carregarDoArquivo() {
        File arquivo = new File(CAMINHO_ARQUIVO);
        if (!arquivo.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length < 4) continue;

                String cod = partes[0];
                String nome = partes[1];
                int cred = Integer.parseInt(partes[2]);
                String tipo = partes[3];
                int periodo = 0;
                String prereq = "-";

                // Formatos possíveis:
                // 4 colunas: codigo;nome;creditos;tipo
                // 5 colunas: + cargaHoraria (ignorada, calculada)
                // 7 colunas: + cargaHoraria;periodoRecomendado;preRequisitos
                if (partes.length >= 6) {
                    try {
                        periodo = Integer.parseInt(partes[5]);
                    } catch (Exception ex) {
                        periodo = 0;
                    }
                }
                if (partes.length >= 7) {
                    prereq = partes[6];
                }

                if (tipo.equals("OBRIGATORIA")) {
                    Disciplina d = new DisciplinaObrigatoria(cod, nome, cred);
                    d.setPeriodoRecomendado(periodo);
                    d.setPreRequisitos(prereq);
                    lista.add(d);
                } else {
                    Disciplina d = new DisciplinaOptativa(cod, nome, cred);
                    d.setPeriodoRecomendado(periodo);
                    d.setPreRequisitos(prereq);
                    lista.add(d);
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler os dados do arquivo.");
        }
    }
}