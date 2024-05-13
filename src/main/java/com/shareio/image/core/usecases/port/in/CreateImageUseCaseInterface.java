package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;

public interface CreateImageUseCaseInterface {
    String createImage(byte[] imageBytes) throws ImageIOException, ImageExistsException;

    void createImage(String imageId, byte[] imageBytes) throws ImageIOException, ImageExistsException;
}
