package com.divyanshu.ParkSpotter.repositories;

import com.divyanshu.parkSpotter.models.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, Long> {

    @Query(value = "SELECT * FROM parking_spaces WHERE " +
            "(6371 * acos(cos(radians(:latitude)) * cos(radians(latitude)) * " +
            "cos(radians(longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(latitude)))) < :radius",
            nativeQuery = true)
    List<ParkingSpace> findParkingSpacesWithinRadius(@Param("latitude") double latitude,
                                                     @Param("longitude") double longitude,
                                                     @Param("radius") double radius);
}
