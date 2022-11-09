package ru.gb.hubr.entity.security;

import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.Set;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "AUTHORITY")
public class Authority implements GrantedAuthority {

    static final long serialVersionUID = -2324313849087772823L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission")
    private String permission;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.LAZY)
    private Set<AccountRole> roles;

    @Override
    public String getAuthority() {
        return this.permission;
    }

}
