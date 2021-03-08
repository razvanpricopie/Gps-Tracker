package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.UserLocation;
import org.scd.model.dto.FilterLocationDTO;
import org.scd.model.dto.UserLocationDTO;
import org.scd.repository.UserLocationRepository;
import org.scd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserLocationServiceImpl implements UserLocationService {

    @Autowired
    private UserLocationRepository userLocationRepository;

    @Autowired
    private UserRepository userRepository;



    @Override
    public List<UserLocation> getAllLocations() {
        List<UserLocation> userLocations = new ArrayList<>();
        Iterable<UserLocation> var10000 = this.userLocationRepository.findAll();
        Objects.requireNonNull(userLocations);
        var10000.forEach(userLocations::add);
        return userLocations;
    }

    @Override
    public UserLocation getLocationById(Long id) {
        return userLocationRepository.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        this.userLocationRepository.deleteById(id);
    }

    @Override
    public UserLocation update(UserLocation userLocation, Long id) {
        UserLocation userLocationUp = userLocationRepository.getById(id);
        if(!(userLocation.getLatitude()== null)){
            userLocationUp.setLatitude(userLocation.getLatitude());
        }
        if(!(userLocation.getLongitude()== null)){
            userLocationUp.setLongitude(userLocation.getLongitude());
        }
        return userLocationRepository.save(userLocationUp);
    }

    @Override
    public UserLocationDTO addLocation(UserLocationDTO userLocationDTO) {

        userLocationRepository.save(new UserLocation(userLocationDTO.getLatitude(), userLocationDTO.getLongitude(), userLocationDTO.getCreationDate(), userRepository.findByEmail(userLocationDTO.getEmail())));
        return null;
    }



    public List<UserLocation> filterByStartAndEnd(FilterLocationDTO filterLocationDTO) throws BusinessException {

        if(filterLocationDTO.getStartDate().compareTo(filterLocationDTO.getEndDate()) > 0){
            throw new BusinessException(402, "End date cannot be before start date");
        }

        return userLocationRepository.myQuery(filterLocationDTO.getUserId(), filterLocationDTO.getStartDate(), filterLocationDTO.getEndDate());
    }
}
