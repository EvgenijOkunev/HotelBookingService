package com.geekhub.service;

import com.geekhub.model.Hotel;
import com.geekhub.model.Photo;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

@Service
@Transactional
@SuppressWarnings("unchecked")
public class PhotoService {

    @Autowired
    private SessionFactory sessionFactory;

    public void createPhoto(String fileName, Hotel hotel, Boolean mainPhoto) {
        Session session = sessionFactory.getCurrentSession();
        Photo photo = new Photo();
        photo.setFileName(fileName);
        photo.setHotel(hotel);
        photo.setMainPhoto(mainPhoto);
        session.save(photo);
    }

    public void deletePhoto(Integer photoId) {
        Session session = sessionFactory.getCurrentSession();
        Photo photo = (Photo) session.get(Photo.class, photoId);
        session.delete(photo);
    }

    public List<Photo> getPhotos(Hotel hotel) {
        Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Photo.class)
                .add(Restrictions.eq("hotel", hotel))
                .list();
    }

    public List<String> upload(Collection<Part> parts, String savePath) throws IOException {

        List<String> uploadedFilesNames = new ArrayList<>();
        Random random = new Random();

        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }

        for (Part part : parts) {
            if (part.getSize() > 0) {
                String fileName = extractFileName(part);
                File uploadedFile;
                do {
                    String fileSavePath = savePath + File.separator + random.nextInt() + fileName;
                    uploadedFile = new File(fileSavePath);
                } while (uploadedFile.exists());
                if (uploadedFile.createNewFile()) {
                    part.write(uploadedFile.getAbsolutePath());
                    uploadedFilesNames.add(uploadedFile.getName());
                }
            }
        }

        return uploadedFilesNames;

    }

    /**
     * Extracts file name from HTTP header content-disposition
     */
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }


}
