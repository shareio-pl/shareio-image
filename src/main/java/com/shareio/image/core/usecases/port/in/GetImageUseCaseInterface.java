package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageIOException;

public interface GetImageUseCaseInterface {
    byte[] getImage(String imageId) throws ImageIOException, ImageDoesNotExistException;
}
