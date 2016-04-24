package com.geekhub.controller;

import com.geekhub.model.Hotel;
import com.geekhub.service.HotelService;
import com.geekhub.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Controller
@RequestMapping("/photos")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, maxFileSize = 1024 * 1024 * 10, maxRequestSize = 1024 * 1024 * 50)
public class PhotoController {

    private static final String SAVE_DIR = "uploadFiles";

    @Autowired
    private PhotoService photoService;
    @Autowired
    private HotelService hotelService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public void uploadPhotos(@RequestParam(value = "hotelId", required = true) Integer hotelId,
                             @RequestParam(value = "mainPhoto", required = true) Boolean mainPhoto,
                             HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException {
        String savePath = request.getServletContext().getRealPath("") + SAVE_DIR;
        Collection<Part> parts = request.getParts();
        List<String> uploadedFilesNames = photoService.upload(parts, savePath);
        Hotel hotel = hotelService.getHotelById(hotelId);
        uploadedFilesNames.forEach(fileName -> photoService.createPhoto(fileName, hotel, mainPhoto));
//        response.sendRedirect("/photos/edit?hotelId=" + hotelId);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String hotelPhotosEdit(@RequestParam(value = "hotelId", required = true) Integer hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        model.addAttribute("hotel", hotel);
        model.addAttribute("mainPhoto", photoService.getMainPhoto(hotel));
        model.addAttribute("photos", photoService.getPhotos(hotel));
        return "hotelPhotosEdit";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public String hotelPhotosDelete(@RequestParam(value = "photoId", required = true) Integer photoId) {
        photoService.deletePhoto(photoId);
        return "";
    }

}
