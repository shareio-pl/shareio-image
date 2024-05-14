package com.shareio.image.core.usecases.service;

import com.shareio.image.core.usecases.port.in.CreateImagePNGUseCaseInterface;
import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;
import com.shareio.image.infrastructure.fsadapter.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class CreateImagePNGUseCaseService implements CreateImagePNGUseCaseInterface {
    private final ImageRepository imageRepository;

    @Override
    public void createImagePNG(String imageId, byte[] imageBytes) throws ImageIOException, ImageExistsException {
        try {
            imageRepository.create(imageId, imageBytes);
        } catch (IOException e) {
            throw new ImageIOException(e.toString());
        }
    }
}
