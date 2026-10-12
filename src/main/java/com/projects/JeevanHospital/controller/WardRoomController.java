package com.projects.JeevanHospital.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.projects.JeevanHospital.model.WardRoom;
import com.projects.JeevanHospital.service.WardRoomService;

@RestController
@RequestMapping("/api/rooms")
public class WardRoomController {

    @Autowired
    private WardRoomService wardRoomService;

    @GetMapping
    public ResponseEntity<List<WardRoom>> getAllRooms() {
        List<WardRoom> rooms = wardRoomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WardRoom> getRoomById(@PathVariable("id") Long wardRoomId) {
        return wardRoomService.getRoomById(wardRoomId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WardRoom> registerRoom(@RequestBody WardRoom wardRoom) {
        WardRoom savedRoom = wardRoomService.registerRoom(wardRoom);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WardRoom> updateRoom(
            @PathVariable("id") Long wardRoomId, 
            @RequestBody WardRoom updatedRecord) {
        WardRoom updatedRoom = wardRoomService.updateRoom(updatedRecord, wardRoomId);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoomById(@PathVariable("id") Long wardRoomId) {
        wardRoomService.deleteRoomById(wardRoomId);
        return ResponseEntity.ok("Ward room deleted successfully.");
    }

    @GetMapping("/available")
    public ResponseEntity<List<WardRoom>> getAvailableRooms() {
        List<WardRoom> rooms = wardRoomService.getAvailableRooms();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/type/{roomType}")
    public ResponseEntity<List<WardRoom>> getRoomsByType(@PathVariable("roomType") String roomType) {
        List<WardRoom> rooms = wardRoomService.getRoomsByType(roomType);
        return ResponseEntity.ok(rooms);
    }
}
