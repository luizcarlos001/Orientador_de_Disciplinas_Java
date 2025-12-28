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

    public ArrayList<Disciplina> getLista() {
        return lista;
    }

    private void salvarNoArquivo() {
     
        try (PrintWriter writer = new PrintWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Disciplina d : lista) {
             
                String linha = d.getCodigo() + ";" + 
                               d.getNome() + ";" + 
                               d.getCreditos() + ";" + 
                               d.getTipo();
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

                if (tipo.equals("OBRIGATORIA")) {
                    lista.add(new DisciplinaObrigatoria(cod, nome, cred));
                } else {
                    lista.add(new DisciplinaOptativa(cod, nome, cred));
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler os dados do arquivo.");
        }
    }
}