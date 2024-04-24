import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class AjoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        // Connexion à la base de données et ajout des informations
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            // Charger le driver JDBC
            Class.forName("com.mysql.jdbc.Driver");

            // Connexion à la base de données (à remplacer avec vos informations de connexion)
            String url = "jdbc:mysql://localhost:3306/compte";
            String utilisateur = "root";
            String motDePasse = "";
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);

            // Requête SQL pour insérer les données dans la base de données
            String sql = "INSERT INTO liste (login, password) VALUES (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, login);
            stmt.setString(2, password);
            int lignesModifiees = stmt.executeUpdate();

            // Redirection vers une page de confirmation après l'ajout
            response.sendRedirect("liste.jsp?ok");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs (par exemple, afficher un message d'erreur)
            response.getWriter().println("Erreur lors de l'ajout du compte : " + e.getMessage());
        } finally {
            // Fermeture des ressources
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



\\ permet d'exécuter la fonction d'ajout de identifient et mot de passe
