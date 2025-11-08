package model;

import java.io.Serializable;

import model.dao.DaoItem;

public class Transporte implements Serializable {

    //
    //ATRIBUTOS
    //
    private int id;
    private String dataSaida;
    private String dataChegada;
    private String situacaoCadastro;

    //
    //ATRIBUTOS DE RELACIONAMENTO
    //
    private Armazem origem;
    private Armazem destino;
    private Agente agente;
    private Produto produto;

    //
    //MÉTODOS
    //
    public Transporte (String dataSaida, String dataChegada, String situacaoCadastro,
                       Armazem origem, Armazem destino, Agente agente, Produto produto) throws ModelException {
        super();
        this.setId();
        this.setDataSaida(dataSaida);
        this.setDataChegada(dataChegada);
        this.cadastrarTransporte();
        this.setOrigem(origem);
        this.setDestino(destino);
        this.setAgente(agente);
        this.setProduto(produto);
    }

    public int getId() {
        return this.id;
    }

    public String getDataSaida() {
        return this.dataSaida;
    }

    public String getDataChegada() {
        return this.dataChegada;
    }

    public String getSituacaoCadastro() {
        return situacaoCadastro;
    }

    public Armazem getOrigem() {
        return this.origem;
    }

    public Armazem getDestino() {
        return this.destino;
    }

    public Agente getAgente() {
        return this.agente;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setId() {
        this.id = DaoItem.obterUltimoId() + 1;
    }

    public void setDataChegada(String dataChegada) throws ModelException {
        Transporte.validarData(dataChegada);
        this.dataChegada = dataChegada;
    }

    public void setDataSaida(String dataSaida) throws ModelException{
        Transporte.validarData(dataSaida);
        this.dataSaida = dataSaida;
    }

    public void setSituacaoCadastro(String situacaoCadastro) throws ModelException {
        Transporte.validarSituacaoCadastro(situacaoCadastro);
        this.situacaoCadastro = situacaoCadastro;
    }

    public void setOrigem(Armazem origem) throws ModelException {
        Transporte.validarOrigem(origem);
        this.origem = origem;
    }

    public void setDestino(Armazem destino) throws ModelException {
        Transporte.validarDestino(destino);
        this.destino = destino;
    }

    public void setAgente(Agente agente) throws ModelException {
        Transporte.validarAgente(agente);
        this.agente = agente;
    }

    public void setProduto(Produto produto) throws ModelException {
        Transporte.validarProduto(produto);
        this.produto = produto;
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

    public static void validarSituacaoCadastro(String situacaoCadastro) throws ModelException{
        if (situacaoCadastro == null || situacaoCadastro.length() == 0) {
            throw new ModelException("Situação de cadastro não pode ser nula.");
        }
    }

    public static void validarOrigem(Armazem origem) throws ModelException{
        if (origem == null) {
            throw new ModelException("Origem não pode ser nula.");
        }
    }

    public static void validarDestino(Armazem destino) throws ModelException{
        if (destino == null) {
            throw new ModelException("Destino não pode ser nulo.");
        }
    }

    public static void validarAgente(Agente agente) throws ModelException{
        if (agente == null) {
            throw new ModelException("Agente não pode ser nulo.");
        }
    }

    public static void validarProduto(Produto produto) throws ModelException{
        if (produto == null) {
            throw new ModelException("Produto não pode ser nulo.");
        }
    }

    public static void validarData(String dateStr) throws ModelException {
        if (dateStr == null || !dateStr.matches("\\d{2}/\\d{2}/\\d{4}")) {
            throw new ModelException("Data não pode ser nula.");
        }

        try {
            String[] parts = dateStr.split("/");
            int dia = Integer.parseInt(parts[0]);
            int mes = Integer.parseInt(parts[1]);
            int ano = Integer.parseInt(parts[2]);

            if (ano < 1900 || mes < 1 || mes > 12 || dia < 1 || dia > 31) {
                throw new ModelException("Data Inválida.");
            }

        } catch (NumberFormatException e) {
            throw new ModelException("Data inválida.");
        }
    }

    public void cadastrarTransporte() throws ModelException {
        this.setSituacaoCadastro("Análise");
    }

    public void reprovarTransporte() throws ModelException {
        this.setSituacaoCadastro("Reprovado");
    }

    public void reverTransporte() throws ModelException{
        this.setSituacaoCadastro("Revisto");
    }

    public void recondicionarTransporte() throws ModelException{
        this.setSituacaoCadastro("Análise");
    }

    public void aprovarTransporte() throws ModelException{
        this.setSituacaoCadastro("Disponível para envio");
    }

    public void agendarTransporte() throws ModelException{
        this.setSituacaoCadastro("Agendado para transporte");
    }

    public void transportar() throws ModelException{
        this.setSituacaoCadastro("Em transito");
    }

    public void entregar() throws ModelException{
        this.setSituacaoCadastro("Entregue");
    }

    // Métodos específicos para Agente
    public void atribuirAgente(Agente agente) throws ModelException {
        this.setAgente(agente);
        this.setSituacaoCadastro("Agente atribuído");
    }

    public void removerAgente() throws ModelException {
        this.setAgente(null);
        this.setSituacaoCadastro("Sem agente");
    }

    // Métodos específicos para Produto
    public void adicionarProduto(Produto produto) throws ModelException {
        this.setProduto(produto);
        if (this.agente != null) {
            this.setSituacaoCadastro("Produto atribuído - Pronto para transporte");
        } else {
            this.setSituacaoCadastro("Produto atribuído - Aguardando agente");
        }
    }

    public void removerProduto() throws ModelException {
        this.setProduto(null);
        this.setSituacaoCadastro("Sem produto");
    }

    public boolean temProduto() {
        return this.produto != null;
    }

    public boolean temAgente() {
        return this.agente != null;
    }

    public boolean prontoParaTransporte() {
        return this.origem != null && this.destino != null &&
                this.agente != null && this.produto != null &&
                this.dataSaida != null && !this.dataSaida.isEmpty();
    }

    @Override
    public String toString() {
        return "Transporte [ID: " + id +
                ", Origem: " + (origem != null ? origem.getNome() : "N/A") +
                ", Destino: " + (destino != null ? destino.getNome() : "N/A") +
                ", Produto: " + (produto != null ? produto.getNome() : "N/A") +
                ", Agente: " + (agente != null ? agente.getNome() : "N/A") +
                ", Situação: " + situacaoCadastro + "]";
    }
}