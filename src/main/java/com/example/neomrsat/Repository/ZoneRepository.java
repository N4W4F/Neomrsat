package com.example.neomrsat.Repository;

import com.example.neomrsat.Model.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ZoneRepository extends JpaRepository<Zone,Integer> {

    Zone findZoneById(Integer Id);


   List<Zone>findZoneByArea(Integer area);
   List<Zone>findZoneByCapacity(Integer capacity );
   List<Zone>findZoneByZoneName(String zoneName);
   List<Zone>findZoneByPricePerHour(BigDecimal price);

}
