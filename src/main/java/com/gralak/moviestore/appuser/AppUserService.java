package com.gralak.moviestore.appuser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserService implements UserDetailsService
{
    private final AppUserRepo appUserRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        AppUser appUser = appUserRepo.findByUsername(username);
        if (appUser == null)
        {
            throw new UsernameNotFoundException("User not found in the database");
        } else
        {
            log.info("User found in the database: {}", username);
        }
        Collection<SimpleGrantedAuthority> authorities = new HashSet<>();
        appUser.getRoles().forEach(appUserRole ->
                authorities.add(new SimpleGrantedAuthority(appUserRole.name())));

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);
    }

    public List<AppUser> getAllUsers()
    {
        return appUserRepo.findAll();
    }

    public AppUser getUser(String username)
    {
        return appUserRepo.findByUsername(username);
    }

    public AppUser saveUser(AppUser appUser)
    {
        if (appUserRepo.findByUsername(appUser.getUsername()) != null)
        {
            throw new IllegalStateException("Username is already taken");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    public AppUser updateUser(AppUser appUser)
    {
        if (appUserRepo.findByUsername(appUser.getUsername()) == null)
        {
            throw new UsernameNotFoundException("User not found in the database");
        }
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepo.save(appUser);
    }

    public void deleteByUsername(String username)
    {
        if (appUserRepo.findByUsername(username) == null)
        {
            throw new UsernameNotFoundException("User not found in the database");
        }
        appUserRepo.deleteByUsername(username);
    }

}
