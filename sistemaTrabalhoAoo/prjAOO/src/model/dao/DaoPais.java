package model.dao;

import model.Pais;

public class DaoPais {
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
    private static Pais[] arrayDeElementos = new Pais[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoPais() {
        super();
    }

    /**
     * Inclui um novo Pais no array de elementos do Dao
     */
    public boolean incluir(Pais novo) {
        if(novo == null)
            return false;

        // Verifica se já existe país com mesmo nome (case insensitive)
        if(obterPaisPeloNome(novo.getNome()) != null)
            return false;

        int tamanho = DaoPais.arrayDeElementos.length;
        if(DaoPais.numElementos == tamanho) {
            // Precisa redimensionar
            Pais[] novoArray = new Pais[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoPais.arrayDeElementos[i];

            DaoPais.arrayDeElementos = novoArray;
        }

        DaoPais.arrayDeElementos[DaoPais.numElementos] = novo;
        DaoPais.numElementos++;
        return true;
    }


    /**
     * Altera um Pais no array de elementos do Dao
     */
    public boolean alterar(Pais paisAlterado) {
        if(this.posicaoDe(paisAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos
     */
    public int posicaoDe(Pais e) {
        for(int i = 0; i < DaoPais.numElementos; i++)
            if(DaoPais.arrayDeElementos[i] == e)
                return i;
        return NAO_ESTA_PRESENTE;
    }


    public boolean remover(Pais ex) {
        int pos;
        // Encontrar a posição do elemento
        for(pos = 0; pos < DaoPais.numElementos; pos++)
            if(DaoPais.arrayDeElementos[pos] == ex)
                break;

        if(pos == DaoPais.numElementos)
            return false;

        // Deslocar elementos para trás (CORREÇÃO: i++ estava errado)
        for(int i = pos; i < DaoPais.numElementos - 1; i++)
            DaoPais.arrayDeElementos[i] = DaoPais.arrayDeElementos[i + 1];  // CORREÇÃO: i + 1

        // Colocar null na última posição e decrementar
        DaoPais.arrayDeElementos[DaoPais.numElementos - 1] = null;
        DaoPais.numElementos--;
        return true;
    }

    public Pais obterPaisPelaId(int id) {
        for(int i = 0; i < DaoPais.numElementos; i++) {
            if(DaoPais.arrayDeElementos[i] != null) {
                int idDoPais = DaoPais.arrayDeElementos[i].getId();
                if(idDoPais == id)
                    return DaoPais.arrayDeElementos[i];
            }
        }
        return null;
    }

    public static int obterUltimoId() {
        int ultimoId = 0;
        for (int i = 0; i < DaoPais.numElementos; i++) {
            if(DaoPais.arrayDeElementos[i] != null) {
                int idAtual = DaoPais.arrayDeElementos[i].getId();
                if(idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        }
        return ultimoId;
    }

    public Pais obterPaisPeloNome(String nome) {
        for(int i = 0; i < DaoPais.numElementos; i++) {
            if(DaoPais.arrayDeElementos[i] != null) {
                String nomeDoPais = DaoPais.arrayDeElementos[i].getNome();
                if(nomeDoPais.equalsIgnoreCase(nome)) // CORREÇÃO: equalsIgnoreCase
                    return DaoPais.arrayDeElementos[i];
            }
        }
        return null;
    }


    public static Pais[] obterTodos() {
        return DaoPais.arrayDeElementos;
    }


    static void recuperarTodos(Pais[] array) {
        if(array == null) return;

        DaoPais.arrayDeElementos = array;
        DaoPais.numElementos = 0;

        for(int i = 0; i < array.length; i++) {
            if(array[i] != null) {
                DaoPais.numElementos++;
            } else {
                break;
            }
        }
    }
}