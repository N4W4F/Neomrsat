package com.example.neomsr.Service;

import com.example.neomsr.ApiResponse.ApiException;
import com.example.neomsr.DTOout.ZoneDTOout;
import com.example.neomsr.Model.MyUser;
import com.example.neomsr.Model.Zone;
import com.example.neomsr.Repository.AuthRepository;
import com.example.neomsr.Repository.ZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final AuthRepository authRepository;


    public void creatZone(Integer userId, Zone zone){
        MyUser user=authRepository.findMyUserById(userId);
        if(user==null){
            throw new ApiException("User with this ID: "+userId+" Is not found");
        }
        zone.setAvgRating(0.0);
        zoneRepository.save(zone);
    }


    public void updateZone(Integer userID,Zone zone){
        MyUser user=authRepository.findMyUserById(userID);
        if(user==null){
            throw new ApiException("User with this ID: "+userID+" Is not found");
        }
        Zone oldZone=zoneRepository.findZoneById(zone.getId());
        if(oldZone==null){
            throw new ApiException("Zone with this ID: "+zone.getId()+" Is not found");
        }
        oldZone.setZoneName(zone.getZoneName());
        oldZone.setCapacity(zone.getCapacity());
        oldZone.setDescription(zone.getDescription());
        oldZone.setLocation(zone.getLocation());
        oldZone.setPricePerHour(zone.getPricePerHour());
        oldZone.setArea(zone.getArea());

        zoneRepository.save(oldZone);
    }

    public void deleteZone(Integer userId,Integer zoneId){
        MyUser user=authRepository.findMyUserById(userId);
        if(user==null){
            throw new ApiException("User with this ID: "+userId+" Is not found");
        }
        Zone zone=zoneRepository.findZoneById(zoneId);
        if(zone==null){
            throw new ApiException("Zone with this ID: "+zoneId+ " Is not found");
        }
           zoneRepository.delete(zone);
    }


    public List<ZoneDTOout>allZone(){
        List<Zone> zone=zoneRepository.findAll();

        List<ZoneDTOout>zoneDTOouts=new ArrayList<>();
        for (Zone z:zone){
            ZoneDTOout dto=new ZoneDTOout();
            dto.setArea(z.getArea());
            dto.setZoneName(z.getZoneName());
            dto.setLocation(z.getLocation());
            dto.setDescription(z.getDescription());
            dto.setCapacity(z.getCapacity());
            dto.setPricePerHour(z.getPricePerHour());
            dto.setAvgRating(z.getAvgRating());
            zoneDTOouts.add(dto);
        }
     return zoneDTOouts;

    }

    public ZoneDTOout getZone(Integer zoneId) {
        Zone zone = zoneRepository.findZoneById(zoneId);

        if (zone == null) {
            throw new ApiException("Zone with ID: " + zoneId + " is not found");
        }

        ZoneDTOout dto = new ZoneDTOout();
        dto.setArea(zone.getArea());
        dto.setZoneName(zone.getZoneName());
        dto.setLocation(zone.getLocation());
        dto.setDescription(zone.getDescription());
        dto.setCapacity(zone.getCapacity());
        dto.setPricePerHour(zone.getPricePerHour());
        dto.setAvgRating(zone.getAvgRating());

        return dto;
    }

}
