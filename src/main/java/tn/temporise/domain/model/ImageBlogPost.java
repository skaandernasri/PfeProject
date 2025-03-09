package tn.temporise.domain.model;




public record ImageBlogPost(
        Long id,
        String url,
        BlogPost blogPost
) {}