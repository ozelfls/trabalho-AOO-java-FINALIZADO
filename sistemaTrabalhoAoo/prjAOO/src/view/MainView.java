// MainView.java
package view;

import controller.Controller;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private Controller controller;
    private JTabbedPane tabbedPane;

    public MainView() {
        this.controller = new Controller();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Sistema de Gerenciamento de Logística");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 800);
        setLocationRelativeTo(null);

        tabbedPane = new JTabbedPane();

        // Adicionar abas para cada entidade
        tabbedPane.addTab("Países", createPaisPanel());
        tabbedPane.addTab("Cidades", createCidadePanel());
        tabbedPane.addTab("Parceiras", createParceiraPanel());
        tabbedPane.addTab("Agentes", createAgentePanel());
        tabbedPane.addTab("Armazéns", createArmazemPanel());
        tabbedPane.addTab("Produtos", createProdutoPanel());
        tabbedPane.addTab("Transportes", createTransportePanel());

        // Menu
        JMenuBar menuBar = new JMenuBar();
        JMenu arquivoMenu = new JMenu("Arquivo");
        JMenuItem salvarItem = new JMenuItem("Salvar Dados");
        salvarItem.addActionListener(e -> {
            controller.salvarDados();
            // Forçar atualização de todas as tabelas após salvar
            atualizarTodasTabelas();
        });

        JMenuItem sairItem = new JMenuItem("Sair");
        sairItem.addActionListener(e -> System.exit(0));

        arquivoMenu.add(salvarItem);
        arquivoMenu.addSeparator();
        arquivoMenu.add(sairItem);
        menuBar.add(arquivoMenu);
        setJMenuBar(menuBar);

        add(tabbedPane);
    }

    // Método para atualizar todas as tabelas
    private void atualizarTodasTabelas() {
        // Esta função pode ser expandida para atualizar todas as tabelas se necessário
        JOptionPane.showMessageDialog(this, "Dados salvos e atualizados com sucesso!");
    }

    private JPanel createPaisPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulário
        JPanel formPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo País"));
        JTextField nomeField = new JTextField(20);

        formPanel.add(new JLabel("Nome do País:"));
        formPanel.add(nomeField);

        JButton cadastrarBtn = new JButton("Cadastrar País");
        formPanel.add(new JLabel());
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"ID", "Nome"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Países Cadastrados"));

        // Ações
        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            if (!nome.isEmpty()) {
                if (controller.cadastrarPais(nome)) {
                    JOptionPane.showMessageDialog(this, "País cadastrado com sucesso!");
                    nomeField.setText("");
                    atualizarTabelaPaises(tableModel);
                    // Atualizar combos que dependem de países
                    atualizarCombosDependentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar país! Verifique se já existe.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha o nome do país!");
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        // Carregar dados iniciais
        atualizarTabelaPaises(tableModel);

        return panel;
    }

    private JPanel createCidadePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Nova Cidade"));
        JComboBox<Pais> paisCombo = new JComboBox<>();
        JTextField nomeField = new JTextField(20);
        JTextField estadoField = new JTextField(20);

        // Carregar países no combo
        atualizarComboPaises(paisCombo);

        formPanel.add(new JLabel("País:"));
        formPanel.add(paisCombo);
        formPanel.add(new JLabel("Nome da Cidade:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Estado:"));
        formPanel.add(estadoField);

        JButton cadastrarBtn = new JButton("Cadastrar Cidade");
        JButton atualizarPaisesBtn = new JButton("Atualizar Lista de Países");
        formPanel.add(atualizarPaisesBtn);
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"ID", "Nome", "Estado", "País"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Cidades Cadastradas"));

        cadastrarBtn.addActionListener(e -> {
            Pais pais = (Pais) paisCombo.getSelectedItem();
            String nome = nomeField.getText().trim();
            String estado = estadoField.getText().trim();

            if (pais != null && !nome.isEmpty() && !estado.isEmpty()) {
                if (controller.cadastrarCidade(pais, nome, estado)) {
                    JOptionPane.showMessageDialog(this, "Cidade cadastrada com sucesso!");
                    nomeField.setText("");
                    estadoField.setText("");
                    atualizarTabelaCidades(tableModel);
                    // Atualizar combos que dependem de cidades
                    atualizarCombosDependentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar cidade! Verifique se já existe.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        atualizarPaisesBtn.addActionListener(e -> {
            atualizarComboPaises(paisCombo);
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaCidades(tableModel);

        return panel;
    }

    private JPanel createParceiraPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Nova Parceira"));
        JTextField cnpjField = new JTextField(20);
        JTextField nomeField = new JTextField(20);

        formPanel.add(new JLabel("CNPJ:"));
        formPanel.add(cnpjField);
        formPanel.add(new JLabel("Nome da Empresa:"));
        formPanel.add(nomeField);

        JButton cadastrarBtn = new JButton("Cadastrar Parceira");
        formPanel.add(new JLabel());
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"CNPJ", "Nome"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Parceiras Cadastradas"));

        cadastrarBtn.addActionListener(e -> {
            String cnpj = cnpjField.getText().trim();
            String nome = nomeField.getText().trim();

            if (!cnpj.isEmpty() && !nome.isEmpty()) {
                if (controller.cadastrarParceira(cnpj, nome)) {
                    JOptionPane.showMessageDialog(this, "Parceira cadastrada com sucesso!");
                    cnpjField.setText("");
                    nomeField.setText("");
                    atualizarTabelaParceiras(tableModel);
                    // Atualizar combos que dependem de parceiras
                    atualizarCombosDependentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar parceira! Verifique se já existe.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaParceiras(tableModel);

        return panel;
    }

    private JPanel createAgentePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Agente"));
        JTextField cpfField = new JTextField(20);
        JTextField nomeField = new JTextField(20);
        JComboBox<Parceira> parceiraCombo = new JComboBox<>();

        // Carregar parceiras
        atualizarComboParceiras(parceiraCombo);

        formPanel.add(new JLabel("CPF:"));
        formPanel.add(cpfField);
        formPanel.add(new JLabel("Nome do Agente:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Empresa Parceira:"));
        formPanel.add(parceiraCombo);

        JButton cadastrarBtn = new JButton("Cadastrar Agente");
        JButton atualizarParceirasBtn = new JButton("Atualizar Parceiras");
        formPanel.add(atualizarParceirasBtn);
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"CPF", "Nome", "Parceira"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(120);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Agentes Cadastrados"));

        cadastrarBtn.addActionListener(e -> {
            String cpf = cpfField.getText().trim();
            String nome = nomeField.getText().trim();
            Parceira parceira = (Parceira) parceiraCombo.getSelectedItem();

            if (!cpf.isEmpty() && !nome.isEmpty() && parceira != null) {
                if (controller.cadastrarAgente(cpf, nome, parceira)) {
                    JOptionPane.showMessageDialog(this, "Agente cadastrado com sucesso!");
                    cpfField.setText("");
                    nomeField.setText("");
                    atualizarTabelaAgentes(tableModel);
                    // Atualizar combos que dependem de agentes
                    atualizarCombosDependentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar agente! Verifique se já existe.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        atualizarParceirasBtn.addActionListener(e -> {
            atualizarComboParceiras(parceiraCombo);
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaAgentes(tableModel);

        return panel;
    }

    private JPanel createArmazemPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Armazém"));
        JComboBox<Cidade> cidadeCombo = new JComboBox<>();
        JTextField nomeField = new JTextField(20);
        JTextField capacidadeField = new JTextField(20);

        // Carregar cidades
        atualizarComboCidades(cidadeCombo);

        formPanel.add(new JLabel("Cidade:"));
        formPanel.add(cidadeCombo);
        formPanel.add(new JLabel("Nome do Armazém:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Capacidade (m³):"));
        formPanel.add(capacidadeField);

        JButton cadastrarBtn = new JButton("Cadastrar Armazém");
        JButton atualizarCidadesBtn = new JButton("Atualizar Cidades");
        formPanel.add(atualizarCidadesBtn);
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"ID", "Nome", "Capacidade", "Cidade", "Situação"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(80);
        table.getColumnModel().getColumn(3).setPreferredWidth(150);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Armazéns Cadastrados"));

        cadastrarBtn.addActionListener(e -> {
            Cidade cidade = (Cidade) cidadeCombo.getSelectedItem();
            String nome = nomeField.getText().trim();
            String capacidadeStr = capacidadeField.getText().trim();

            if (cidade != null && !nome.isEmpty() && !capacidadeStr.isEmpty()) {
                try {
                    float capacidade = Float.parseFloat(capacidadeStr);
                    if (capacidade > 0) {
                        if (controller.cadastrarArmazem(cidade, nome, capacidade)) {
                            JOptionPane.showMessageDialog(this, "Armazém cadastrado com sucesso!");
                            nomeField.setText("");
                            capacidadeField.setText("");
                            atualizarTabelaArmazens(tableModel);
                            // Atualizar combos que dependem de armazéns
                            atualizarCombosDependentes();
                        } else {
                            JOptionPane.showMessageDialog(this, "Erro ao cadastrar armazém! Verifique se já existe.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "A capacidade deve ser maior que zero!");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Capacidade deve ser um número válido!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        atualizarCidadesBtn.addActionListener(e -> {
            atualizarComboCidades(cidadeCombo);
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaArmazens(tableModel);

        return panel;
    }

    private JPanel createProdutoPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Produto"));
        JTextField nomeField = new JTextField(20);
        JTextArea descricaoArea = new JTextArea(3, 20);
        JScrollPane descricaoScroll = new JScrollPane(descricaoArea);

        formPanel.add(new JLabel("Nome do Produto:"));
        formPanel.add(nomeField);
        formPanel.add(new JLabel("Descrição:"));
        formPanel.add(descricaoScroll);

        JButton cadastrarBtn = new JButton("Cadastrar Produto");
        formPanel.add(new JLabel());
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"ID", "Nome", "Descrição", "Situação"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(150);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Produtos Cadastrados"));

        cadastrarBtn.addActionListener(e -> {
            String nome = nomeField.getText().trim();
            String descricao = descricaoArea.getText().trim();

            if (!nome.isEmpty() && !descricao.isEmpty()) {
                if (controller.cadastrarProduto(nome, descricao)) {
                    JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!");
                    nomeField.setText("");
                    descricaoArea.setText("");
                    atualizarTabelaProdutos(tableModel);
                    // Atualizar combos que dependem de produtos
                    atualizarCombosDependentes();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar produto! Verifique se já existe.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaProdutos(tableModel);

        return panel;
    }

    private JPanel createTransportePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(7, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Cadastrar Novo Transporte"));

        JComboBox<Armazem> origemCombo = new JComboBox<>();
        JComboBox<Armazem> destinoCombo = new JComboBox<>();
        JComboBox<Agente> agenteCombo = new JComboBox<>();
        JComboBox<Produto> produtoCombo = new JComboBox<>();
        JTextField dataSaidaField = new JTextField(20);
        JTextField dataChegadaField = new JTextField(20);

        // Carregar combos
        atualizarComboArmazens(origemCombo);
        atualizarComboArmazens(destinoCombo);
        atualizarComboAgentes(agenteCombo);
        atualizarComboProdutos(produtoCombo);

        formPanel.add(new JLabel("Armazém Origem:"));
        formPanel.add(origemCombo);
        formPanel.add(new JLabel("Armazém Destino:"));
        formPanel.add(destinoCombo);
        formPanel.add(new JLabel("Agente Responsável:"));
        formPanel.add(agenteCombo);
        formPanel.add(new JLabel("Produto:"));
        formPanel.add(produtoCombo);
        formPanel.add(new JLabel("Data Saída (dd/mm/aaaa):"));
        formPanel.add(dataSaidaField);
        formPanel.add(new JLabel("Data Chegada (dd/mm/aaaa):"));
        formPanel.add(dataChegadaField);

        JButton cadastrarBtn = new JButton("Cadastrar Transporte");
        JButton atualizarBtn = new JButton("Atualizar Listas");
        formPanel.add(atualizarBtn);
        formPanel.add(cadastrarBtn);

        // Tabela
        String[] colunas = {"ID", "Origem", "Destino", "Agente", "Produto", "Data Saída", "Data Chegada", "Situação"};
        DefaultTableModel tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(tableModel);
        table.getColumnModel().getColumn(0).setPreferredWidth(50);
        table.getColumnModel().getColumn(1).setPreferredWidth(120);
        table.getColumnModel().getColumn(2).setPreferredWidth(120);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(100);
        table.getColumnModel().getColumn(6).setPreferredWidth(100);
        table.getColumnModel().getColumn(7).setPreferredWidth(120);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Transportes Cadastrados"));

        cadastrarBtn.addActionListener(e -> {
            Armazem origem = (Armazem) origemCombo.getSelectedItem();
            Armazem destino = (Armazem) destinoCombo.getSelectedItem();
            Agente agente = (Agente) agenteCombo.getSelectedItem();
            Produto produto = (Produto) produtoCombo.getSelectedItem();
            String dataSaida = dataSaidaField.getText().trim();
            String dataChegada = dataChegadaField.getText().trim();

            if (origem != null && destino != null && agente != null && produto != null &&
                    !dataSaida.isEmpty() && !dataChegada.isEmpty()) {
                if (controller.cadastrarTransporte(dataSaida, dataChegada, origem, destino, agente, produto)) {
                    JOptionPane.showMessageDialog(this, "Transporte cadastrado com sucesso!");
                    dataSaidaField.setText("");
                    dataChegadaField.setText("");
                    atualizarTabelaTransportes(tableModel);
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao cadastrar transporte!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!");
            }
        });

        atualizarBtn.addActionListener(e -> {
            atualizarComboArmazens(origemCombo);
            atualizarComboArmazens(destinoCombo);
            atualizarComboAgentes(agenteCombo);
            atualizarComboProdutos(produtoCombo);
        });

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        panel.add(mainPanel);

        atualizarTabelaTransportes(tableModel);

        return panel;
    }

    // Métodos auxiliares para atualizar combos
    private void atualizarComboPaises(JComboBox<Pais> combo) {
        combo.removeAllItems();
        for (Pais pais : controller.listarPaises()) {
            if (pais != null) combo.addItem(pais);
        }
    }

    private void atualizarComboCidades(JComboBox<Cidade> combo) {
        combo.removeAllItems();
        for (Cidade cidade : controller.listarCidades()) {
            if (cidade != null) combo.addItem(cidade);
        }
    }

    private void atualizarComboParceiras(JComboBox<Parceira> combo) {
        combo.removeAllItems();
        for (Parceira parceira : controller.listarParceiras()) {
            if (parceira != null) combo.addItem(parceira);
        }
    }

    private void atualizarComboAgentes(JComboBox<Agente> combo) {
        combo.removeAllItems();
        for (Agente agente : controller.listarAgentes()) {
            if (agente != null) combo.addItem(agente);
        }
    }

    private void atualizarComboArmazens(JComboBox<Armazem> combo) {
        combo.removeAllItems();
        for (Armazem armazem : controller.listarArmazens()) {
            if (armazem != null) combo.addItem(armazem);
        }
    }

    private void atualizarComboProdutos(JComboBox<Produto> combo) {
        combo.removeAllItems();
        for (Produto produto : controller.listarProdutos()) {
            if (produto != null) combo.addItem(produto);
        }
    }

    // Método para atualizar todos os combos dependentes
    private void atualizarCombosDependentes() {
        // Este método pode ser chamado quando dados importantes são atualizados
        // para garantir que todos os combos estejam sincronizados
    }

    // Métodos para atualizar tabelas
    private void atualizarTabelaPaises(DefaultTableModel model) {
        model.setRowCount(0);
        for (Pais pais : controller.listarPaises()) {
            if (pais != null) {
                model.addRow(new Object[]{pais.getId(), pais.getNome()});
            }
        }
    }

    private void atualizarTabelaCidades(DefaultTableModel model) {
        model.setRowCount(0);
        for (Cidade cidade : controller.listarCidades()) {
            if (cidade != null) {
                model.addRow(new Object[]{
                        cidade.getId(),
                        cidade.getNome(),
                        cidade.getEstado(),
                        cidade.getPais() != null ? cidade.getPais().getNome() : "N/A"
                });
            }
        }
    }

    private void atualizarTabelaParceiras(DefaultTableModel model) {
        model.setRowCount(0);
        for (Parceira parceira : controller.listarParceiras()) {
            if (parceira != null) {
                model.addRow(new Object[]{parceira.getCnpj(), parceira.getNome()});
            }
        }
    }

    private void atualizarTabelaAgentes(DefaultTableModel model) {
        model.setRowCount(0);
        for (Agente agente : controller.listarAgentes()) {
            if (agente != null) {
                model.addRow(new Object[]{
                        agente.getCpf(),
                        agente.getNome(),
                        agente.getParceira() != null ? agente.getParceira().getNome() : "N/A"
                });
            }
        }
    }

    private void atualizarTabelaArmazens(DefaultTableModel model) {
        model.setRowCount(0);
        for (Armazem armazem : controller.listarArmazens()) {
            if (armazem != null) {
                model.addRow(new Object[]{
                        armazem.getId(),
                        armazem.getNome(),
                        armazem.getCapacidade(),
                        armazem.getCidade() != null ? armazem.getCidade().getNome() : "N/A",
                        armazem.getSituacaoCadastro()
                });
            }
        }
    }

    private void atualizarTabelaProdutos(DefaultTableModel model) {
        model.setRowCount(0);
        for (Produto produto : controller.listarProdutos()) {
            if (produto != null) {
                model.addRow(new Object[]{
                        produto.getId(),
                        produto.getNome(),
                        produto.getDescricao(),
                        produto.getSituacaoCadastro()
                });
            }
        }
    }

    private void atualizarTabelaTransportes(DefaultTableModel model) {
        model.setRowCount(0);
        for (Transporte transporte : controller.listarTransportes()) {
            if (transporte != null) {
                model.addRow(new Object[]{
                        transporte.getId(),
                        transporte.getOrigem() != null ? transporte.getOrigem().getNome() : "N/A",
                        transporte.getDestino() != null ? transporte.getDestino().getNome() : "N/A",
                        transporte.getAgente() != null ? transporte.getAgente().getNome() : "N/A",
                        transporte.getProduto() != null ? transporte.getProduto().getNome() : "N/A",
                        transporte.getDataSaida(),
                        transporte.getDataChegada(),
                        transporte.getSituacaoCadastro()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView view = new MainView();
            view.setVisible(true);
        });
    }
}