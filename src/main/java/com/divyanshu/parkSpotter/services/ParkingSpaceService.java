package com.divyanshu.parkSpotter.services;



import com.divyanshu.parkSpotter.models.ParkingSpace;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import com.divyanshu.ParkSpotter.repositories.ParkingSpaceRepository;
@Service
@RequiredArgsConstructor
@Slf4j
public class ParkingSpaceService {
    private final ParkingSpaceRepository parkingSpaceRepository;
    private Authentication authentication;

    public List<ParkingSpace> findParkingSpacesWithinRadius(double latitude, double longitude, double radius) {
        return parkingSpaceRepository.findParkingSpacesWithinRadius(latitude, longitude, radius);
    }

    public ParkingSpace saveParkingSpace(ParkingSpace parkingSpace, MultipartFile image) throws IOException {


        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            parkingSpace.setImageUrl(imageUrl);
        }
        return parkingSpaceRepository.save(parkingSpace);
    }

    private String saveImage(MultipartFile image) throws IOException {
        String imageFolder = "images/"; // or any directory path you want
        String imageFileName = UUID.randomUUID().toString() + "-" + image.getOriginalFilename();
        Path imagePath = Paths.get(imageFolder + imageFileName);
        Files.createDirectories(imagePath.getParent());
        Files.write(imagePath, image.getBytes());
        return imagePath.toString();
    }
}

