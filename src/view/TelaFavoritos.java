package view;

import controller.ListaController;
import controller.VideoController;
import model.*;
import javax.swing.*;
import java.util.ArrayList;

public class TelaFavoritos extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaFavoritos.class.getName());

    private model.Usuario usuarioLogado;
    private controller.ListaController listaCtrl = new controller.ListaController();
    private controller.VideoController videoCtrl = new controller.VideoController();
    private DefaultListModel<model.ListaReproducao> modelListas = new DefaultListModel<>();
    private DefaultListModel<model.Video> modelVideos = new DefaultListModel<>();

    public TelaFavoritos(model.Usuario usuario) {
        this.usuarioLogado = usuario;
        initComponents();
        setLocationRelativeTo(null);
        jListListas.setModel(modelListas);
        jListVideos.setModel(modelVideos);
        jBtnVoltar.addActionListener(e -> dispose());
        jBtnEditar.addActionListener(e -> editarLista());
        jBtnExcluir.addActionListener(e -> excluirLista());
        jBtnNovaLista.addActionListener(e -> criarLista());
        jBtnAdicionar.addActionListener(e -> adicionarVideo());
        jBtnRemover.addActionListener(e -> removerVideo());
        jListListas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarVideosDaLista();
        });
        carregarListas();
    }

    private void carregarListas() {
        modelListas.clear();
        for (model.ListaReproducao l : listaCtrl.listarPorUsuario(usuarioLogado.getId()))
            modelListas.addElement(l);
    }

    private void carregarVideosDaLista() {
        modelVideos.clear();
        model.ListaReproducao sel = jListListas.getSelectedValue();
        if (sel == null) return;
        for (model.Video v : listaCtrl.listarVideos(sel.getId()))
            modelVideos.addElement(v);
    }

    private void criarLista() {
        String nome = JOptionPane.showInputDialog(this, "Nome da nova lista:");
        if (nome == null || nome.trim().isEmpty()) return;
        listaCtrl.criar(nome.trim(), usuarioLogado.getId());
        carregarListas();
    }

    private void editarLista() {
        model.ListaReproducao sel = jListListas.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista."); return; }
        String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", sel.getNome());
        if (novoNome == null || novoNome.trim().isEmpty()) return;
        listaCtrl.editar(sel.getId(), novoNome.trim());
        carregarListas();
    }

    private void excluirLista() {
        model.ListaReproducao sel = jListListas.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir lista '" + sel.getNome() + "'?");
        if (confirm == JOptionPane.YES_OPTION) {
            listaCtrl.excluir(sel.getId());
            carregarListas();
            modelVideos.clear();
        }
    }

    private void adicionarVideo() {
        model.ListaReproducao lista = jListListas.getSelectedValue();
        if (lista == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista primeiro."); return; }
        ArrayList<model.Video> todos = videoCtrl.listarTodos();
        model.Video[] arr = todos.toArray(new model.Video[0]);
        model.Video escolhido = (model.Video) JOptionPane.showInputDialog(
            this, "Escolha o vídeo:", "Adicionar à Lista",
            JOptionPane.PLAIN_MESSAGE, null, arr, arr.length > 0 ? arr[0] : null
        );
        if (escolhido == null) return;
        listaCtrl.adicionarVideo(lista.getId(), escolhido.getId());
        carregarVideosDaLista();
    }

    private void removerVideo() {
        model.ListaReproducao lista = jListListas.getSelectedValue();
        model.Video video = jListVideos.getSelectedValue();
        if (lista == null || video == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma lista e um vídeo.");
            return;
        }
        listaCtrl.removerVideo(lista.getId(), video.getId());
        carregarVideosDaLista();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jLabelMinhasListas = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabelListas = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListListas = new javax.swing.JList<>();
        jBtnNovaLista = new javax.swing.JButton();
        jBtnEditar = new javax.swing.JButton();
        jBtnExcluir = new javax.swing.JButton();
        jLabelVideos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListVideos = new javax.swing.JList<>();
        jBtnAdicionar = new javax.swing.JButton();
        jBtnRemover = new javax.swing.JButton();
        jBtnVoltar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FEItv - Favoritos");
        setBackground(new java.awt.Color(18, 18, 18));

        jPanel2.setBackground(new java.awt.Color(18, 18, 18));
        jPanel1.setBackground(new java.awt.Color(30, 30, 30));

        jLabelTitulo.setFont(new java.awt.Font("Arial Black", 1, 32));
        jLabelTitulo.setForeground(new java.awt.Color(220, 20, 20));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("FEItv - Mat e Let");

        jLabelMinhasListas.setFont(new java.awt.Font("Arial Black", 1, 20));
        jLabelMinhasListas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelMinhasListas.setText("Minhas Listas");

        jPanel3.setBackground(new java.awt.Color(30, 30, 30));

        jLabelListas.setFont(new java.awt.Font("Arial", 1, 14));
        jLabelListas.setForeground(new java.awt.Color(255, 255, 255));
        jLabelListas.setText("Listas de Reprodução");

        jListListas.setBackground(new java.awt.Color(51, 51, 51));
        jListListas.setForeground(new java.awt.Color(255, 255, 255));
        jListListas.setSelectionBackground(new java.awt.Color(229, 9, 20));
        jListListas.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setViewportView(jListListas);

        jBtnNovaLista.setBackground(new java.awt.Color(229, 9, 20));
        jBtnNovaLista.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnNovaLista.setForeground(new java.awt.Color(255, 255, 255));
        jBtnNovaLista.setText("Nova Lista");
        jBtnNovaLista.setBorderPainted(false);
        jBtnNovaLista.setFocusPainted(false);
        jBtnNovaLista.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBtnEditar.setBackground(new java.awt.Color(229, 9, 20));
        jBtnEditar.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnEditar.setForeground(new java.awt.Color(255, 255, 255));
        jBtnEditar.setText("Editar");
        jBtnEditar.setBorderPainted(false);
        jBtnEditar.setFocusPainted(false);
        jBtnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBtnExcluir.setBackground(new java.awt.Color(229, 9, 20));
        jBtnExcluir.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnExcluir.setForeground(new java.awt.Color(255, 255, 255));
        jBtnExcluir.setText("Excluir");
        jBtnExcluir.setBorderPainted(false);
        jBtnExcluir.setFocusPainted(false);
        jBtnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jLabelVideos.setFont(new java.awt.Font("Arial", 1, 14));
        jLabelVideos.setForeground(new java.awt.Color(255, 255, 255));
        jLabelVideos.setText("Vídeos da Lista");

        jListVideos.setBackground(new java.awt.Color(51, 51, 51));
        jListVideos.setForeground(new java.awt.Color(255, 255, 255));
        jListVideos.setSelectionBackground(new java.awt.Color(229, 9, 20));
        jListVideos.setSelectionForeground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setViewportView(jListVideos);

        jBtnAdicionar.setBackground(new java.awt.Color(229, 9, 20));
        jBtnAdicionar.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnAdicionar.setForeground(new java.awt.Color(255, 255, 255));
        jBtnAdicionar.setText("Adicionar Vídeo");
        jBtnAdicionar.setBorderPainted(false);
        jBtnAdicionar.setFocusPainted(false);
        jBtnAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBtnRemover.setBackground(new java.awt.Color(229, 9, 20));
        jBtnRemover.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnRemover.setForeground(new java.awt.Color(255, 255, 255));
        jBtnRemover.setText("Remover Vídeo");
        jBtnRemover.setBorderPainted(false);
        jBtnRemover.setFocusPainted(false);
        jBtnRemover.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jBtnVoltar.setBackground(new java.awt.Color(64, 64, 64));
        jBtnVoltar.setFont(new java.awt.Font("Arial", 1, 12));
        jBtnVoltar.setForeground(new java.awt.Color(255, 255, 255));
        jBtnVoltar.setText("Voltar");
        jBtnVoltar.setBorderPainted(false);
        jBtnVoltar.setFocusPainted(false);
        jBtnVoltar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelListas)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jBtnNovaLista, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnEditar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jBtnExcluir)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelVideos)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jBtnAdicionar)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jBtnRemover))
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelListas)
                    .addComponent(jLabelVideos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnNovaLista)
                    .addComponent(jBtnEditar)
                    .addComponent(jBtnExcluir)
                    .addComponent(jBtnAdicionar)
                    .addComponent(jBtnRemover))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(225, 225, 225)
                        .addComponent(jLabelMinhasListas))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jBtnVoltar))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMinhasListas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBtnVoltar)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
        pack();
    }// </editor-fold>

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new TelaFavoritos(null).setVisible(true));
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jBtnAdicionar;
    private javax.swing.JButton jBtnEditar;
    private javax.swing.JButton jBtnExcluir;
    private javax.swing.JButton jBtnNovaLista;
    private javax.swing.JButton jBtnRemover;
    private javax.swing.JButton jBtnVoltar;
    private javax.swing.JLabel jLabelListas;
    private javax.swing.JLabel jLabelMinhasListas;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JLabel jLabelVideos;
    private javax.swing.JList<model.ListaReproducao> jListListas;
    private javax.swing.JList<model.Video> jListVideos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration
}