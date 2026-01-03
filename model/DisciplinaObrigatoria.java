package model;

public class DisciplinaObrigatoria extends Disciplina {
    
    public DisciplinaObrigatoria(String codigo, String nome, int creditos) {
        super(codigo, nome, creditos);
    }

    @Override
    public String getTipo() { 
        return "OBRIGATORIA"; 
    }

    @Override
    public String getCategoria() {
        return "Núcleo Comum (BICT)";
    }
}