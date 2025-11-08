package model;

import java.io.Serializable;

import model.dao.DaoCidade;

public class Cidade implements Serializable {


    //
    //ATRIBUTOS
    //
    private int id;
    private String nome;
    private String estado;

    //
    //ATRIBUTOS DE RELACIONAMENTO
    //
    private Pais pais;

    //
    // METODOS
    //

    public Cidade(Pais pais, String nome, String estado) throws ModelException {
        super();
        this.setPais(pais);
        this.setNome(nome);
        this.setEstado(estado);
        this.setId();
    }

    public int getId() {
        return this.id;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEstado() {
        return this.estado;
    }

    public void setId() {
        this.id = DaoCidade.obterUltimoId() + 1;
    }

    public void setNome(String nome) throws ModelException{
        Cidade.validarNome(nome);
        this.nome = nome;
    }

    public void setEstado(String estado) throws ModelException {
        Cidade.validarNome(estado);
        this.estado = estado;
    }

    public void setPais(Pais pais) throws ModelException{
        Cidade.validarPais(pais);
        this.pais = pais;
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

    public static void validarPais(Pais pais) throws ModelException{
        if(pais == null)
            throw new ModelException("Por favor selecione o país a qual pertence essa cidade.");
    }

    public Pais getPais() {
        return this.pais;
    }

    @Override
    public String toString() {
        return this.nome + " - " + this.estado;
    }
}