package net.ent.etnc.rl_arenas.security.services;

import net.ent.etnc.rl_arenas.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implémentation de {@link UserDetailsService} qui charge un utilisateur par email.
 *
 * <p>Utilisée par {@link net.ent.etnc.certificationcenter.security.WebSecurityConfig WebSecurityConfig}
 * pour l'authentification par formulaire et par
 * {@link net.ent.etnc.certificationcenter.security.jwt.AuthJwtFilter AuthJwtFilter}
 * pour la résolution de l'utilisateur depuis un token JWT valide.
 * L'entité {@code User} implémente {@link UserDetails} directement — aucune conversion nécessaire.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String pseudo) throws UsernameNotFoundException {
        return userRepository.findByUsername(pseudo)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + pseudo));
    }
}
