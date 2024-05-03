package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageExistsException;

public interface CreateImageUseCaseInterface {
    String createImage(byte[] imageBytes) throws ImageExistsException;
    void createImage(String imageId, byte[] imageBytes) throws ImageExistsException;
}
