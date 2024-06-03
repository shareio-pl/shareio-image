package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageConversionException;
import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;

public interface CreateImageUseCaseInterface {
    void createImagePNG(String imageId, byte[] imageBytes) throws ImageIOException, ImageExistsException;

    void createImageJPG(String imageId, byte[] imageBytes) throws ImageConversionException, ImageIOException, ImageExistsException;
}
