package model;

import java.io.Serializable;

import model.dao.DaoPais;

public class Pais implements Serializable {
	
	//
	//ATRIBUTOS
	//
	private int id;
	private String nome;
	
	//
	//MÉTODOS
	//
	public Pais (String nome) throws ModelException {
		super();
		this.setId();
		this.setNome(nome);
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getNome() {
		return this.nome;
	}
		
	public void setId() {
		this.id = DaoPais.obterUltimoId() + 1;
	}
	
	public void setNome(String nome) throws ModelException{
		Pais.validarNome(nome);
		this.nome = nome;
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

    @Override
    public String toString() {
        return this.nome;
    }

}
