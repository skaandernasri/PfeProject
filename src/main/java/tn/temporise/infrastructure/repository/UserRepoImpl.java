package tn.temporise.infrastructure.repository;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tn.temporise.domain.port.UserRepo;
import tn.temporise.infrastructure.persistence.entity.Role;
import tn.temporise.infrastructure.persistence.entity.Utilisateur;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class UserRepoImpl implements UserRepo {

    private final JdbcTemplate jdbcTemplate;

    public UserRepoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Utilisateur> findByEmail(String email) {
        String sql = "SELECT * FROM utilisateur WHERE email = ?";
        try {
            Utilisateur user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), email);
            if (user != null) {
                // Load roles for the user
                String findRolesSql = "SELECT role FROM utilisateur_role WHERE user_id = ?";
                Set<Role> roles = jdbcTemplate.queryForList(findRolesSql, String.class, user.getId())
                        .stream()
                        .map(Role::valueOf)
                        .collect(Collectors.toSet());
                user.setRoles(roles);
            }
            return Optional.ofNullable(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Set<Role> findRolesById(long id) {
        String sql = "SELECT r.* FROM role r " +
                "JOIN utilisateur_role ur ON r.id = ur.role_id " +
                "WHERE ur.utilisateur_id = ?";
        return new HashSet<>(jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Role.class), id));
    }

    @Override
    public void deleteByEmail(String email) {
        String sql = "DELETE FROM utilisateur WHERE email = ?";
        jdbcTemplate.update(sql, email);
    }

    @Override
    public Utilisateur save(Utilisateur user) {
        if (user.getId() == null) {
            // Step 1: Insert the user into the utilisateur table
            String insertUserSql = "INSERT INTO utilisateur (nom, email) VALUES (?, ?) RETURNING id";
            Long userId = jdbcTemplate.queryForObject(insertUserSql, Long.class, user.getNom(), user.getEmail());

            // Set the generated ID to the user object
            user.setId(userId);

            // Step 2: Insert roles into the utilisateur_role table
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                String insertRoleSql = "INSERT INTO utilisateur_role (user_id, role) VALUES (?, ?)";
                for (Role role : user.getRoles()) {
                    jdbcTemplate.update(insertRoleSql, userId, role.name()); // Convert enum to String
                }
            }

            return user;
        } else {
            // Step 1: Update the user in the utilisateur table
            String updateUserSql = "UPDATE utilisateur SET nom = ?, email = ? WHERE id = ?";
            jdbcTemplate.update(updateUserSql, user.getNom(), user.getEmail(), user.getId());

            // Step 2: Delete existing roles for the user
            String deleteRolesSql = "DELETE FROM utilisateur_role WHERE user_id = ?";
            jdbcTemplate.update(deleteRolesSql, user.getId());

            // Step 3: Insert updated roles into the utilisateur_role table
            if (user.getRoles() != null && !user.getRoles().isEmpty()) {
                String insertRoleSql = "INSERT INTO utilisateur_role (user_id, role) VALUES (?, ?)";
                for (Role role : user.getRoles()) {
                    jdbcTemplate.update(insertRoleSql, user.getId(), role.name()); // Convert enum to String
                }
            }

            return user;
        }
    }
}

