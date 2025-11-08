package model;

import java.io.Serializable;
import java.util.List;

import model.dao.DaoProduto;

public class Produto implements Serializable {
	
	//
	//CONSTANTES
	//
	private static int TAM_MAX_DESC = 150;
	
	//
	//ATRIBUTOS
	//
	private int id;
	private String nome;
	private String descricao;
	private String situacaoCadastro;
	
	//
	//ATRIBUTOS DE RELACIONAMENTO
	//
	private List<Item> items;
	
	//
	//MÉTODOS
	//
	public Produto (String nome, String descricao) throws ModelException {
		super();
		this.setId();
		this.setNome(nome);
		this.setDescricao(descricao);
		this.cadastrarProduto();
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
	
	public String getDescricao() {
		return this.descricao;
	}
	
	public String getSituacaoCadastro() {
		return situacaoCadastro;
	}
	
	public List<Item> getItems(){
		return this.items;
	}
	
	public void addItem(Item item) {
        items.add(item);
    }
    
    public void removeItem(Item item) {
        items.remove(item);
    }
    
    public boolean containsItem(Item item) {
        return items.contains(item);
    }
		
	public void setId() {
		this.id = DaoProduto.obterUltimoId() + 1;
	}
	
	public void setDescricao(String descricao) throws ModelException {
		Produto.validarDescricao(descricao);
		this.descricao = descricao;
	}
	
	public void setNome(String nome) throws ModelException{
		Produto.validarNome(nome);
		this.nome = nome;
	}
	
	public void setSituacaoCadastro(String situacaoCadastro) throws ModelException {
		Produto.validarSituacaoCadastro(situacaoCadastro);
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
	
	public static void validarDescricao(String descricao) throws ModelException {
		if (descricao == null || descricao.length() == 0)
			throw new ModelException("O nome não pode ser nulo!");
		if (descricao.length() > TAM_MAX_DESC) {
			throw new ModelException("A descrição não pode ser maior que " + TAM_MAX_DESC + "caracteres.");
		}
	}
	
	public static void validarSituacaoCadastro(String situacaoCadastro) throws ModelException{
		if (situacaoCadastro == null || situacaoCadastro.length() == 0) {
			throw new ModelException("Situação de cadastro não pode ser nula.");
		}
	}
	
	public void cadastrarProduto() throws ModelException {
		this.setSituacaoCadastro("Análise");
	}
	
	public void reprovarProduto() throws ModelException {
		this.setSituacaoCadastro("Reprovado");
	}
	
	public void reverProduto() throws ModelException{
		this.setSituacaoCadastro("Revisto");
	}
	
	
	public void recondicionarProduto() throws ModelException{
		this.setSituacaoCadastro("Análise");
	}
	
	public void aprovarProduto() throws ModelException{
		this.setSituacaoCadastro("Disponível para Venda");
	}
	
	public void venderProduto() throws ModelException{
		this.setSituacaoCadastro("Vendido");
	}

	public void transportarProduto() throws ModelException{
		this.setSituacaoCadastro("Em transito");
	}
	
	public void entregarProduto() throws ModelException{
		this.setSituacaoCadastro("Entregue");
	}

    @Override
    public String toString() {
        return this.nome;
    }
}
