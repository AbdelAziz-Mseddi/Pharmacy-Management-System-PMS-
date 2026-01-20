package controle_interface;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connexion_sql.Connexion;

public class employeDAO {
	public Employe login(int idEmploye, String motDePasse)
	        throws SQLException {

	    String sql =
	        "SELECT idEmploye, nom, prenom, privilege " +
	        "FROM Employe WHERE idEmploye = ? AND mdp = ?";

	    try (Connection conn = Connexion.getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {

	        ps.setInt(1, idEmploye);
	        ps.setString(2, motDePasse);

	        ResultSet rs = ps.executeQuery();

	        if (rs.next()) {
	            Employe e = new Employe();
	            e.setIdEmploye(rs.getInt("idEmploye"));
	            e.setNom(rs.getString("nom"));
	            e.setPrenom(rs.getString("prenom"));
	            e.setPrivilege(rs.getString("privilege"));
	            return e; // LOGIN SUCCESS
	        }
	    }
	    return null; // LOGIN FAILED
	}

	public int creerEmploye(
	        String nom,
	        String prenom,
	        int tel,
	        String mail,
	        String motDePasseHash,
	        String privilege
	) throws SQLException {

	    String sql =
	        "INSERT INTO Employe " +
	        "(nom, prenom, tel, mail, mdp, privilege) " +
	        "VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection conn = Connexion.getConnection();
	         PreparedStatement ps =
	             conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

	        ps.setString(1, nom);
	        ps.setString(2, prenom);
	        ps.setInt(3, tel);
	        ps.setString(4, mail);
	        ps.setString(5, motDePasseHash);
	        ps.setString(6, privilege); // "user" or "admin"

	        ps.executeUpdate();

	        ResultSet keys = ps.getGeneratedKeys();
	        if (keys.next()) {
	            return keys.getInt(1); // idEmploye generated
	        }
	    }
	    return -1;
	}

	
	
}
