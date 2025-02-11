package org.wildcodeschool.myblog.mapper;

import java.util.stream.Collectors;

import org.wildcodeschool.myblog.dto.ImageDTO;
import org.wildcodeschool.myblog.model.Article;
import org.wildcodeschool.myblog.model.Image;

public class ImageMapper {
    public ImageDTO convertToDTO(Image image) {
        ImageDTO imageDTO = new ImageDTO();
        imageDTO.setId(image.getId());
        imageDTO.setUrl(image.getUrl());
        if (image.getArticles() != null) {
            imageDTO.setArticleIds(image.getArticles().stream().map(Article::getId).collect(Collectors.toList()));
        }
        return imageDTO;
    }
}