import javax.swing.*;

public class MainBuscador {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Buscador buscador = new Buscador();
            buscador.mostrarInterfaz();
        });
    }
}
