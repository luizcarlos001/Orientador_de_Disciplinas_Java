package model;

public abstract class Disciplina {
    protected String codigo;
    protected String nome;
    protected int creditos;
    protected int periodoRecomendado;

    public Disciplina(String codigo, String nome, int creditos) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.periodoRecomendado = 0; 
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public int getCargaHoraria() {
        return this.creditos * 15;
    }

    public int getPeriodoRecomendado() {
        return periodoRecomendado;
    }

    public void setPeriodoRecomendado(int periodo) {
        this.periodoRecomendado = periodo;
    }

    public abstract String getCategoria();
    
    public abstract String getTipo();
}