package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;

public interface CreateImagePNGUseCaseInterface {
    void createImagePNG(String imageId, byte[] imageBytes) throws ImageIOException, ImageExistsException;
}
