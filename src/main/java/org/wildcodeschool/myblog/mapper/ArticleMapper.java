package org.wildcodeschool.myblog.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.wildcodeschool.myblog.dto.ArticleCreateDTO;
import org.wildcodeschool.myblog.dto.ArticleDTO;
import org.wildcodeschool.myblog.dto.AuthorDTO;
import org.wildcodeschool.myblog.exception.ResourceNotFoundException;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.ArticleAuthor;
import org.wildcodeschool.myblog.model.Author;
import org.wildcodeschool.myblog.model.Category;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.repository.AuthorRepository;
import org.wildcodeschool.myblog.repository.CategoryRepository;

@Component
public class ArticleMapper {
    
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;

    public ArticleMapper(
            CategoryRepository categoryRepository,
            AuthorRepository authorRepository) {
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
    }

    public ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setUpdatedAt(article.getUpdatedAt());
        if (article.getCategory() != null) {
            articleDTO.setCategoryName(article.getCategory().getName());
        }
        if (article.getImages() != null) {
            articleDTO.setImageUrls(article.getImages().stream().map(Image::getUrl).collect(Collectors.toList()));
        }
        if (article.getArticleAuthors() != null) {
            articleDTO.setAuthors(article.getArticleAuthors().stream()
                    .filter(articleAuthor -> articleAuthor.getAuthor() != null) 
                    .map(articleAuthor -> {
                        AuthorDTO authorDTO = new AuthorDTO();
                        authorDTO.setId(articleAuthor.getAuthor().getId());
                        authorDTO.setFirstname(articleAuthor.getAuthor().getFirstname());
                        authorDTO.setLastname(articleAuthor.getAuthor().getLastname());
                        return authorDTO;
                    })
                    .collect(Collectors.toList()));
        }
        return articleDTO;
    }

    public Article convertToEntity(ArticleCreateDTO articleCreateDTO) {
        Article article = new Article();
        article.setTitle(articleCreateDTO.getTitle());
        article.setContent(articleCreateDTO.getContent());
        article.setCreatedAt(LocalDateTime.now());
        article.setUpdatedAt(LocalDateTime.now());

        Category category = categoryRepository.findById(articleCreateDTO.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "La catégorie avec l'id " + articleCreateDTO.getCategoryId() + " n'existe pas."));
        article.setCategory(category);
        if (articleCreateDTO.getImages() != null) {
            List<Image> images = articleCreateDTO.getImages().stream()
                .map(imageDTO -> {
                    Image image = new Image();
                    image.setUrl(imageDTO.getUrl());
                    return image;
                })
                .collect(Collectors.toList());
            article.setImages(images);
        }

        if (articleCreateDTO.getAuthors() != null) {
            List<ArticleAuthor> articleAuthors = articleCreateDTO.getAuthors().stream()
                .map(authorDTO -> {
                    Author author = authorRepository.findById(authorDTO.getAuthorId())
                            .orElseThrow(() -> new ResourceNotFoundException(
                                    "L'auteur avec l'id " + authorDTO.getAuthorId() + " n'existe pas."));
                    
                    ArticleAuthor articleAuthor = new ArticleAuthor();
                    articleAuthor.setAuthor(author);
                    articleAuthor.setArticle(article);
                    articleAuthor.setContribution(authorDTO.getContribution());
                    
                    return articleAuthor;
                })
                .collect(Collectors.toList());
            article.setArticleAuthors(articleAuthors);
        }

        return article;
    }
}