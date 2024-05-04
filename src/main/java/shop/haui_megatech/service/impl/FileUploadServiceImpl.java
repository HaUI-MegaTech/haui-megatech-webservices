package shop.haui_megatech.service.impl;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import shop.haui_megatech.service.FileUploadService;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final Cloudinary cloudinary;

    @Override
    public String uploadFile(MultipartFile multipartFile) throws IOException {
        return cloudinary.uploader()
                         .upload(multipartFile.getBytes(), Map.of("public_id", UUID.randomUUID()
                                                                                   .toString()))
                         .get("url")
                         .toString();
    }
}
