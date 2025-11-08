package model.dao;

import model.Parceira;

public class DaoParceira {
    //
    // CONSTANTES
    //
    final public static int TAM_INICIAL_ELEMENTOS = 5;
    final public static int FATOR_CRESCIMENTO = 3;
    final public static int NAO_ESTA_PRESENTE = -1;

    //
    // ATRIBUTOS
    //
    private static int numElementos = 0;
    private static Parceira[] arrayDeElementos = new Parceira[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoParceira() {
        super();
    }

    /**
     * Inclui um novo Parceira no array de elementos do Dao
     */
    public boolean incluir(Parceira novo) {
        if(novo == null)
            return false;

        //Evita Dupliccnpjade Diretamente no DAO
        if(obterParceiraPeloNome(novo.getNome()) != null)
            return false;
        if(obterParceiraPelaCnpj(novo.getCnpj()) != null)
            return false;

        int tamanho = DaoParceira.arrayDeElementos.length;
        if(DaoParceira.numElementos == tamanho) {

            Parceira[] novoArray = new Parceira[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoParceira.arrayDeElementos[i];

            DaoParceira.arrayDeElementos = novoArray;
        }

        DaoParceira.arrayDeElementos[ DaoParceira.numElementos ] = novo;
        DaoParceira.numElementos++;
        return true;
    }


    /**
     * Altera um Parceira no array de elementos do Dao. Não será preciso
     * realizar nada específico, pois o objeto já deverá estar presente
     * no array
     */
    public boolean alterar(Parceira empAlterado) {
        if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos. Se não estiver
     * presente, vamos retornar -1 (NAO_ESTA_PRESENTE)
     */
    public int posicaoDe(Parceira e) {
        for(int i = 0; i < DaoParceira.numElementos; i++)
            if(DaoParceira.arrayDeElementos[i] == e)
                return i;
        return -1;
    }


    public boolean remover(Parceira ex) {
        int pos;
        // Varrendo o arrayDeElementos para sabermos em que posição
        // o objeto apontado por 'ex' está
        for(pos = 0; pos < DaoParceira.numElementos; pos++)
            if(DaoParceira.arrayDeElementos[pos] == ex)
                break;
        // Se pos é igual ao numElementos, é porque o objeto apontado
        // por 'ex' não está no arrayDeElementos, logo retornamos false
        if(pos == DaoParceira.numElementos)
            return false;
        // Deslocando os elementos que estão à frente, uma posição para trás
        for(int i = pos; i < DaoParceira.numElementos-1; i++)
            DaoParceira.arrayDeElementos[i] = DaoParceira.arrayDeElementos[i + 1];
        // Colocando null na antiga última posição
        DaoParceira.arrayDeElementos[numElementos - 1] = null;
        // Decrementando o número de elementos do Dao
        DaoParceira.numElementos--;
        // retornamos true, informando que efetuamos a operação
        return true;
    }

    public Parceira obterParceiraPelaCnpj(String cnpj) {
        // Para cada Parceira presente dentro do array de elementos
        for(int i = 0; i < DaoParceira.numElementos; i++) {
            String cnpjDoParceira = DaoParceira.arrayDeElementos[i].getCnpj();
            if(cnpjDoParceira.equals(cnpj))
                return DaoParceira.arrayDeElementos[i];
        }
        return null;
    }

    public Parceira obterParceiraPeloNome(String nome) {
        for(int i = 0; i < DaoParceira.numElementos; i++) {
            String nomeDoParceira = DaoParceira.arrayDeElementos[i].getNome();
            if(nomeDoParceira.equals(nome))
                return DaoParceira.arrayDeElementos[i];
        }
        return null;
    }


    public static Parceira[] obterTodos() {
        return DaoParceira.arrayDeElementos;
    }


    static void recuperarTodos(Parceira[] array) {
        DaoParceira.arrayDeElementos = array;
        for(numElementos = 0; numElementos < array.length; numElementos++)
            if(array[numElementos] == null)
                break;
    }
}