package com.shareio.image.core.usecases.service;

import com.shareio.image.core.usecases.port.in.CreateImageUseCaseInterface;
import com.shareio.image.exceptions.ImageConversionException;
import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;
import com.shareio.image.infrastructure.fsadapter.repositories.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@AllArgsConstructor
public class CreateImageUseCaseService implements CreateImageUseCaseInterface {
    private final ImageRepository imageRepository;

    @Override
    public void createImagePNG(String imageId, byte[] imageBytes) throws ImageIOException, ImageExistsException {
        try {
            imageRepository.create(imageId, imageBytes);
        } catch (IOException e) {
            throw new ImageIOException(e.toString());
        }
    }

    @Override
    public void createImageJPG(String imageId, byte[] imageBytes) throws ImageConversionException, ImageIOException, ImageExistsException {
        try {
            BufferedImage jpgImage = ImageIO.read(new ByteArrayInputStream(imageBytes));
            ByteArrayOutputStream pngStream = new ByteArrayOutputStream();
            try {
                boolean result = ImageIO.write(jpgImage, "png", pngStream);
                if (!result) {
                    throw new IOException("result is false");
                }
            } catch (IllegalArgumentException | IOException e) {
                throw new ImageConversionException(e.toString());
            }
            imageRepository.create(imageId, pngStream.toByteArray());
        } catch (IOException e) {
            throw new ImageIOException(e.toString());
        }
    }
}
