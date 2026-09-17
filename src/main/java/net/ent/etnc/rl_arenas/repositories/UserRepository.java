package net.ent.etnc.rl_arenas.repositories;

import net.ent.etnc.rl_arenas.models.entities.User;
import net.ent.etnc.rl_arenas.models.enums.Role;
import net.ent.etnc.rl_arenas.repositories.commons.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User> {

    @Query("SELECT u FROM User u WHERE u.pseudo = :username")
    Optional<User> findByUsername(String username);
    long countByRole(Role role);
}