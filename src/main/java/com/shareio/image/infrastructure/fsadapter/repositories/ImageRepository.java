package com.shareio.image.infrastructure.fsadapter.repositories;

import com.shareio.image.Const;
import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageExistsException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Repository
public class ImageRepository {
    public void create(String id, byte[] image) throws IOException, ImageExistsException {
        Path imagePath = Path.of(Const.IMAGE_DIRECTORY, id + Const.IMAGE_EXTENSION);

        if (Files.exists(imagePath) || id.equals(Const.DEFAULT_UUID)) {
            throw new ImageExistsException(id);
        }

        Files.write(imagePath, image);
    }

    public byte[] read(String id) throws IOException, ImageDoesNotExistException {
        Path imagePath;
        if (id.equals(Const.DEFAULT_UUID)) {
            imagePath = Path.of("/" + id + Const.IMAGE_EXTENSION);
        } else {
            imagePath = Path.of(Const.IMAGE_DIRECTORY, id + Const.IMAGE_EXTENSION);
        }

        if (!Files.exists(imagePath)) {
            throw new ImageDoesNotExistException(id);
        }

        return Files.readAllBytes(imagePath);
    }

    public void delete(String id) throws IOException, ImageDoesNotExistException {
        Path imagePath = Path.of(Const.IMAGE_DIRECTORY, id + Const.IMAGE_EXTENSION);

        if (!Files.exists(imagePath)) {
            throw new ImageDoesNotExistException(id);
        }

        Files.delete(imagePath);
    }
}

