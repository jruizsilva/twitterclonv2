package twitterclonv2.domain.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Length(min = 2)
    @NotBlank(message = "comment can't be empty")
    private String content;

    @JsonBackReference
    @ManyToOne
    @ToString.Exclude
    private PostEntity post;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}