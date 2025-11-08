package model.dao;

import model.Produto;

public class DaoProduto {
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
    private static Produto[] arrayDeElementos = new Produto[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoProduto() {
        super();
    }

    /**
     * Inclui um novo Produto no array de elementos do Dao
     */
    public boolean incluir(Produto novo) {
        if(novo == null)
            return false;

        //Evita Duplicidade Diretamente no DAO
        if(obterProdutoPeloNome(novo.getNome()) !=null)
            return false;
        if(obterProdutoPelaId(novo.getId()) !=null)
            return false;

        int tamanho = DaoProduto.arrayDeElementos.length;
        if(DaoProduto.numElementos == tamanho) {

            Produto[] novoArray = new Produto[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoProduto.arrayDeElementos[i];

            DaoProduto.arrayDeElementos = novoArray;
        }

        DaoProduto.arrayDeElementos[ DaoProduto.numElementos ] = novo;
        DaoProduto.numElementos++;
        return true;
    }


    /**
     * Altera um Produto no array de elementos do Dao. Não será preciso
     * realizar nada específico, pois o objeto já deverá estar presente
     * no array
     */
    public boolean alterar(Produto empAlterado) {
        if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos. Se não estiver
     * presente, vamos retornar -1 (NAO_ESTA_PRESENTE)
     */
    public int posicaoDe(Produto e) {
        for(int i = 0; i < DaoProduto.numElementos; i++)
            if(DaoProduto.arrayDeElementos[i] == e)
                return i;
        return -1;
    }


    public boolean remover(Produto ex) {
        int pos;
        // Varrendo o arrayDeElementos para sabermos em que posição
        // o objeto apontado por 'ex' está
        for(pos = 0; pos < DaoProduto.numElementos; pos++)
            if(DaoProduto.arrayDeElementos[pos] == ex)
                break;
        // Se pos é igual ao numElementos, é porque o objeto apontado
        // por 'ex' não está no arrayDeElementos, logo retornamos false
        if(pos == DaoProduto.numElementos)
            return false;
        // Deslocando os elementos que estão à frente, uma posição para trás
        for(int i = pos; i < DaoProduto.numElementos-1; i++)
            DaoProduto.arrayDeElementos[i] = DaoProduto.arrayDeElementos[i + 1];
        // Colocando null na antiga última posição
        DaoProduto.arrayDeElementos[numElementos - 1] = null;
        // Decrementando o número de elementos do Dao
        DaoProduto.numElementos--;
        // retornamos true, informando que efetuamos a operação
        return true;
    }

    public Produto obterProdutoPelaId(int id) {
        // Para cada Produto presente dentro do array de elementos
        for(int i = 0; i < DaoProduto.numElementos; i++) {
            int idDoProduto = DaoProduto.arrayDeElementos[i].getId();
            if(idDoProduto == id)
                return DaoProduto.arrayDeElementos[i];
        }
        return null;
    }

    public static int obterUltimoId() {
        int ultimoId = 0;
        for (int i = 0; i < DaoProduto.numElementos; i++) {
            if(DaoProduto.arrayDeElementos[i] != null) {
                int idAtual = DaoProduto.arrayDeElementos[i].getId();
                if(idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        }
        return ultimoId;
    }

    public Produto obterProdutoPeloNome(String nome) {
        for(int i = 0; i < DaoProduto.numElementos; i++) {
            String nomeDoProduto = DaoProduto.arrayDeElementos[i].getNome();
            if(nomeDoProduto.equals(nome))
                return DaoProduto.arrayDeElementos[i];
        }
        return null;
    }

    public Produto obterProdutoPelaSituacaoCadastro(String situacaoCadastro) {
        for(int i = 0; i < DaoProduto.numElementos; i++) {
            String situacaoCadastroAtual = DaoProduto.arrayDeElementos[i].getNome();
            if(situacaoCadastroAtual.equals(situacaoCadastro))
                return DaoProduto.arrayDeElementos[i];
        }
        return null;
    }


    public static Produto[] obterTodos() {
        return DaoProduto.arrayDeElementos;
    }


    static void recuperarTodos(Produto[] array) {
        DaoProduto.arrayDeElementos = array;
        for(numElementos = 0; numElementos < array.length; numElementos++)
            if(array[numElementos] == null)
                break;
    }
}