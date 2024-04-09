package shop.haui_megatech.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.base.ResponseUtil;
import shop.haui_megatech.base.RestApiV1;
import shop.haui_megatech.service.FileUploadService;

import java.io.IOException;

@RestApiV1
public class FileUploadController {
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("image") MultipartFile multipartFile) {
        String imageURL;
        try {
            imageURL = fileUploadService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseUtil.created(imageURL);
    }
}
