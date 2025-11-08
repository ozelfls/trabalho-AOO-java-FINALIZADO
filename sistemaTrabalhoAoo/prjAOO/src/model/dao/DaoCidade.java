package model.dao;

import model.Cidade;

public class DaoCidade {
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
    private static Cidade[] arrayDeElementos = new Cidade[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoCidade() {
        super();
    }

    /**
     * Inclui um novo Cidade no array de elementos do Dao
     */
    public boolean incluir(Cidade novo) {
        if(novo == null)
            return false;

        //Evita Duplicidade Diretamente no DAO
        if(obterCidadePeloNome(novo.getNome()) !=null)
            return false;
        if(obterCidadePelaId(novo.getId()) !=null)
            return false;

        int tamanho = DaoCidade.arrayDeElementos.length;
        if(DaoCidade.numElementos == tamanho) {

            Cidade[] novoArray = new Cidade[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoCidade.arrayDeElementos[i];

            DaoCidade.arrayDeElementos = novoArray;
        }

        DaoCidade.arrayDeElementos[ DaoCidade.numElementos ] = novo;
        DaoCidade.numElementos++;
        return true;
    }



    public boolean alterar(Cidade empAlterado) {
        if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }


    public int posicaoDe(Cidade e) {
        for(int i = 0; i < DaoCidade.numElementos; i++)
            if(DaoCidade.arrayDeElementos[i] == e)
                return i;
        return -1;
    }


    public boolean remover(Cidade ex) {
        int pos;

        for(pos = 0; pos < DaoCidade.numElementos; pos++)
            if(DaoCidade.arrayDeElementos[pos] == ex)
                break;

        if(pos == DaoCidade.numElementos)
            return false;
        // Deslocando os elementos que estão à frente, uma posição para trás
        for(int i = pos; i < DaoCidade.numElementos-1; i++)
            DaoCidade.arrayDeElementos[i] = DaoCidade.arrayDeElementos[i + 1];  // CORREÇÃO: i + 1
        // Colocando null na antiga última posição
        DaoCidade.arrayDeElementos[numElementos - 1] = null;
        // Decrementando o número de elementos do Dao
        DaoCidade.numElementos--;
        return true;
    }

    public Cidade obterCidadePelaId(int id) {
        // Para cada Cidade presente dentro do array de elementos
        for(int i = 0; i < DaoCidade.numElementos; i++) {
            int idDoCidade = DaoCidade.arrayDeElementos[i].getId();
            if(idDoCidade == id)
                return DaoCidade.arrayDeElementos[i];
        }
        return null;
    }

    public static int obterUltimoId() {
        int ultimoId = 0;
        for (int i = 0; i < DaoCidade.numElementos; i++) {
            if(DaoCidade.arrayDeElementos[i] != null) {
                int idAtual = DaoCidade.arrayDeElementos[i].getId();
                if(idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        }
        return ultimoId;
    }


    public Cidade obterCidadePeloNome(String nome) {
        for(int i = 0; i < DaoCidade.numElementos; i++) {
            String nomeDoCidade = DaoCidade.arrayDeElementos[i].getNome();
            if(nomeDoCidade.equals(nome))
                return DaoCidade.arrayDeElementos[i];
        }
        return null;
    }


    public static Cidade[] obterTodos() {
        return DaoCidade.arrayDeElementos;
    }


    static void recuperarTodos(Cidade[] array) {
        DaoCidade.arrayDeElementos = array;
        for(numElementos = 0; numElementos < array.length; numElementos++)
            if(array[numElementos] == null)
                break;
    }
}