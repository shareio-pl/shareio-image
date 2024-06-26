package com.shareio.image.infrastructure.controller;

import com.shareio.image.Const;
import com.shareio.image.core.usecases.port.in.CreateImageUseCaseInterface;
import com.shareio.image.core.usecases.port.in.DeleteImageUseCaseInterface;
import com.shareio.image.core.usecases.port.in.GetImageUseCaseInterface;
import com.shareio.image.exceptions.ImageConversionException;
import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageExistsException;
import com.shareio.image.exceptions.ImageIOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

import static org.apache.tomcat.util.http.fileupload.FileUploadBase.MULTIPART_FORM_DATA;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final GetImageUseCaseInterface getImageUseCaseInterface;
    private final CreateImageUseCaseInterface createImageUseCaseInterface;
    private final DeleteImageUseCaseInterface deleteImageUseCaseInterface;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    private ResponseEntity<byte[]> getImage(@PathVariable(value = "id") String id) {
        byte[] image;
        try {
            image = getImageUseCaseInterface.getImage(id);
        } catch (ImageIOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ImageDoesNotExistException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> createImage(@PathVariable(value = "id") String id, @RequestPart("file") MultipartFile file) {
        if (file.getSize() > Const.MAX_FILE_SIZE) {
            return new ResponseEntity<>("Max file size is " + Const.MAX_FILE_SIZE / 1024 + " KB. This file is " + file.getSize() / 1024 + " KB", HttpStatus.PAYLOAD_TOO_LARGE);
        }

        if (Objects.equals(file.getContentType(), Const.MIME_IMAGE_PNG) || Objects.equals(file.getContentType(), Const.MIME_IMAGE_JPG)) {
            byte[] image;
            try {
                image = file.getBytes();
            } catch (IOException e) {
                return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
            }

            try {
                if (Objects.equals(file.getContentType(), Const.MIME_IMAGE_PNG)) {
                    createImageUseCaseInterface.createImagePNG(id, image);
                } else {
                    createImageUseCaseInterface.createImageJPG(id, image);
                }
            } catch (ImageConversionException e) {
                return new ResponseEntity<>("Internal server error (jpg -> png conversion)", HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ImageIOException e) {
                return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ImageExistsException e) {
                return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
            }
            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        return new ResponseEntity<>("Unsupported file type. This endpoint accepts only PNG or JPG images", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @Deprecated
    @RequestMapping(value = "/createPNG/{id}", method = RequestMethod.POST, consumes = MULTIPART_FORM_DATA, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> createImagePNG(@PathVariable(value = "id") String id, @RequestPart("file") MultipartFile file) {
        if (!Objects.equals(file.getContentType(), Const.MIME_IMAGE_PNG)) {
            return new ResponseEntity<>("Unsupported file type. This endpoint accepts only PNG images", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        if (file.getSize() > Const.MAX_FILE_SIZE) {
            return new ResponseEntity<>("Max file size is " + Const.MAX_FILE_SIZE / 1024 + " KB. This file is " + file.getSize() / 1024 + " KB", HttpStatus.PAYLOAD_TOO_LARGE);
        }

        byte[] image;
        try {
            image = file.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }

        try {
            createImageUseCaseInterface.createImagePNG(id, image);
        } catch (ImageIOException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ImageExistsException e) {
            return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @Deprecated
    @RequestMapping(value = "/createJPG/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> createImageJPG(@PathVariable(value = "id") String id, @RequestPart("file") MultipartFile file) {
        if (!Objects.equals(file.getContentType(), Const.MIME_IMAGE_JPG)) {
            return new ResponseEntity<>("Unsupported file type. This endpoint accepts only JPG images", HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }

        if (file.getSize() > Const.MAX_FILE_SIZE) {
            return new ResponseEntity<>("Max file size is " + Const.MAX_FILE_SIZE / 1024 + " KB. This file is " + file.getSize() / 1024 + " KB", HttpStatus.PAYLOAD_TOO_LARGE);
        }

        byte[] image;
        try {
            image = file.getBytes();
        } catch (IOException e) {
            return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
        }

        try {
            createImageUseCaseInterface.createImageJPG(id, image);
        } catch (ImageConversionException e) {
            return new ResponseEntity<>("Internal server error (jpg -> png conversion)", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ImageIOException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ImageExistsException e) {
            return new ResponseEntity<>("Not found", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> deleteImage(@PathVariable(value = "id") String id) {
        if (id.equals(Const.DEFAULT_UUID)) {
            return new ResponseEntity<>("Can not delete default image", HttpStatus.METHOD_NOT_ALLOWED);
        }

        try {
            deleteImageUseCaseInterface.deleteImage(id);
        } catch (ImageIOException e) {
            return new ResponseEntity<>("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (ImageDoesNotExistException e) {
            return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
