package edu.escuelaing.ieti.app.service;

import edu.escuelaing.ieti.app.controller.user.UserDto;
import edu.escuelaing.ieti.app.repository.document.User;

import java.util.List;

public interface UserService
{
    User create(UserDto userDto );

    User findById( String id );

    User findByEmail( String email );

    List<User> all();

    boolean deleteById( String id );

    User update( UserDto userDto, String id );
}