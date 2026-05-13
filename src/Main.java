import view.TelaLogin;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Garante que o Swing rode na EDT (boa prática)
        SwingUtilities.invokeLater(() -> new TelaLogin());
    }
}
