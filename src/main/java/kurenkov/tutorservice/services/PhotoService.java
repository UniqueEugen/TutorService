package kurenkov.tutorservice.services;

import kurenkov.tutorservice.entities.Photo;
import kurenkov.tutorservice.entities.Tutor;
import kurenkov.tutorservice.repositories.PhotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final TutorService tutorService;

    public PhotoService(
            PhotoRepository addressRepository,
            TutorService tutorService) {
        this.tutorService = tutorService;
        this.photoRepository = addressRepository;
    }

    public List<Photo> getAllPhotos() {
        return photoRepository.findAll();
    }

    public Photo getPhotoById(Long id) {
        return photoRepository.findById(id).orElse(null);
    }

    public void savePhoto(MultipartFile image, Tutor tutor)  {
        Photo photo = new Photo();
        try{
            Date currentDate = new Date();
            photo.setFilename("profilePhoto" + currentDate.toString().replaceAll("\\D+", "")+ image.getOriginalFilename());
            photo.setContent(image.getBytes());
            if(tutor.getPhoto()!=null){
                photo.setId(tutor.getPhoto().getId());
            }
            tutor.setPhoto(photo);
            tutorService.saveTutor(tutor);
        }catch (IOException e){

        }

    }

    public void deletePhoto(Long id) {
        photoRepository.deleteById(id);
    }
}

