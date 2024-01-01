package twitterclonv2.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;
    private String content;
    @Column(name = "created_at",
            nullable = false,
            updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = UserEntity.class)
    @JsonBackReference
    private UserEntity author;
    @ManyToMany(mappedBy = "postsLiked")
    @ToString.Exclude
    private List<UserEntity> usersLikes;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}