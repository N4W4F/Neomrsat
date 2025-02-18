package com.example.neomsr.Repository;

import com.example.neomsr.Model.ZoneSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ZoneScheduleRepository extends JpaRepository<ZoneSchedule,Integer> {
}
