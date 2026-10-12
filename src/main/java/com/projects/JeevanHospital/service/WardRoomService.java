package com.projects.JeevanHospital.service;

import java.util.List;
import java.util.Optional;
import com.projects.JeevanHospital.model.WardRoom;

public interface WardRoomService {
    
    List<WardRoom> getAllRooms();
    
    Optional<WardRoom> getRoomById(Long wardRoomId);
    
    WardRoom registerRoom(WardRoom wardRoom);
    
    WardRoom updateRoom(WardRoom wardRoom, Long wardRoomId);
    
    void deleteRoomById(Long wardRoomId);
    
    // 🌟 Advanced Operational Query Filters for Admissions
    List<WardRoom> getAvailableRooms();
    
    List<WardRoom> getRoomsByType(String roomType);
}
