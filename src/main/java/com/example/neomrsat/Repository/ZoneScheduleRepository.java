package com.example.neomrsat.Repository;

import com.example.neomrsat.Model.ZoneSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneScheduleRepository extends JpaRepository<ZoneSchedule,Integer> {
}
