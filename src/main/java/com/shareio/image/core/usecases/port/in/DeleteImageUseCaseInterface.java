package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageDoesNotExistException;

public interface DeleteImageUseCaseInterface {
    void deleteImage(String imageId) throws ImageDoesNotExistException;
}
