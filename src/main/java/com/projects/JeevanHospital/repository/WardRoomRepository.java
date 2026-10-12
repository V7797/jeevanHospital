package com.projects.JeevanHospital.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.JeevanHospital.model.WardRoom;

@Repository
public interface WardRoomRepository extends JpaRepository<WardRoom, Long>{
	 // Usecase: Quickly track vacancy status for emergency room placement dashboards
    List<WardRoom> findByIsOccupied(boolean isOccupied);
    
    // Usecase: Filter clinical rooms by operational classification groupings
    List<WardRoom> findByRoomType(String roomType);
    
    // Usecase: Verify duplicate allocations before initializing new clinic maps
    boolean existsByRoomNumber(String roomNumber);

}
