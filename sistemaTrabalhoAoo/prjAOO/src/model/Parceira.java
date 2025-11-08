package model;

import java.io.Serializable;

public class Parceira implements Serializable {
    //
    // CONSTANTES
    //
    public static int TAM_CNPJ = 18;


    //
    //ATRIBUTOS
    //
    private String cnpj;
    private String nome;

    public Parceira(String cnpj, String nome) throws ModelException {
        super();
        this.setCnpj(cnpj);
        this.setNome(nome);
    }

    public String getNome() {
        return this.nome;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setNome(String nome) throws ModelException{
        Parceira.validarNome(nome);
        this.nome = nome;
    }

    public void setCnpj(String cnpj) throws ModelException {
        Parceira.validarCnpj(cnpj);
        this.cnpj = cnpj;
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

    public static void validarCnpj(String cnpj) throws ModelException {
        if (cnpj == null || cnpj.length() == 0)
            throw new ModelException("O cnpj não pode ser nulo!");
        if (cnpj.length() != TAM_CNPJ)
            throw new ModelException("O cnpj deve ter " + TAM_CNPJ + " caracteres!");

        // Validação correta para CNPJ: números, pontos, barra e hífen
        for (int i = 0; i < cnpj.length(); i++) {
            char c = cnpj.charAt(i);
            // Permite: números (0-9), pontos (.), barra (/) e hífen (-)
            if (!Character.isDigit(c) && c != '.' && c != '/' && c != '-') {
                throw new ModelException("No CNPJ, há um caracterer inválido '" + c + "' na posição " + i);
            }
        }

        // Validação adicional do formato: XX.XXX.XXX/XXXX-XX
        if (!cnpj.matches("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}")) {
            throw new ModelException("Formato do CNPJ inválido! Use: XX.XXX.XXX/XXXX-XX");
        }
    }

    // ADICIONE ESTE MÉTODO
    @Override
    public String toString() {
        return this.nome;
    }
}