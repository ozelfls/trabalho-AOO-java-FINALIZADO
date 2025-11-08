package model.dao;

import model.Armazem;
import model.Transporte;

public class DaoTransporte {
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
    private static Transporte[] arrayDeElementos = new Transporte[TAM_INICIAL_ELEMENTOS];

    //
    // MÉTODOS
    //
    public DaoTransporte() {
        super();
    }

    /**
     * Inclui um novo Transporte no array de elementos do Dao
     */
    public boolean incluir(Transporte novo) {
        if(novo == null)
            return false;

        if(obterTransportePelaId(novo.getId()) !=null)
            return false;

        int tamanho = DaoTransporte.arrayDeElementos.length;
        if(DaoTransporte.numElementos == tamanho) {

            Transporte[] novoArray = new Transporte[tamanho + FATOR_CRESCIMENTO];

            for(int i = 0; i < tamanho; i++)
                novoArray[i] = DaoTransporte.arrayDeElementos[i];

            DaoTransporte.arrayDeElementos = novoArray;
        }

        DaoTransporte.arrayDeElementos[ DaoTransporte.numElementos ] = novo;
        DaoTransporte.numElementos++;
        return true;
    }


    /**
     * Altera um Transporte no array de elementos do Dao. Não será preciso
     * realizar nada específico, pois o objeto já deverá estar presente
     * no array
     */
    public boolean alterar(Transporte empAlterado) {
        if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
            return false;
        return true;
    }

    /**
     * Informa a posição do objeto no arrayDeElementos. Se não estiver
     * presente, vamos retornar -1 (NAO_ESTA_PRESENTE)
     */
    public int posicaoDe(Transporte e) {
        for(int i = 0; i < DaoTransporte.numElementos; i++)
            if(DaoTransporte.arrayDeElementos[i] == e)
                return i;
        return -1;
    }


    public boolean remover(Transporte ex) {
        int pos;
        // Varrendo o arrayDeElementos para sabermos em que posição
        // o objeto apontado por 'ex' está
        for(pos = 0; pos < DaoTransporte.numElementos; pos++)
            if(DaoTransporte.arrayDeElementos[pos] == ex)
                break;
        // Se pos é igual ao numElementos, é porque o objeto apontado
        // por 'ex' não está no arrayDeElementos, logo retornamos false
        if(pos == DaoTransporte.numElementos)
            return false;
        // Deslocando os elementos que estão à frente, uma posição para trás
        for(int i = pos; i < DaoTransporte.numElementos-1; i++)
            DaoTransporte.arrayDeElementos[i] = DaoTransporte.arrayDeElementos[i + 1];
        // Colocando null na antiga última posição
        DaoTransporte.arrayDeElementos[numElementos - 1] = null;
        // Decrementando o número de elementos do Dao
        DaoTransporte.numElementos--;
        // retornamos true, informando que efetuamos a operação
        return true;
    }

    public Transporte obterTransportePelaId(int id) {
        // Para cada Transporte presente dentro do array de elementos
        for(int i = 0; i < DaoTransporte.numElementos; i++) {
            int idDoTransporte = DaoTransporte.arrayDeElementos[i].getId();
            if(idDoTransporte == id)
                return DaoTransporte.arrayDeElementos[i];
        }
        return null;
    }

    public static int obterUltimoId() {
        int ultimoId = 0;
        for (int i = 0; i < DaoTransporte.numElementos; i++) {
            if(DaoTransporte.arrayDeElementos[i] != null) {
                int idAtual = DaoTransporte.arrayDeElementos[i].getId();
                if(idAtual > ultimoId) {
                    ultimoId = idAtual;
                }
            }
        }
        return ultimoId;
    }

    public Transporte obterTransportePelaOrigem(Armazem origem) {
        for(int i = 0; i < DaoTransporte.numElementos; i++) {
            Armazem nomeDaOrigem = DaoTransporte.arrayDeElementos[i].getOrigem();
            if(nomeDaOrigem.equals(origem))
                return DaoTransporte.arrayDeElementos[i];
        }
        return null;
    }

    public Transporte obterTransportePeloDestino(Armazem destino) {
        for(int i = 0; i < DaoTransporte.numElementos; i++) {
            Armazem nomeDoDestino = DaoTransporte.arrayDeElementos[i].getDestino();
            if(nomeDoDestino.equals(destino))
                return DaoTransporte.arrayDeElementos[i];
        }
        return null;
    }


    public static Transporte[] obterTodos() {
        return DaoTransporte.arrayDeElementos;
    }


    static void recuperarTodos(Transporte[] array) {
        DaoTransporte.arrayDeElementos = array;
        for(numElementos = 0; numElementos < array.length; numElementos++)
            if(array[numElementos] == null)
                break;
    }
}