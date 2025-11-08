package model;

import java.io.Serializable;

public class Agente implements Serializable {
    //
    // CONSTANTES
    //
    public static int TAM_CPF = 14;

    //
    // ATRIBUTOS
    //
    private String cpf; // tipo do atributo cpf: Ponteiro para (um objeto) String
    private String nome; // tipo do atributo nome: Ponteiro para (um objeto) String

    //
    //ATRIBUTO DE RELACIONAMENTO
    //
    private Parceira parceira;

    //
    // MÉTODOS
    //

    public Agente(String c, String n, Parceira i) throws ModelException {
        super();
        this.setCpf(c);
        this.setNome(n);
        this.setParceira(i);
    }


    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String novoCpf) throws ModelException {
        Agente.validarCpf(novoCpf);
        this.cpf = novoCpf;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String novoNome) throws ModelException {
        Agente.validarNome(novoNome);
        this.nome = novoNome;
    }

    public Parceira getParceira() {
        return this.parceira;
    }


    public void setParceira(Parceira parceira) throws ModelException {
        Agente.validarParceira(parceira);
        this.parceira = parceira;
    }

    @Override
    public String toString() {
        return this.nome + " (" + this.cpf + ")";
    }

    public static void validarCpf(String cpf) throws ModelException {
        if (cpf == null || cpf.length() == 0)
            throw new ModelException("O cpf não pode ser nulo!");
        if (cpf.length() != TAM_CPF)
            throw new ModelException("O cpf deve ter " + TAM_CPF + " caracteres!");

        // Validação correta para CPF: números, pontos e hífen
        for (int i = 0; i < cpf.length(); i++) {
            char c = cpf.charAt(i);
            // Permite: números (0-9), pontos (.) e hífen (-)
            if (!Character.isDigit(c) && c != '.' && c != '-') {
                throw new ModelException("No CPF, há um caracterer inválido '" + c + "' na posição " + i);
            }
        }

        // Validação adicional do formato: XXX.XXX.XXX-XX
        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            throw new ModelException("Formato do CPF inválido! Use: XXX.XXX.XXX-XX");
        }
    }

    public static void validarNome(String nome) throws ModelException {
        if (nome == null || nome.length() == 0)
            throw new ModelException("O nome não pode ser nulo!");
        for (int i = 0; i < nome.length(); i++) {
            char c = nome.charAt(i);
            if (!Character.isAlphabetic(c) && !Character.isSpaceChar(c))
                throw new ModelException("No nome, há um caracterer inválido '" + c + "' na posição " + i);
        }
    }

    public static void validarParceira(Parceira parceira) throws ModelException{
        if(parceira == null)
            throw new ModelException("Por favor selecione a empresa parceira a qual esse agente é afiliado.");
    }
}