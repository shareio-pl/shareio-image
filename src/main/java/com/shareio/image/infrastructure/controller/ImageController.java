package com.shareio.image.infrastructure.controller;

import com.shareio.image.core.usecases.port.in.DeleteImageUseCaseInterface;
import com.shareio.image.core.usecases.port.in.GetImageUseCaseInterface;
import com.shareio.image.exceptions.ImageDoesNotExistException;
import com.shareio.image.exceptions.ImageIOException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/image")
public class ImageController {
    private final GetImageUseCaseInterface getImageUseCaseInterface;
    private final DeleteImageUseCaseInterface deleteImageUseCaseInterface;

    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    private ResponseEntity<byte[]> getImage(@PathVariable(value = "id") String id) {
        // TODO: may change type of id from String to UUID
        // TODO: id may require verification / sanitization
        byte[] image;
        try {
            image = getImageUseCaseInterface.getImage(id);
        } catch (ImageIOException e) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
        } catch (ImageDoesNotExistException e) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(image, HttpStatusCode.valueOf(200));
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> createImage() {
        return new ResponseEntity<>("Not implemented", HttpStatusCode.valueOf(501));
    }

    @RequestMapping(value = "/create/{id}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> createImage(@PathVariable(value = "id") String id) {
        return new ResponseEntity<>("Not implemented", HttpStatusCode.valueOf(501));
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    private ResponseEntity<String> deleteImage(@PathVariable(value = "id") String id) {
        try {
            deleteImageUseCaseInterface.deleteImage(id);
        } catch (ImageIOException e) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(500));
        } catch (ImageDoesNotExistException e) {
            return new ResponseEntity<>(null, HttpStatusCode.valueOf(404));
        }
        return new ResponseEntity<>(id, HttpStatusCode.valueOf(200));
    }
}
