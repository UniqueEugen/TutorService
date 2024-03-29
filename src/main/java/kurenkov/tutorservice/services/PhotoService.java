package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.Photo;
import kurenkov.tutorservice.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository addressRepository) {
        this.photoRepository = addressRepository;
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    public Photo savePhoto(Photo photo) {
        return photoRepository.save(photo);
    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }
}

