import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/controller")
public class controller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public controller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // Vérification du login et du mot de passe
        if (userExists(login, password)) {
            // Redirection vers la page suivante si les identifiants sont corrects
            response.sendRedirect("liste.jsp");
        } else {
            // Si les identifiants sont incorrects, afficher un message d'erreur
            response.sendRedirect("MDPLOG.jsp");
        }
    }

    private boolean userExists(String login, String password) {
        String dbUrl = "jdbc:mysql://localhost:3306/compte";
        String dbUsername = "root";
        String dbPassword = "";
        String query = "SELECT * FROM liste WHERE login = ? AND password = ?";
        
        try (Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
             PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // true if record exists, false otherwise
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}


// elle permet de verifieer sur la base de donné si l'identifient et le mot de pasee fonction 
//on met le fichier dans défault pakege dans java resource
