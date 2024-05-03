package com.shareio.image.core.usecases.port.in;

import com.shareio.image.exceptions.ImageDoesNotExistException;

import java.io.IOException;

public interface GetImageUseCaseInterface {
    byte[] getImage(String imageId) throws IOException, ImageDoesNotExistException;
}
