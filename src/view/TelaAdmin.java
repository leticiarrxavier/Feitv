package view;

import controller.VideoController;
import controller.UsuarioController;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TelaAdmin extends JFrame {

    private final VideoController   videoCtrl   = new VideoController();
    private final UsuarioController usuarioCtrl = new UsuarioController();

    private final DefaultListModel<Video> modelVideos = new DefaultListModel<>();
    private final JList<Video>            listaVideos = new JList<>(modelVideos);

    public TelaAdmin() {
        setTitle("FEItv - Painel do Administrador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 480);
        setLayout(new BorderLayout(10, 10));

        listaVideos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listaVideos), BorderLayout.CENTER);

        JPanel painelAcoes = new JPanel(new GridLayout(0, 1, 5, 8));
        painelAcoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JButton btnCadastrarVideo = new JButton("+ Cadastrar Vídeo");
        JButton btnExcluirVideo   = new JButton("✖ Excluir Vídeo");
        JButton btnUsuarios       = new JButton("👤 Consultar Usuários");
        JButton btnEstatisticas   = new JButton("📊 Estatísticas");
        JButton btnSair           = new JButton("Sair");
        painelAcoes.add(btnCadastrarVideo);
        painelAcoes.add(btnExcluirVideo);
        painelAcoes.add(new JSeparator());
        painelAcoes.add(btnUsuarios);
        painelAcoes.add(btnEstatisticas);
        painelAcoes.add(Box.createVerticalStrut(30));
        painelAcoes.add(btnSair);
        add(painelAcoes, BorderLayout.EAST);

        JLabel lblTitulo = new JLabel("  Gerenciar Vídeos", SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 5));
        add(lblTitulo, BorderLayout.NORTH);

        btnCadastrarVideo.addActionListener(e -> cadastrarVideo());
        btnExcluirVideo.addActionListener(e -> excluirVideo());
        btnUsuarios.addActionListener(e -> consultarUsuarios());
        btnEstatisticas.addActionListener(e -> verEstatisticas());
        btnSair.addActionListener(e -> { dispose(); new TelaLogin(); });

        carregarVideos();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void carregarVideos() {
        modelVideos.clear();
        for (Video v : videoCtrl.listarTodos()) modelVideos.addElement(v);
    }

    private void cadastrarVideo() {
        JTextField fTitulo = new JTextField();
        JComboBox<String> fTipo = new JComboBox<>(new String[]{"Filme", "Serie"});
        JTextField fGenero = new JTextField();
        JTextField fAno    = new JTextField();
        JTextArea  fDesc   = new JTextArea(3, 20);
        Object[] campos = { "Título:", fTitulo, "Tipo:", fTipo, "Gênero:", fGenero, "Ano:", fAno, "Descrição:", new JScrollPane(fDesc) };
        int resultado = JOptionPane.showConfirmDialog(this, campos, "Cadastrar Vídeo", JOptionPane.OK_CANCEL_OPTION);
        if (resultado != JOptionPane.OK_OPTION) return;
        try {
            String titulo = fTitulo.getText().trim();
            String tipo   = (String) fTipo.getSelectedItem();
            String genero = fGenero.getText().trim();
            int    ano    = Integer.parseInt(fAno.getText().trim());
            String desc   = fDesc.getText().trim();
            if (titulo.isEmpty()) { JOptionPane.showMessageDialog(this, "Título obrigatório."); return; }
            boolean ok = videoCtrl.cadastrar(titulo, tipo, genero, ano, desc);
            if (ok) { JOptionPane.showMessageDialog(this, "Vídeo cadastrado com sucesso!"); carregarVideos(); }
            else JOptionPane.showMessageDialog(this, "Erro ao cadastrar.", "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ano inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirVideo() {
        Video sel = listaVideos.getSelectedValue();
        if (sel == null) { JOptionPane.showMessageDialog(this, "Selecione um vídeo."); return; }
        int confirm = JOptionPane.showConfirmDialog(this, "Excluir \"" + sel.getTitulo() + "\"?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) { videoCtrl.excluir(sel.getId()); carregarVideos(); }
    }

    private void consultarUsuarios() {
        ArrayList<Usuario> usuarios = usuarioCtrl.listarTodos();
        DefaultListModel<String> m = new DefaultListModel<>();
        for (Usuario u : usuarios) m.addElement(u.getId() + " | " + u.getNome() + " | " + u.getEmail());
        JList<String> lista = new JList<>(m);
        JOptionPane.showMessageDialog(this, new JScrollPane(lista), "Usuários Cadastrados (" + usuarios.size() + ")", JOptionPane.INFORMATION_MESSAGE);
    }

    private void verEstatisticas() {
        int totalVideos   = videoCtrl.contarVideos();
        int totalUsuarios = usuarioCtrl.contarUsuarios();
        StringBuilder sb = new StringBuilder();
        sb.append("=== ESTATÍSTICAS DO SISTEMA ===\n\n");
        sb.append("Total de vídeos:   ").append(totalVideos).append("\n");
        sb.append("Total de usuários: ").append(totalUsuarios).append("\n\n");
        sb.append("=== TOP 5 VÍDEOS MAIS CURTIDOS ===\n\n");
        int pos = 1;
        for (Video v : videoCtrl.top5())
            sb.append(pos++).append("º  ").append(v.getTitulo()).append(" [").append(v.getTipo()).append("] - ").append(v.getCurtidas()).append(" curtida(s)\n");
        JTextArea ta = new JTextArea(sb.toString());
        ta.setEditable(false);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Estatísticas", JOptionPane.INFORMATION_MESSAGE);
    }
}
