package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.Article;
import tn.temporise.infrastructure.api.ArticlesApi;

import java.util.List;

public class BlogpostController implements ArticlesApi {
    @Override
    public ResponseEntity<Article> _createArticle(Article article) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteArticle(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<Article>> _getAllArticles() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Article> _getArticleById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Article> _updateArticle(Long id, Article article) throws Exception {
        return null;
    }
}
