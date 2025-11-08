package model;

import java.io.Serializable;

import model.dao.DaoItem;

public class Item implements Serializable {
	
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
	//MÉTODOS
	//
	public Item (String nome, String descricao, String situacaoCadastro) throws ModelException {
		super();
		this.setId();
		this.setNome(nome);
		this.setDescricao(descricao);
		this.setSituacaoCadastro(situacaoCadastro);
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
		
	public void setId() {
		this.id = DaoItem.obterUltimoId() + 1;
	}
	
	public void setDescricao(String descricao) throws ModelException {
		Item.validarDescricao(descricao);
		this.descricao = descricao;
	}
	
	public void setNome(String nome) throws ModelException{
		Item.validarNome(nome);
		this.nome = nome;
	}
	
	public void setSituacaoCadastro(String situacaoCadastro) throws ModelException {
		Item.validarSituacaoCadastro(situacaoCadastro);
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

}
