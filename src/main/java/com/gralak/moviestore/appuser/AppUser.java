package com.gralak.moviestore.appuser;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class AppUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @ElementCollection(targetClass = AppUserRole.class)
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private List<AppUserRole> roles;

    public AppUser(String username, String password, List<AppUserRole> roles)
    {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}