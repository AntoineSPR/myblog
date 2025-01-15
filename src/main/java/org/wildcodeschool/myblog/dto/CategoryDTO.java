package org.wildcodeschool.myblog.dto;

import java.time.LocalDateTime;
import java.util.List;

import org.wildcodeschool.myblog.model.Article;

public class CategoryDTO {
    private Long id;
    private String name;
    private LocalDateTime updatedAt;
    private List<ArticleDTO> linkedArticles;

    // Getters et Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<ArticleDTO> getLinkedArticles() {
        return linkedArticles;
    }

    public void setLinkedArticles(List<ArticleDTO> linkedArticles) {
        this.linkedArticles = linkedArticles;
    }
}
