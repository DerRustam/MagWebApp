package com.rustam.magbackend.controller;

import com.rustam.magbackend.enums.DataConflictType;
import com.rustam.magbackend.exception.DataServiceException;
import com.rustam.magbackend.service.data.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/data/img")
public class StaffImageController {

    private ImageService imageService;
    @Autowired
    public StaffImageController(ImageService imageService) {
        this.imageService = imageService;
    }



    @GetMapping(value = "/{fileName}")
    @ResponseBody
    public byte[] getFullImage(@PathVariable String fileName, HttpServletResponse response){
        return imageService.getImage(fileName);
    }

    @ExceptionHandler(DataServiceException.class)
    @ResponseBody
    ResponseEntity<String> dataErrorResult(DataServiceException ex){
        if (ex.getConflict() == DataConflictType.NOT_FOUND){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Requested Image Not Found");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
}
