package com.projects.JeevanHospital.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.projects.JeevanHospital.model.WardRoom;
import com.projects.JeevanHospital.repository.WardRoomRepository;
import com.projects.JeevanHospital.service.WardRoomService;

@Service
public class WardRoomServiceImpl implements WardRoomService {

    @Autowired
    private WardRoomRepository wardRoomRepository;

    @Override
    @Transactional(readOnly = true)
    public List<WardRoom> getAllRooms() {
        return wardRoomRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WardRoom> getRoomById(Long wardRoomId) {
        return wardRoomRepository.findById(wardRoomId);
    }

    @Override
    @Transactional
    public WardRoom registerRoom(WardRoom wardRoom) {
        // 1. Conflict Prevention: Check for duplicate clinical room numbers
        if (wardRoomRepository.existsByRoomNumber(wardRoom.getRoomNumber())) {
            throw new IllegalStateException("A ward room already exists with Room Number: " + wardRoom.getRoomNumber());
        }
        
        return wardRoomRepository.save(wardRoom);
    }

    @Override
    @Transactional
    public WardRoom updateRoom(WardRoom updatedRecord, Long wardRoomId) {
        WardRoom existingRoom = wardRoomRepository.findById(wardRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Ward room not found with ID: " + wardRoomId));

        // Update changeable architectural metrics securely
        existingRoom.setRoomNumber(updatedRecord.getRoomNumber());
        existingRoom.setRoomType(updatedRecord.getRoomType());
        existingRoom.setOccupied(updatedRecord.isOccupied());
        //existingRoom.setDailyCharge(updatedRecord.getDailyCharge());

        return wardRoomRepository.save(existingRoom);
    }

    @Override
    @Transactional
    public void deleteRoomById(Long wardRoomId) {
        WardRoom wardRoom = wardRoomRepository.findById(wardRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot delete. Ward room not found with ID: " + wardRoomId));
        
        // Safety Guardrail: Prevent deleting an active room with a patient currently checked into it
        if (wardRoom.isOccupied()) {
            throw new IllegalStateException("Operational conflict: Cannot delete a ward room while it is marked as occupied.");
        }
        
        wardRoomRepository.deleteById(wardRoomId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WardRoom> getAvailableRooms() {
        return wardRoomRepository.findByIsOccupied(false); // Fetches only vacant rooms
    }

    @Override
    @Transactional(readOnly = true)
    public List<WardRoom> getRoomsByType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty()) {
            throw new IllegalArgumentException("Room type search parameter cannot be blank");
        }
        return wardRoomRepository.findByRoomType(roomType);
    }
}
