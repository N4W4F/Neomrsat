package com.example.neomrsat.Controller;

import com.example.neomrsat.ApiResponse.ApiResponse;
import com.example.neomrsat.DTOout.ZoneDTOout;
import com.example.neomrsat.Model.MyUser;
import com.example.neomrsat.Model.Zone;
import com.example.neomrsat.Service.ZoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/neomrsat/zone")
public class ZoneController {
    private final ZoneService zoneService;


    @PostMapping("/create")
    public ResponseEntity createZone(@AuthenticationPrincipal MyUser myUser,
                                     @RequestBody @Valid Zone zone) {
        zoneService.createZone(myUser.getId(), zone);
        return ResponseEntity.status(200).body(new ApiResponse("Zone has been created successfully"));
    }

    @GetMapping("/get-all")
    public ResponseEntity getAllZones() {
        return ResponseEntity.status(200).body(zoneService.allZone());
    }

    @PutMapping("/update/{zoneId}")
    public ResponseEntity updateZone(@AuthenticationPrincipal MyUser myUser,
                                     @PathVariable Integer zoneId,
                                     @RequestBody @Valid Zone zone) {
        zoneService.updateZone(myUser.getId(), zoneId, zone);
        return ResponseEntity.status(200).body(new ApiResponse("Zone has been updated successfully"));
    }

    @DeleteMapping("/delete/{zoneId}")
    public ResponseEntity  deleteZone(@AuthenticationPrincipal MyUser myUser, @PathVariable Integer zoneId) {
        zoneService.deleteZone(myUser.getId(), zoneId);
        return ResponseEntity.status(200).body(new ApiResponse("Zone has been deleted successfully"));
    }

    @GetMapping("/get/{zoneId}")
    public ResponseEntity getZone(@PathVariable Integer zoneId) {
        ZoneDTOout zoneDTO = zoneService.getZone(zoneId);
        return ResponseEntity.status(200).body(zoneDTO);
    }

    @GetMapping("/get-by-area/{area}")
    public ResponseEntity getZoneByArea(@PathVariable Integer area) {
        return ResponseEntity.status(200).body(zoneService.getZoneByArea(area));
    }

    @GetMapping("/get-by-capacity/{capacity}")
    public ResponseEntity getZoneByCapacity(@PathVariable Integer capacity) {
        return ResponseEntity.status(200).body(zoneService.getZoneByCapacity(capacity));
    }

    @GetMapping("/get-by-name/{zoneName}")
    public ResponseEntity getZoneByName(@PathVariable String zoneName) {
        return ResponseEntity.status(200).body(zoneService.getZoneByName(zoneName));
    }

    @GetMapping("/get-by-price/{price}")
    public ResponseEntity getZoneByPricePerHour(@PathVariable BigDecimal price) {
        return ResponseEntity.status(200).body(zoneService.getZoneByPricePerHour(price));
    }
}
