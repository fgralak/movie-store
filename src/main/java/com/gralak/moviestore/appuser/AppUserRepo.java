package com.gralak.moviestore.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long>
{
    AppUser findByUsername(String username);

    void deleteByUsername(String username);
}
