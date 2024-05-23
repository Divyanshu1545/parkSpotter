package com.divyanshu.parkSpotter.controllers;


import com.divyanshu.parkSpotter.models.ParkingSpace;
import com.divyanshu.parkSpotter.models.User;
import com.divyanshu.parkSpotter.services.ParkingSpaceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/parking-spaces")


public class ParkingSpaceController {
    private final ParkingSpaceService parkingSpaceService;

    @GetMapping("/within-radius")
    public List<ParkingSpace> getParkingSpacesWithinRadius(@RequestParam double latitude,
                                                           @RequestParam double longitude,
                                                           @RequestParam double radius) {
        return parkingSpaceService.findParkingSpacesWithinRadius(latitude, longitude, radius);
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('OWNER')")
    public ParkingSpace createParkingSpace(@RequestParam double latitude,
                                           @RequestParam double longitude,
                                           @RequestParam String address,
                                           @RequestParam(required = false) MultipartFile image,
                                           @AuthenticationPrincipal User user,
                                           HttpServletRequest request) throws IOException {
SecurityContext context =SecurityContextHolder.getContext();
        ParkingSpace parkingSpace = ParkingSpace.builder()
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .user((User)user)
                .build();

        return parkingSpaceService.saveParkingSpace(parkingSpace, image);
    }
//    @PreAuthorize("hasRole('OWNER')")
//    public ParkingSpace createParkingSpace(){
//
//
//    }
}

