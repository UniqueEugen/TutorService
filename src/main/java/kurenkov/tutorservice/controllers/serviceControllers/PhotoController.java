package kurenkov.tutorservice.controllers.serviceControllers;


import kurenkov.tutorservice.entities.Photo;
import kurenkov.tutorservice.services.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/photo")
public class PhotoController {
    @Autowired
    PhotoService photoService;

    @GetMapping("/getImage/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Photo photo = photoService.getPhotoById(id);

        if (photo == null) {
            return ResponseEntity.notFound().build();
        }

        byte[] imageData = photo.getContent();
        String filename = photo.getFilename();

        String fileExtension = getFileExtension(filename);
        MediaType mediaType = resolveMediaType(fileExtension);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageData);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

    private MediaType resolveMediaType(String fileExtension) {
        switch (fileExtension) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "jpeg":
            case "jpg":
                return MediaType.IMAGE_JPEG;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
