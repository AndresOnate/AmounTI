package edu.escuelaing.ieti.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.escuelaing.ieti.app.controller.user.UserDto;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import edu.escuelaing.ieti.app.repository.document.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface UserService
{
    User create(UserDto userDto );
    User addProject(String projectName, String id) throws JsonProcessingException;

    Cantidades findProjectByUser(String projectName, String id) throws ProyectoNoExiste;

    User findById( String id );

    User findByEmail( String email );

    List<User> all();

    boolean deleteById( String id );

    User update( UserDto userDto, String id );
    ArrayList<HashMap<String, Cantidades>> allProjects ();
    HashMap<String, Cantidades> allProjectsByUser (String id);
}