package com.divyanshu.parkSpotter.controllers;


import com.divyanshu.parkSpotter.models.ParkingSpace;
import com.divyanshu.parkSpotter.services.ParkingSpaceService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
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
    @Parameters({@Parameter(in = ParameterIn.HEADER,name = "Authorization",schema = @Schema(type = "string"))})
    public ParkingSpace createParkingSpace(@RequestParam double latitude,
                                           @RequestParam double longitude,
                                           @RequestParam String address,
                                           @RequestParam(required = false) MultipartFile image) throws IOException {
        ParkingSpace parkingSpace = ParkingSpace.builder()
                .latitude(latitude)
                .longitude(longitude)
                .address(address)
                .build();
        return parkingSpaceService.saveParkingSpace(parkingSpace, image);
    }
}

