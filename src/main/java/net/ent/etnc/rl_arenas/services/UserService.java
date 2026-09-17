package net.ent.etnc.rl_arenas.services;

import net.ent.etnc.rl_arenas.models.entities.User;
import net.ent.etnc.rl_arenas.models.enums.Role;
import net.ent.etnc.rl_arenas.services.commons.Service;
import net.ent.etnc.rl_arenas.services.commons.ServiceException;

import java.util.Optional;

public interface UserService extends Service<User, Long> {
    Optional<User> findByUsername(String username);

    User deactivate(Long id) throws ServiceException;

    User changeRole(Long id, Role role) throws ServiceException;
}