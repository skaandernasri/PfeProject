package tn.temporise.infrastructure.controller;

import org.springframework.http.ResponseEntity;
import tn.temporise.domain.model.ArticleRequest;
import tn.temporise.domain.model.ArticleResponse;

import tn.temporise.infrastructure.api.ArticlesApi;

import java.util.List;

public class BlogpostController implements ArticlesApi {
    @Override
    public ResponseEntity<ArticleResponse> _createArticle(ArticleRequest article) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> _deleteArticle(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<List<ArticleResponse>> _getAllArticles() throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<ArticleResponse> _getArticleById(Long id) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<ArticleResponse> _updateArticle(Long id, ArticleRequest article) throws Exception {
        return null;
    }
}