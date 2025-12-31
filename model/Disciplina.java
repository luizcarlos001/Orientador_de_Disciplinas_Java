package model;

public abstract class Disciplina {
    protected String codigo;
    protected String nome;
    protected int creditos;
    protected int periodoRecomendado;
    protected String preRequisitos;

    public Disciplina(String codigo, String nome, int creditos) {
        this.codigo = codigo;
        this.nome = nome;
        this.creditos = creditos;
        this.periodoRecomendado = 0;
        this.preRequisitos = "-";
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }
    public int getCreditos() { return creditos; }

    public int getCargaHoraria() {
        return this.creditos * 15;
    }

    public int getPeriodoRecomendado() { return periodoRecomendado; }
    public void setPeriodoRecomendado(int periodoRecomendado) { this.periodoRecomendado = periodoRecomendado; }

    public String getPreRequisitos() { return preRequisitos; }
    public void setPreRequisitos(String preRequisitos) { this.preRequisitos = (preRequisitos == null || preRequisitos.trim().isEmpty()) ? "-" : preRequisitos; }

    public abstract String getCategoria();
    
    public abstract String getTipo();
}