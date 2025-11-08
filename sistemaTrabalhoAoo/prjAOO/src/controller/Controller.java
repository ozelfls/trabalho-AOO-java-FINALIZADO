package controller;

import model.*;
import model.dao.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private DaoPais daoPais;
    private DaoCidade daoCidade;
    private DaoAgente daoAgente;
    private DaoArmazem daoArmazem;
    private DaoParceira daoParceira;
    private DaoProduto daoProduto;
    private DaoItem daoItem;
    private DaoTransporte daoTransporte;

    public Controller() {
        this.daoPais = new DaoPais();
        this.daoCidade = new DaoCidade();
        this.daoAgente = new DaoAgente();
        this.daoArmazem = new DaoArmazem();
        this.daoParceira = new DaoParceira();
        this.daoProduto = new DaoProduto();
        this.daoItem = new DaoItem();
        this.daoTransporte = new DaoTransporte();

        // Carregar dados salvos
        Serializador.recuperarObjetos();
    }

    // Métodos para Pais
    public boolean cadastrarPais(String nome) {
        try {
            Pais pais = new Pais(nome);
            return daoPais.incluir(pais);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Pais[] listarPaises() {
        return DaoPais.obterTodos();
    }

    public Pais buscarPaisPorNome(String nome) {
        return daoPais.obterPaisPeloNome(nome);
    }

    // Métodos para Cidade
    public boolean cadastrarCidade(Pais pais, String nome, String estado) {
        try {
            Cidade cidade = new Cidade(pais, nome, estado);
            return daoCidade.incluir(cidade);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Cidade[] listarCidades() {
        return DaoCidade.obterTodos();
    }

    // Métodos para Parceira
    public boolean cadastrarParceira(String cnpj, String nome) {
        try {
            Parceira parceira = new Parceira(cnpj, nome);
            return daoParceira.incluir(parceira);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Parceira[] listarParceiras() {
        return DaoParceira.obterTodos();
    }

    // Métodos para Agente
    public boolean cadastrarAgente(String cpf, String nome, Parceira parceira) {
        try {
            Agente agente = new Agente(cpf, nome, parceira);
            return daoAgente.incluir(agente);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Agente[] listarAgentes() {
        return DaoAgente.obterTodos();
    }

    // Métodos para Armazem
    public boolean cadastrarArmazem(Cidade cidade, String nome, float capacidade) {
        try {
            Armazem armazem = new Armazem(cidade, nome, capacidade);
            return daoArmazem.incluir(armazem);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Armazem[] listarArmazens() {
        return DaoArmazem.obterTodos();
    }

    // Métodos para Item
    public boolean cadastrarItem(String nome, String descricao, String situacao) {
        try {
            Item item = new Item(nome, descricao, situacao);
            return daoItem.incluir(item);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Item[] listarItens() {
        return DaoItem.obterTodos();
    }

    // Métodos para Produto
    public boolean cadastrarProduto(String nome, String descricao) {
        try {
            Produto produto = new Produto(nome, descricao);
            return daoProduto.incluir(produto);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Produto[] listarProdutos() {
        return DaoProduto.obterTodos();
    }

    // Métodos para Transporte - ATUALIZADOS
    public boolean cadastrarTransporte(String dataSaida, String dataChegada,
                                       Armazem origem, Armazem destino,
                                       Agente agente, Produto produto) {
        try {
            Transporte transporte = new Transporte(dataSaida, dataChegada, "Análise",
                    origem, destino, agente, produto);
            return daoTransporte.incluir(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public Transporte[] listarTransportes() {
        return DaoTransporte.obterTodos();
    }

    // Métodos específicos para Transporte
    public boolean atribuirAgenteTransporte(Transporte transporte, Agente agente) {
        try {
            transporte.atribuirAgente(agente);
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean adicionarProdutoTransporte(Transporte transporte, Produto produto) {
        try {
            transporte.adicionarProduto(produto);
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean removerAgenteTransporte(Transporte transporte) {
        try {
            transporte.removerAgente();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean removerProdutoTransporte(Transporte transporte) {
        try {
            transporte.removerProduto();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    // Métodos para buscar transportes específicos
    public Transporte buscarTransportePorId(int id) {
        return daoTransporte.obterTransportePelaId(id);
    }

    public Transporte buscarTransportePorOrigem(Armazem origem) {
        return daoTransporte.obterTransportePelaOrigem(origem);
    }

    public Transporte buscarTransportePorDestino(Armazem destino) {
        return daoTransporte.obterTransportePeloDestino(destino);
    }

    public List<Transporte> buscarTransportesPorAgente(Agente agente) {
        List<Transporte> transportesDoAgente = new ArrayList<>();
        for (Transporte transporte : listarTransportes()) {
            if (transporte != null && transporte.getAgente() != null &&
                    transporte.getAgente().equals(agente)) {
                transportesDoAgente.add(transporte);
            }
        }
        return transportesDoAgente;
    }

    public List<Transporte> buscarTransportesPorProduto(Produto produto) {
        List<Transporte> transportesDoProduto = new ArrayList<>();
        for (Transporte transporte : listarTransportes()) {
            if (transporte != null && transporte.getProduto() != null &&
                    transporte.getProduto().equals(produto)) {
                transportesDoProduto.add(transporte);
            }
        }
        return transportesDoProduto;
    }

    public List<Transporte> buscarTransportesPorSituacao(String situacao) {
        List<Transporte> transportesPorSituacao = new ArrayList<>();
        for (Transporte transporte : listarTransportes()) {
            if (transporte != null && transporte.getSituacaoCadastro() != null &&
                    transporte.getSituacaoCadastro().equalsIgnoreCase(situacao)) {
                transportesPorSituacao.add(transporte);
            }
        }
        return transportesPorSituacao;
    }

    // Métodos para alterar status do Transporte
    public boolean aprovarTransporte(Transporte transporte) {
        try {
            transporte.aprovarTransporte();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean reprovarTransporte(Transporte transporte) {
        try {
            transporte.reprovarTransporte();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean agendarTransporte(Transporte transporte) {
        try {
            transporte.agendarTransporte();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean iniciarTransporte(Transporte transporte) {
        try {
            transporte.transportar();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean finalizarTransporte(Transporte transporte) {
        try {
            transporte.entregar();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean recondicionarTransporte(Transporte transporte) {
        try {
            transporte.recondicionarTransporte();
            return daoTransporte.alterar(transporte);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    // Métodos para verificar status
    public boolean transporteProntoParaEnvio(Transporte transporte) {
        return transporte != null && transporte.prontoParaTransporte();
    }

    public boolean transporteTemAgente(Transporte transporte) {
        return transporte != null && transporte.temAgente();
    }

    public boolean transporteTemProduto(Transporte transporte) {
        return transporte != null && transporte.temProduto();
    }

    // Métodos para alterar status de outras entidades
    public boolean aprovarProduto(Produto produto) {
        try {
            produto.aprovarProduto();
            return daoProduto.alterar(produto);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean reprovarProduto(Produto produto) {
        try {
            produto.reprovarProduto();
            return daoProduto.alterar(produto);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean aprovarArmazem(Armazem armazem) {
        try {
            armazem.aprovarArmazem();
            return daoArmazem.alterar(armazem);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    public boolean reprovarArmazem(Armazem armazem) {
        try {
            armazem.reprovarArmazem();
            return daoArmazem.alterar(armazem);
        } catch (ModelException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        }
    }

    // Salvar dados
    public void salvarDados() {
        Serializador.salvarObjetos();
        JOptionPane.showMessageDialog(null, "Dados salvos com sucesso!");
    }

    // Métodos de remoção
    public boolean removerTransporte(Transporte transporte) {
        return daoTransporte.remover(transporte);
    }

    public boolean removerProduto(Produto produto) {
        return daoProduto.remover(produto);
    }

    public boolean removerAgente(Agente agente) {
        return daoAgente.remover(agente);
    }

    public boolean removerArmazem(Armazem armazem) {
        return daoArmazem.remover(armazem);
    }

    public boolean removerCidade(Cidade cidade) {
        return daoCidade.remover(cidade);
    }

    public boolean removerPais(Pais pais) {
        return daoPais.remover(pais);
    }

    public boolean removerParceira(Parceira parceira) {
        return daoParceira.remover(parceira);
    }
}