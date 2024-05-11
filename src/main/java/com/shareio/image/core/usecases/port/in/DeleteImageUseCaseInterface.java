package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageIOException;

public interface DeleteImageUseCaseInterface {
    void deleteImage(String imageId) throws ImageIOException, ImageDoesNotExistException;
}
