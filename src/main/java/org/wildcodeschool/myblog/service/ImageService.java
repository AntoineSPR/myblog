package org.wildcodeschool.myblog.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.wildcodeschool.myblog.dto.ImageDTO;
import org.wildcodeschool.myblog.mapper.ImageMapper;
import org.wildcodeschool.myblog.model.Image;
import org.wildcodeschool.myblog.repository.ImageRepository;

@Service
public class ImageService {
    
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public List<ImageDTO> getAllImages() {
        List<Image> images = imageRepository.findAll();
        return images.stream()
                .map(imageMapper::convertToDTO)
                .collect(Collectors.toList());
    }

    public ImageDTO getImageById(Long id) {
        return imageRepository.findById(id)
                .map(imageMapper::convertToDTO)
                .orElse(null);
    }

    public ImageDTO createImage(Image image) {
        Image savedImage = imageRepository.save(image);
        return imageMapper.convertToDTO(savedImage);
    }

    public ImageDTO updateImage(Long id, Image imageDetails) {
        return imageRepository.findById(id)
                .map(image -> {
                    image.setUrl(imageDetails.getUrl());
                    Image updatedImage = imageRepository.save(image);
                    return imageMapper.convertToDTO(updatedImage);
                })
                .orElse(null);
    }

    public boolean deleteImage(Long id) {
        return imageRepository.findById(id)
                .map(image -> {
                    imageRepository.delete(image);
                    return true;
                })
                .orElse(false);
    }
}