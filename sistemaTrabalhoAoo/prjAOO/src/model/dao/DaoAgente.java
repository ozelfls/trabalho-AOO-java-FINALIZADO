package model.dao;

import model.Agente;

public class DaoAgente {
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
    private static Agente[] arrayDeElementos = new Agente[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoAgente() {
        super();
    }

    /**
     * Inclui um novo Agente no array de elementos do Dao
     */
    public boolean incluir(Agente novo) {
        if(novo == null)
            return false;

        //Evita Dupliccpfade Diretamente no DAO
        if(obterAgentePeloNome(novo.getNome()) !=null)
            return false;
        if(obterAgentePelaCpf(novo.getCpf()) !=null)
            return false;

        int tamanho = DaoAgente.arrayDeElementos.length;
        if(DaoAgente.numElementos == tamanho) {

            Agente[] novoArray = new Agente[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoAgente.arrayDeElementos[i];

            DaoAgente.arrayDeElementos = novoArray;
        }

        DaoAgente.arrayDeElementos[ DaoAgente.numElementos ] = novo;
        DaoAgente.numElementos++;
        return true;
    }


    /**
     * Altera um Agente no array de elementos do Dao. Não será preciso
     * realizar nada específico, pois o objeto já deverá estar presente
     * no array
     */
    public boolean alterar(Agente empAlterado) {
        if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos. Se não estiver
     * presente, vamos retornar -1 (NAO_ESTA_PRESENTE)
     */
    public int posicaoDe(Agente e) {
        for(int i = 0; i < DaoAgente.numElementos; i++)
            if(DaoAgente.arrayDeElementos[i] == e)
                return i;
        return -1;
    }


    public boolean remover(Agente ex) {
        int pos;
        // Varrendo o arrayDeElementos para sabermos em que posição
        // o objeto apontado por 'ex' está
        for(pos = 0; pos < DaoAgente.numElementos; pos++)
            if(DaoAgente.arrayDeElementos[pos] == ex)
                break;
        // Se pos é igual ao numElementos, é porque o objeto apontado
        // por 'ex' não está no arrayDeElementos, logo retornamos false
        if(pos == DaoAgente.numElementos)
            return false;
        // Deslocando os elementos que estão à frente, uma posição para trás
        for(int i = pos; i < DaoAgente.numElementos-1; i++)
            DaoAgente.arrayDeElementos[i] = DaoAgente.arrayDeElementos[i + 1];
        // Colocando null na antiga última posição
        DaoAgente.arrayDeElementos[numElementos - 1] = null;
        // Decrementando o número de elementos do Dao
        DaoAgente.numElementos--;
        // retornamos true, informando que efetuamos a operação
        return true;
    }

    public Agente obterAgentePelaCpf(String cpf) {
        // Para cada Agente presente dentro do array de elementos
        for(int i = 0; i < DaoAgente.numElementos; i++) {
            String cpfDoAgente = DaoAgente.arrayDeElementos[i].getCpf();
            if(cpfDoAgente.equals(cpf))
                return DaoAgente.arrayDeElementos[i];
        }
        return null;
    }


    public Agente obterAgentePeloNome(String nome) {
        for(int i = 0; i < DaoAgente.numElementos; i++) {
            String nomeDoAgente = DaoAgente.arrayDeElementos[i].getNome();
            if(nomeDoAgente.equals(nome))
                return DaoAgente.arrayDeElementos[i];
        }
        return null;
    }


    public static Agente[] obterTodos() {
        return DaoAgente.arrayDeElementos;
    }


    static void recuperarTodos(Agente[] array) {
        DaoAgente.arrayDeElementos = array;
        for(numElementos = 0; numElementos < array.length; numElementos++)
            if(array[numElementos] == null)
                break;
    }
}