import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Buscador extends JFrame {
    private JLabel resultadoLabel;
    private JButton consultarButton;
    private JTextField consultaField;
    private String url = "jdbc:mysql://localhost:3306/estudiantes2024a";
    private String user = "root";
    private String password = "123456";

    public Buscador() {
        super("Buscador de Estudiantes");

        resultadoLabel = new JLabel("Resultado:");
        consultarButton = new JButton("Consultar");
        consultaField = new JTextField(20);

        consultarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarEstudiante();
            }
        });

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("Ingrese la cédula o nombre del estudiante:"));
        panel.add(consultaField);
        panel.add(consultarButton);
        panel.add(resultadoLabel);

        getContentPane().add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);
    }

    public void mostrarInterfaz() {
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private void buscarEstudiante() {
        String consulta = consultaField.getText().trim();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String query = "SELECT cedula,nombre, b1, b2, (b1 + b2) / 2 AS promedio FROM estudiantes WHERE cedula = ? OR nombre LIKE ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, consulta);
            preparedStatement.setString(2, "%" + consulta + "%");

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String cedulaEstudiante = resultSet.getString("cedula");
                String nombreEstudiante = resultSet.getString("nombre");
                double notaB1 = resultSet.getDouble("b1");
                double notaB2 = resultSet.getDouble("b2");
                double promedio = resultSet.getDouble("promedio");

                String mensaje = "Nombre del estudiante: " + nombreEstudiante + "\n";
                mensaje += "Cédula: " + cedulaEstudiante + "\n";
                mensaje += "Nota b1: " + notaB1 + "\n";
                mensaje += "Nota b2: " + notaB2 + "\n";
                mensaje += "Promedio: " + promedio + "\n\n";

                if (promedio >= 28) {
                    mensaje += "El estudiante aprueba el semestre.";
                } else {
                    double notaNecesaria = 28 - promedio;
                    mensaje += "El estudiante reprueba.\n";
                    mensaje += "Faltan " + String.format("%.2f", notaNecesaria) + " puntos para llegar a 28 y aprobar el semestre.";
                }

                resultadoLabel.setText(mensaje);
            } else {
                resultadoLabel.setText("No se encontró ningún estudiante con la cédula o nombre: " + consulta);
            }

        } catch (SQLException e) {
            resultadoLabel.setText("Error al ejecutar la consulta: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Buscador buscador = new Buscador();
            buscador.mostrarInterfaz();
        });
    }
}
