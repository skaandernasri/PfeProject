package tn.temporise.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "imageblogpost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageBlogPostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", nullable = false)
    private BlogPostEntity blogPost;
}