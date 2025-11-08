package model.dao;

import model.Item;

public class DaoItem {
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
	private static Item[] arrayDeElementos = new Item[TAM_INICIAL_ELEMENTOS];
	
	//
	// MÉTODOS
	// 
	public DaoItem() {
		super();
	}
	
	/**
	 * Inclui um novo Item no array de elementos do Dao
	 */
	public boolean incluir(Item novo) {
		if(novo == null)
			return false;
		
		//Evita Duplicidade Diretamente no DAO
		if(obterItemPeloNome(novo.getNome()) !=null)
			return false;
		if(obterItemPelaId(novo.getId()) !=null)
			return false;
		
		int tamanho = DaoItem.arrayDeElementos.length;
		if(DaoItem.numElementos == tamanho) {
			
			Item[] novoArray = new Item[tamanho + FATOR_CRESCIMENTO];
			
			for(int i = 0; i < tamanho; i++)
				novoArray[i] = DaoItem.arrayDeElementos[i];
			 
			DaoItem.arrayDeElementos = novoArray;
		}
		
		DaoItem.arrayDeElementos[ DaoItem.numElementos ] = novo;
		DaoItem.numElementos++;
		return true;		
	}
	
	
	/**
	 * Altera um Item no array de elementos do Dao. Não será preciso
	 * realizar nada específico, pois o objeto já deverá estar presente
	 * no array
	 */
	public boolean alterar(Item empAlterado) {
		if(this.posicaoDe(empAlterado) == NAO_ESTA_PRESENTE)
			return false;		
		return true;		
	}
	
	/**
	 * Informa a posição do objeto no arrayDeElementos. Se não estiver
	 * presente, vamos retornar -1 (NAO_ESTA_PRESENTE)
	 */
	public int posicaoDe(Item e) {
		for(int i = 0; i < DaoItem.numElementos; i++)
			if(DaoItem.arrayDeElementos[i] == e)
				return i;
		return -1;
	}
	
	
	public boolean remover(Item ex) {
		int pos;
		// Varrendo o arrayDeElementos para sabermos em que posição
		// o objeto apontado por 'ex' está
		for(pos = 0; pos < DaoItem.numElementos; pos++) 
			if(DaoItem.arrayDeElementos[pos] == ex) 
				break;
		// Se pos é igual ao numElementos, é porque o objeto apontado
		// por 'ex' não está no arrayDeElementos, logo retornamos false
		if(pos == DaoItem.numElementos)
			return false;
		// Deslocando os elementos que estão à frente, uma posição para trás
		for(int i = pos; i < DaoItem.numElementos-1; i++)
			DaoItem.arrayDeElementos[i] = DaoItem.arrayDeElementos[i++];  
		// Colocando null na antiga última posição
		DaoItem.arrayDeElementos[numElementos - 1] = null;
		// Decrementando o número de elementos do Dao
		DaoItem.numElementos--;
		// retornamos true, informando que efetuamos a operação
		return true;
	}
	
	public Item obterItemPelaId(int id) {
		// Para cada Item presente dentro do array de elementos
		for(int i = 0; i < DaoItem.numElementos; i++) {
			int idDoItem = DaoItem.arrayDeElementos[i].getId();
			if(idDoItem == id)
				return DaoItem.arrayDeElementos[i];
		}
		return null;
	}
	
	public static int obterUltimoId() {
		for (int i = 0; i < DaoItem.numElementos; i++) {
			if(DaoItem.arrayDeElementos[i] != null && i == DaoItem.numElementos) {
				return DaoItem.arrayDeElementos[i].getId();
			}
			else if(DaoItem.arrayDeElementos[i] == null && i != 0) {
				return DaoItem.arrayDeElementos[i-1].getId();
			}
		}
		return 0;
	}

	public Item obterItemPeloNome(String nome) {
		for(int i = 0; i < DaoItem.numElementos; i++) {
			String nomeDoItem = DaoItem.arrayDeElementos[i].getNome();
			if(nomeDoItem.equals(nome))
				return DaoItem.arrayDeElementos[i];
		}
		return null;
	}
	
	public Item obterItemPelaSituacaoCadastro(String situacaoCadastro) {
		for(int i = 0; i < DaoItem.numElementos; i++) {
			String situacaoCadastroAtual = DaoItem.arrayDeElementos[i].getNome();
			if(situacaoCadastroAtual.equals(situacaoCadastro))
				return DaoItem.arrayDeElementos[i];
		}
		return null;
	}
	
	

	public static Item[] obterTodos() {
		return DaoItem.arrayDeElementos;
	}
	
	
	static void recuperarTodos(Item[] array) {
		DaoItem.arrayDeElementos = array;
		for(numElementos = 0; numElementos < array.length; numElementos++)
			if(array[numElementos] == null)
				break;
	}
}
