package twitterclonv2.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import twitterclonv2.common.util.Role;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity
@Table(name = "users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    private String username;
    private String password;
    private String description;
    private String name;
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonManagedReference
    @OneToMany(targetEntity = PostEntity.class,
               mappedBy = "author")
    @ToString.Exclude
    private List<PostEntity> postsCreated;
    @ManyToMany
    @JoinTable(
            name = "users_posts_likes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    @ToString.Exclude
    private List<PostEntity> postsLiked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities =
                role.getPermissions()
                    .stream()
                    .map(permissionEnum -> new SimpleGrantedAuthority(permissionEnum.name()))
                    .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + role.name()));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}