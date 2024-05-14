package shop.haui_megatech.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.annotation.RestApiV1;
import shop.haui_megatech.service.FileUploadService;
import shop.haui_megatech.utility.ResponseUtil;

import java.io.IOException;

@RestApiV1
@RequiredArgsConstructor
@Tag(name = "File upload")
public class FileUploadController {
    private final FileUploadService fileUploadService;

    @PostMapping("/upload-file")
    public ResponseEntity<?> uploadFile(@RequestParam("static/images") MultipartFile multipartFile) {
        String imageURL;
        try {
            imageURL = fileUploadService.uploadFile(multipartFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseUtil.created(imageURL);
    }
}
