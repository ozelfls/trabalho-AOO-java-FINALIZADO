package model;

import java.io.Serializable;

import model.dao.DaoArmazem;

public class Armazem implements Serializable {

    //
    //ATRIBUTOS
    //
    private int id;
    private String nome;
    private float capacidade;
    private String situacaoCadastro;

    //
    //ATRIBUTOS DE RELACIONAMENTO
    //
    private Cidade cidade;

    //
    // METODOS
    //

    public Armazem(Cidade cidade, String nome, float capacidade) throws ModelException {
        super();
        this.setCidade(cidade);
        this.setNome(nome);
        this.setCapacidade(capacidade);
        this.setId();
        this.cadastrarArmazem();
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public float getCapacidade() {
        return this.capacidade;
    }

    public Cidade getCidade() {
        return this.cidade;
    }

    public String getSituacaoCadastro() {
        return situacaoCadastro;
    }

    public void setId() {
        this.id = DaoArmazem.obterUltimoId() + 1;
    }

    public void setNome(String nome) throws ModelException{
        Armazem.validarNome(nome);
        this.nome = nome;
    }

    public void setCapacidade(float capacidade) throws ModelException {
        this.capacidade = capacidade;
    }

    public void setCidade(Cidade cidade) throws ModelException{
        Armazem.validarCidade(cidade);
        this.cidade = cidade;
    }

    public void setSituacaoCadastro(String situacaoCadastro) throws ModelException {
        Armazem.validarSituacaoCadastro(situacaoCadastro);
        this.situacaoCadastro = situacaoCadastro;
    }

    public static void validarNome(String nome) throws ModelException {
        if (nome == null || nome.length() == 0)
            throw new ModelException("O nome não pode ser nulo!");
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            if (!Character.isAlphabetic(c) && !Character.isSpaceChar(c) && c != '-')
                throw new ModelException("No nome, há um caracterer inválido '" + c + "' na posição " + i);
        }
    }

    public static void validarCidade(Cidade cidade) throws ModelException{
        if(cidade == null)
            throw new ModelException("Por favor selecione a cidade onde o armazém está localizado.");
    }

    public static void validarSituacaoCadastro(String situacaoCadastro) throws ModelException{
        if (situacaoCadastro == null || situacaoCadastro.length() == 0) {
            throw new ModelException("Situação de cadastro não pode ser nula.");
        }
    }

    public void cadastrarArmazem() throws ModelException {
        this.setSituacaoCadastro("Análise");
    }

    public void reprovarArmazem() throws ModelException {
        this.setSituacaoCadastro("Reprovado");
    }

    public void verificarArmazem() throws ModelException{
        this.setSituacaoCadastro("Verificado");
    }

    public void bloquearArmazenamento() throws ModelException{
        this.setSituacaoCadastro("Bloqueado para armazenamento");
    }

    public void recondicionarArmazem() throws ModelException{
        this.setSituacaoCadastro("Análise");
    }

    public void aprovarArmazem() throws ModelException{
        this.setSituacaoCadastro("Disponível");
    }

    public void encherArmazem() throws ModelException{
        this.setSituacaoCadastro("Cheio");
    }

    @Override
    public String toString() {
        return this.nome + " - " + (this.cidade != null ? this.cidade.getNome() : "Sem cidade");
    }
}