package model.dao;

import model.Armazem;

public class DaoArmazem {
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
    private static Armazem[] arrayDeElementos = new Armazem[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoArmazem() {
        super();
    }

    /**
     * Inclui um novo Armazem no array de elementos do Dao
     */
    public boolean incluir(Armazem novo) {
        if(novo == null)
            return false;

        // Verifica se já existe armazém com mesmo nome
        if(obterArmazemPeloNome(novo.getNome()) != null)
            return false;

        int tamanho = DaoArmazem.arrayDeElementos.length;
        if(DaoArmazem.numElementos == tamanho) {
            // Precisa redimensionar
            Armazem[] novoArray = new Armazem[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoArmazem.arrayDeElementos[i];

            DaoArmazem.arrayDeElementos = novoArray;
        }

        DaoArmazem.arrayDeElementos[DaoArmazem.numElementos] = novo;
        DaoArmazem.numElementos++;
        return true;
    }


    /**
     * Altera um Armazem no array de elementos do Dao
     */
    public boolean alterar(Armazem armazemAlterado) {
        if(this.posicaoDe(armazemAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos
     */
    public int posicaoDe(Armazem e) {
        for(int i = 0; i < DaoArmazem.numElementos; i++)
            if(DaoArmazem.arrayDeElementos[i] == e)
                return i;
        return NAO_ESTA_PRESENTE;
    }


    public boolean remover(Armazem ex) {
        int pos;
        // Encontrar a posição do elemento
        for(pos = 0; pos < DaoArmazem.numElementos; pos++)
            if(DaoArmazem.arrayDeElementos[pos] == ex)
                break;

        if(pos == DaoArmazem.numElementos)
            return false;

        // Deslocar elementos para trás (CORREÇÃO: i+1 em vez de i++)
        for(int i = pos; i < DaoArmazem.numElementos - 1; i++)
            DaoArmazem.arrayDeElementos[i] = DaoArmazem.arrayDeElementos[i + 1];

        // Colocar null na última posição e decrementar
        DaoArmazem.arrayDeElementos[DaoArmazem.numElementos - 1] = null;
        DaoArmazem.numElementos--;
        return true;
    }

    public Armazem obterArmazemPelaId(int id) {
        for(int i = 0; i < DaoArmazem.numElementos; i++) {
            if(DaoArmazem.arrayDeElementos[i] != null) {
                int idDoArmazem = DaoArmazem.arrayDeElementos[i].getId();
                if(idDoArmazem == id)
                    return DaoArmazem.arrayDeElementos[i];
            }
        }
        return null;
    }

    public static int obterUltimoId() {
        int ultimoId = 0;
        for (int i = 0; i < DaoArmazem.numElementos; i++) {
            if(DaoArmazem.arrayDeElementos[i] != null) {
                int idAtual = DaoArmazem.arrayDeElementos[i].getId();
                if(idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        }
        return ultimoId;
    }

    public Armazem obterArmazemPeloNome(String nome) {
        for(int i = 0; i < DaoArmazem.numElementos; i++) {
            if(DaoArmazem.arrayDeElementos[i] != null) {
                String nomeDoArmazem = DaoArmazem.arrayDeElementos[i].getNome();
                if(nomeDoArmazem.equalsIgnoreCase(nome))
                    return DaoArmazem.arrayDeElementos[i];
            }
        }
        return null;
    }


    public static Armazem[] obterTodos() {
        return DaoArmazem.arrayDeElementos;
    }


    static void recuperarTodos(Armazem[] array) {
        if(array == null) return;

        DaoArmazem.arrayDeElementos = array;
        DaoArmazem.numElementos = 0;

        for(int i = 0; i < array.length; i++) {
            if(array[i] != null) {
                DaoArmazem.numElementos++;
            } else {
                break;
            }
        }
    }
}