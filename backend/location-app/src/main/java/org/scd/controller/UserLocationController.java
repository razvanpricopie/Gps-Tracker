package org.scd.controller;

import org.scd.config.exception.BusinessException;
import org.scd.model.UserLocation;
import org.scd.model.dto.FilterLocationDTO;
import org.scd.model.dto.UserLocationDTO;
import org.scd.service.UserLocationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/locations")
public class UserLocationController {

    private final UserLocationServiceImpl userLocationServiceImpl;

    @Autowired
    public UserLocationController(UserLocationServiceImpl userLocationServiceImpl) {
        this.userLocationServiceImpl = userLocationServiceImpl;
    }

    @GetMapping(path = "/allLocations")
    public ResponseEntity<List<UserLocation>> getAllLocations(){
        return ResponseEntity.ok(this.userLocationServiceImpl.getAllLocations());
    }

    @GetMapping(path = "/getLocation/{locationId}")
    public UserLocation getLocationById(@PathVariable("locationId") Long locationId){
        return userLocationServiceImpl.getLocationById(locationId);
    }

    @DeleteMapping(path = "/deleteLocation/{locationId}")
    public void deleteLocation(@PathVariable("locationId") Long locationId){
        this.userLocationServiceImpl.deleteById(locationId);
    }

    @PutMapping(path = "/updateLocationById/{locationId}")
    public UserLocation updateLocationById(@RequestBody UserLocation userLocation,@PathVariable long locationId){
        return userLocationServiceImpl.update(userLocation, locationId);
    }

    @PostMapping(path = "/createLocation")
    public ResponseEntity<UserLocationDTO> addLocation(@RequestBody final UserLocationDTO userLocationDTO){
            return ResponseEntity.ok(userLocationServiceImpl.addLocation(userLocationDTO));
    }

    @GetMapping(path = "filterByStartAndEnd")
    public List<UserLocation> filterByStartAndEnd(@RequestBody FilterLocationDTO filterLocationDTO) throws BusinessException{
        return userLocationServiceImpl.filterByStartAndEnd(filterLocationDTO);
    }


}
