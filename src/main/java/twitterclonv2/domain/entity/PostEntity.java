package twitterclonv2.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JsonManagedReference
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "posts_users_likes",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonManagedReference
    @ToString.Exclude
    private List<UserEntity> likedByUsers;

    @ManyToMany(mappedBy = "postsSaved")
    @JsonManagedReference
    @ToString.Exclude
    private List<UserEntity> savedByUsers;

    @OneToMany(mappedBy = "post")
    @JsonManagedReference
    @ToString.Exclude
    private List<CommentEntity> comments;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}