import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateExample {

    private static final String URL = "jdbc:mysql://localhost:3306/estudiantes2024a";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer la conexión
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Crear la consulta de actualización
            String sql = "UPDATE estudiantes SET b1 = ? WHERE cedula = ?";
            pstmt = conn.prepareStatement(sql);

            // Establecer los valores para los parámetros
            pstmt.setString(1, "9");
            pstmt.setInt(2, 1); // Suponiendo que estamos actualizando el registro con id = 1

            // Ejecutar la actualización
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Filas afectadas: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar los recursos
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}