package com.shareio.image.core.usecases.service;

import com.shareio.image.core.usecases.port.in.GetImageUseCaseInterface;
import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageIOException;
import com.shareio.image.infrastructure.fsadapter.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class GetImageUseCaseService implements GetImageUseCaseInterface {
    private final ImageRepository imageRepository;

    @Override
    public byte[] getImage(String imageId) throws ImageIOException, ImageDoesNotExistException {
        try {
            return imageRepository.read(imageId);
        } catch (IOException e) {
            throw new ImageIOException(e.toString());
        }
    }
}
