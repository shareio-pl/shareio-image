package com.shareio.image.core.usecases.service;

import com.shareio.image.core.usecases.port.in.DeleteImageUseCaseInterface;
import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageIOException;
import com.shareio.image.infrastructure.fsadapter.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class DeleteImageUseCaseService implements DeleteImageUseCaseInterface {
    private final ImageRepository imageRepository;

    @Override
    public void deleteImage(String imageId) throws ImageIOException, ImageDoesNotExistException {
        try {
            imageRepository.delete(imageId);
        } catch (IOException e) {
            throw new ImageIOException(e.toString());
        }
    }
}
