package org.scd.service;

import org.scd.config.exception.BusinessException;
import org.scd.model.User;
import org.scd.model.dto.UserLoginDTO;
import org.scd.model.dto.UserRegisterDTO;

import java.util.List;

public interface UserService {
    /**
     * Get existing list of users from database
     * @return
     */
    List<User> getUsers();

    /**
     * Login into application
     * @param userLoginDTO - user information
     * @return
     */
    User login(final UserLoginDTO userLoginDTO) throws BusinessException;

    User register(final UserRegisterDTO userRegisterDTO) throws BusinessException;
}
