package com.esgi.notfound.exposition.controllers;

import com.esgi.notfound.domain.entities.ParkingSpot;
import com.esgi.notfound.domain.services.ParkingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/parking")
public class ParkingController {

    private final ParkingService parkingService;

    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/available")
    public List<ParkingSpot> getAvailableSpots(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false, defaultValue = "false") boolean needsElectric
    ) {
        return needsElectric
                ? parkingService.getAvailableElectricSpots(start, end)
                : parkingService.getAvailableStandardSpots(start, end);
    }
}
