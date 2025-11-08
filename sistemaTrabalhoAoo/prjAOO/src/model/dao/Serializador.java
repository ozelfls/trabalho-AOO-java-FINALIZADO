package model.dao;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import model.Pais;
import model.Parceira;
import model.Produto;
import model.Transporte;
import model.Agente;
import model.Armazem;
import model.Cidade;

public class Serializador {

    public static void salvarObjetos() {
        try {
            // Arquivo o arquivo "objetos.dat" para escrita
            FileOutputStream fos = new FileOutputStream("objetos.dat");
            // Instanciando um objeto de serialização vinculado ao arquivo
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // Para cada DAO presente em nosso projeto, recuperamos a
            // referência para o array que aponta para os objetos da classe
            // que ele gerencia o armazenamento

            oos.writeObject(DaoPais.obterTodos());
            oos.writeObject(DaoCidade.obterTodos());
            oos.writeObject(DaoAgente.obterTodos());
            oos.writeObject(DaoArmazem.obterTodos());
            oos.writeObject(DaoParceira.obterTodos());
            oos.writeObject(DaoProduto.obterTodos());
            oos.writeObject(DaoTransporte.obterTodos());

            // Efetiva a gravação do arquivo
            oos.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problema no salvamento dos objetos: " + e.getMessage());
        }
    }

    public static void recuperarObjetos() {
        try {
            // Arquivo o arquivo "objetos.dat" para leitura
            FileInputStream fis = new FileInputStream("objetos.dat");
            // Instanciando um objeto de serialização vinculado ao arquivo
            ObjectInputStream ois = new ObjectInputStream(fis);

            // Para cada DAO presente em nosso projeto, recuperamos o
            // array que contém os objetos salvos no arquivo. A ordem de
            // leitura precisa ser exatamente a mesma com que os objetos
            // foram salvos.

            DaoPais.recuperarTodos((Pais[])ois.readObject());
            DaoCidade.recuperarTodos((Cidade[])ois.readObject());
            DaoAgente.recuperarTodos((Agente[])ois.readObject());
            DaoArmazem.recuperarTodos((Armazem[])ois.readObject());
            DaoParceira.recuperarTodos((Parceira[])ois.readObject());
            DaoProduto.recuperarTodos((Produto[])ois.readObject());
            DaoTransporte.recuperarTodos((Transporte[])ois.readObject());

            // Fechando o arquivo
            ois.close();
        } catch (IOException e) {
            // Arquivo não existe ainda - primeira execução do programa
            // Não mostra mensagem de erro, é normal na primeira execução
            System.out.println("Arquivo de dados não encontrado. Iniciando com dados vazios.");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Problema na recuperação dos objetos: " + e.getMessage());
        }
    }

}