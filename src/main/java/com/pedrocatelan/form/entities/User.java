package com.pedrocatelan.form.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "full_name")
    private String fullname;

    @Column
    private String password;

    @Column(name = "account_non_expired")
    private Boolean accountNonExpired;

    @Column(name = "account_non_locked")
    private Boolean accountNonLocked;

    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired;

    @Column
    private Boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER) // Tipo eager (preguiçoso), carrega tudo de uma vez
    @JoinTable(name = "user_permission",
            // Deve ser utilizado para mapear as colunas da tabela do meio de um relacionamento ManyToMany
            joinColumns = { @JoinColumn(name = "id_user")}, // Indica a coluna que terá com relação com o ID dessa entidade.
            inverseJoinColumns = {@JoinColumn(name = "id_permission")} // Indica a coluna que fará o mapeamento inverso, de permissão para usuário

    )
    private List<Permission> permissions;


    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();
        for(Permission permission: permissions) {
            roles.add(permission.getDescription());
        }
        return roles;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }
}
