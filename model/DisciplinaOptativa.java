package model;

public class DisciplinaOptativa extends Disciplina {
    public DisciplinaOptativa(String codigo, String nome, int creditos) {
        super(codigo, nome, creditos);
    }

    @Override
    public String getTipo() { return "OPTATIVA"; }

    @Override
    public String getCategoria() {
        return "Específica (Eng. Computação)";
    }
}