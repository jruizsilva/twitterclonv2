package twitterclonv2.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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

    @ManyToOne(targetEntity = UserEntity.class,
               cascade = CascadeType.ALL)
    /*@JsonBackReference*/
    private UserEntity author;

    @OneToMany(mappedBy = "post",
               orphanRemoval = true,
               cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<LikeEntity> likes;

    @OneToMany(mappedBy = "post")
    private List<BookmarkEntity> bookmarks;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}