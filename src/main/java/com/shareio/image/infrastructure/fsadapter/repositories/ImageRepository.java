package com.shareio.image.infrastructure.fsadapter.repositories;

import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageExistsException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class ImageRepository {
    private final String imageDirectory = "/images/";
    private final String imageExtension = ".png";

    public void create(String id, byte[] image) throws IOException, ImageExistsException {
        Path imagePath = Path.of(imageDirectory, id + imageExtension);

        if (Files.exists(imagePath)) {
            throw new ImageExistsException(id);
        }

        Files.write(imagePath, image);
    }

    public byte[] read(String id) throws IOException, ImageDoesNotExistException {
        Path imagePath = Path.of(imageDirectory, id + imageExtension);

        if (!Files.exists(imagePath)) {
            throw new ImageDoesNotExistException(id);
        }

        return Files.readAllBytes(imagePath);
    }

    public void delete(String id) throws IOException, ImageDoesNotExistException {
        Path imagePath = Path.of(imageDirectory, id + imageExtension);

        if (!Files.exists(imagePath)) {
            throw new ImageDoesNotExistException(id);
        }

        Files.delete(imagePath);
    }
}

