package net.ent.etnc.rl_arenas.services.impl;

import net.ent.etnc.rl_arenas.models.entities.User;
import net.ent.etnc.rl_arenas.models.enums.Role;
import net.ent.etnc.rl_arenas.repositories.UserRepository;
import net.ent.etnc.rl_arenas.services.UserService;
import net.ent.etnc.rl_arenas.services.commons.AbstractService;
import net.ent.etnc.rl_arenas.services.commons.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl extends AbstractService<User, UserRepository> implements UserService {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        super(userRepository);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public User create(User entity) throws ServiceException {
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        return super.create(entity);
    }

    @Override
    @Transactional
    public User update(User entity) throws ServiceException {
        if (entity.getPassword() != null && !entity.getPassword().startsWith("$2")) {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        } else if (entity.getPassword() == null) {
            entity.setPassword(repository.findById(entity.getId())
                    .map(User::getPassword)
                    .orElse(null));
        }
        return super.update(entity);
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws ServiceException {
        User user = repository.findById(id)
                .orElseThrow(() ->
                        new ServiceException("Utilisateur introuvable")
                );
        if (user.getRole() == Role.ADMIN) {
            long nombreAdmins = repository.countByRole(Role.ADMIN);
            if (nombreAdmins <= 1) {
                throw new ServiceException("Impossible de supprimer le dernier administrateur.");
            }
        }

        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public User deactivate(Long id) throws ServiceException {
        User user = repository.findById(id)
                .orElseThrow(() -> new ServiceException("RG-U02", "Utilisateur introuvable"));
        if (user.getRole() == Role.ADMIN) {
            throw new ServiceException("RG-U05", "Impossible de désactiver un administrateur.");
        }
        user.setActive(false);
        return repository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User changeRole(Long id, Role role) throws ServiceException {
        User user = repository.findById(id)
                .orElseThrow(() -> new ServiceException("RG-U02", "Utilisateur introuvable"));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            User currentUser = auth.getPrincipal() instanceof User ? (User) auth.getPrincipal() : null;
            if (currentUser != null && currentUser.getId().equals(user.getId())) {
                throw new ServiceException("RG-U05", "Impossible de changer son propre rôle");
            }
        }
        user.setRole(role);
        return repository.save(user);
    }
}