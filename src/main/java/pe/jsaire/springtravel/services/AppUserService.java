package pe.jsaire.springtravel.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jsaire.springtravel.models.entity.documents.AppUserDocument;
import pe.jsaire.springtravel.repositories.AppUserRepository;
import pe.jsaire.springtravel.services.abstract_service.IUserService;
import pe.jsaire.springtravel.utils.exceptions.UserNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppUserService implements IUserService, UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public Map<String, Boolean> enabled(String username) {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        user.setEnabled(!user.isEnabled());

        var userSave = appUserRepository.save(user);
        return Collections.singletonMap(userSave.getUsername(), userSave.isEnabled());
    }

    @Override
    @Transactional
    public Map<String, List<String>> addRole(String username, String role) {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        user.getRole().getGrantedAuthorities().add(role);
        var userSave = appUserRepository.save(user);
        var authorities = userSave.getRole().getGrantedAuthorities();

        return Collections.singletonMap(userSave.getUsername(), authorities);
    }

    @Override
    @Transactional
    public Map<String, List<String>> removeRole(String username, String role) {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        user.getRole().getGrantedAuthorities().remove(role);
        var userSave = appUserRepository.save(user);
        var authorities = userSave.getRole().getGrantedAuthorities();
        return Collections.singletonMap(userSave.getUsername(), authorities);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        return mapUserToDatails(user);
    }

    private static UserDetails mapUserToDatails(AppUserDocument user) {
        Set<GrantedAuthority> grantedAuthorities = user.getRole()
                .getGrantedAuthorities()
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
        return new User(user.getUsername(), user.getPassword(), user.isEnabled()
                , true
                , true
                , true
                , grantedAuthorities);

    }


}
