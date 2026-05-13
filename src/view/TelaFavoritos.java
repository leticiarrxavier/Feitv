package view;

import controller.ListaController;
import controller.VideoController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaFavoritos extends JFrame {

    private Usuario usuarioLogado;
    private ListaController listaCtrl  = new ListaController();
    private VideoController  videoCtrl = new VideoController();

    private DefaultListModel<ListaReproducao> modelListas = new DefaultListModel<>();
    private JList<ListaReproducao>            listaListas = new JList<>(modelListas);

    private DefaultListModel<Video> modelVideos = new DefaultListModel<>();
    private JList<Video>            listaVideos = new JList<>(modelVideos);

    public TelaFavoritos(Usuario usuario) {
        this.usuarioLogado = usuario;

        setTitle("FEItv - Favoritos de " + usuario.getNome());
        setSize(700, 450);
        setLayout(new BorderLayout(10, 10));

        JPanel painelEsq = new JPanel(new BorderLayout());
        painelEsq.setBorder(BorderFactory.createTitledBorder("Minhas Listas"));
        painelEsq.add(new JScrollPane(listaListas), BorderLayout.CENTER);

        JPanel botoesLista = new JPanel(new GridLayout(1, 3, 5, 5));
        JButton btnCriar   = new JButton("Criar");
        JButton btnEditar  = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        botoesLista.add(btnCriar); botoesLista.add(btnEditar); botoesLista.add(btnExcluir);
        painelEsq.add(botoesLista, BorderLayout.SOUTH);
        add(painelEsq, BorderLayout.WEST);

        JPanel painelDir = new JPanel(new BorderLayout());
        painelDir.setBorder(BorderFactory.createTitledBorder("Vídeos da Lista"));
        painelDir.add(new JScrollPane(listaVideos), BorderLayout.CENTER);

        JPanel botoesVideo = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton btnAdicionar = new JButton("Adicionar Vídeo");
        JButton btnRemover   = new JButton("Remover Vídeo");
        botoesVideo.add(btnAdicionar); botoesVideo.add(btnRemover);
        painelDir.add(botoesVideo, BorderLayout.SOUTH);
        add(painelDir, BorderLayout.CENTER);

        btnCriar.addActionListener(e -> criarLista());
        btnEditar.addActionListener(e -> editarLista());
        btnExcluir.addActionListener(e -> excluirLista());
        btnAdicionar.addActionListener(e -> adicionarVideo());
        btnRemover.addActionListener(e -> removerVideo());

        listaListas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) carregarVideosDaLista();
        });

        carregarListas();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarListas() {
        modelListas.clear();
        for (ListaReproducao l : listaCtrl.listarPorUsuario(usuarioLogado.getId()))
            modelListas.addElement(l);
    }

    private void carregarVideosDaLista() {
        modelVideos.clear();
        ListaReproducao sel = listaListas.getSelectedValue();
        if (sel == null) return;
        for (Video v : listaCtrl.listarVideos(sel.getId()))
            modelVideos.addElement(v);
    }

    private void criarLista() {
        String nome = JOptionPane.showInputDialog(this, "Nome da nova lista:");
        if (nome == null || nome.trim().isEmpty()) return;
        listaCtrl.criar(nome.trim(), usuarioLogado.getId());
        carregarListas();
    }

    private void editarLista() {
        ListaReproducao sel = listaListas.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista."); return; }
        String novoNome = JOptionPane.showInputDialog(this, "Novo nome:", sel.getNome());
        if (novoNome == null || novoNome.trim().isEmpty()) return;
        listaCtrl.editar(sel.getId(), novoNome.trim());
        carregarListas();
    }

    private void excluirLista() {
        ListaReproducao sel = listaListas.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir lista '" + sel.getNome() + "'?");
        if (confirm == JOptionPane.YES_OPTION) {
            listaCtrl.excluir(sel.getId());
            carregarListas();
            modelVideos.clear();
        }
    }

    private void adicionarVideo() {
        ListaReproducao lista = listaListas.getSelectedValue();
        if (lista == null) { JOptionPane.showMessageDialog(this, "Selecione uma lista primeiro."); return; }
        ArrayList<Video> todos = videoCtrl.listarTodos();
        Video[] arr = todos.toArray(new Video[0]);
        Video escolhido = (Video) JOptionPane.showInputDialog(
            this, "Escolha o vídeo:", "Adicionar à Lista",
            JOptionPane.PLAIN_MESSAGE, null, arr, arr.length > 0 ? arr[0] : null
        );
        if (escolhido == null) return;
        listaCtrl.adicionarVideo(lista.getId(), escolhido.getId());
        carregarVideosDaLista();
    }

    private void removerVideo() {
        ListaReproducao lista = listaListas.getSelectedValue();
        Video video = listaVideos.getSelectedValue();
        if (lista == null || video == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma lista e um vídeo.");
            return;
        }
        listaCtrl.removerVideo(lista.getId(), video.getId());
        carregarVideosDaLista();
    }
}
