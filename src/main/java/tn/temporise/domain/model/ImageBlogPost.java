package tn.temporise.domain.model;

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
public class ImageBlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false,name = "url")
    private String url;

    @ManyToOne
    @JoinColumn(name = "blogpost_id", nullable = false)
    private BlogPost blogPost;
}