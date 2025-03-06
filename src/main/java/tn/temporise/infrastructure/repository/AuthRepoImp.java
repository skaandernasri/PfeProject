package tn.temporise.infrastructure.repository;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.AuthRepo;
import tn.temporise.infrastructure.persistence.entity.Authentification;
import tn.temporise.infrastructure.persistence.entity.TypeAuthentification;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.util.Optional;
@Repository
public class AuthRepoImp implements AuthRepo {
    private final JdbcTemplate jdbcTemplate;

    public AuthRepoImp(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Authentification> findByUserEmail(String email) {
        String sql = "SELECT a.*, u.id as user_id, u.email as user_email FROM authentification a " +
                "JOIN utilisateur u ON a.user_id = u.id " +
                "WHERE u.email = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Authentification auth = new Authentification();
            auth.setId(rs.getLong("id"));
            auth.setPassword(rs.getString("password"));
            auth.setProviderId(rs.getString("provider_id"));
            auth.setToken(rs.getString("token"));
            auth.setType(TypeAuthentification.valueOf(rs.getString("type")));

            // Create and set the Utilisateur (user) object
            Utilisateur user = new Utilisateur();
            user.setId(rs.getLong("user_id"));
            user.setEmail(rs.getString("user_email"));
            // Set other fields of Utilisateur as needed

            auth.setUser(user); // Set the user in Authentification
            return auth;
        }, email).stream().findFirst();
    }

    @Override
    public Optional<Authentification> findByUserEmailAndProviderId(String email, String providerId) {
        String sql = "SELECT a.*, u.id as user_id, u.email as user_email FROM authentification a " +
                "JOIN utilisateur u ON a.user_id = u.id " +
                "WHERE u.email = ? AND a.provider_id = ?";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Authentification auth = new Authentification();
            auth.setId(rs.getLong("id"));
            auth.setPassword(rs.getString("password"));
            auth.setProviderId(rs.getString("provider_id"));
            auth.setToken(rs.getString("token"));
            auth.setType(TypeAuthentification.valueOf(rs.getString("type")));

            // Create and set the Utilisateur (user) object
            Utilisateur user = new Utilisateur();
            user.setId(rs.getLong("user_id"));
            user.setEmail(rs.getString("user_email"));
            // Set other fields of Utilisateur as needed

            auth.setUser(user); // Set the user in Authentification
            return auth;
        }, email, providerId).stream().findFirst();
    }

    @Override
    public Authentification save(Authentification authentification) {
        if (authentification.getId() == null) {
            // Insert new authentication record
            String sql = "INSERT INTO authentification (password, provider_id, type, token, user_id) VALUES (?, ?, ?, ?, ?)";
            jdbcTemplate.update(
                    sql,
                    authentification.getPassword(),
                    authentification.getProviderId(),
                    authentification.getType().name(),
                    authentification.getToken(),
                    authentification.getUser().getId()
            );return authentification;
        } else {
            // Update existing authentication record
            String sql = "UPDATE authentification SET password = ?, provider_id = ?, type = ?, token = ?, user_id = ? WHERE id = ?";
            jdbcTemplate.update(
                    sql,
                    authentification.getPassword(),
                    authentification.getProviderId(),
                    authentification.getType().name(),
                    authentification.getToken(),
                    authentification.getUser().getId(),
                    authentification.getId()
            );return authentification;
        }
    }
}
