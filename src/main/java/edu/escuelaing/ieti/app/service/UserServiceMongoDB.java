package edu.escuelaing.ieti.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.escuelaing.ieti.app.controller.user.UserDto;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import edu.escuelaing.ieti.app.repository.UserRepository;
import edu.escuelaing.ieti.app.repository.document.User;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceMongoDB implements UserService
{
    private final UserRepository userRepository;

    public UserServiceMongoDB( @Autowired UserRepository userRepository )
    {
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserDto userDto )
    {
        User createdUser = userRepository.save(new User(userDto));
        Document document = new Document(createdUser.getCantidadesDeUsuario());

        return createdUser;
    }

    @Override
    public User addProject(String id, String projectName) throws JsonProcessingException {
        Optional<User> optionalUser = userRepository.findById(id);
        System.out.println(optionalUser.isPresent());
        if ( optionalUser.isPresent() )
        {
            User user = optionalUser.get();
            user.addProyecto(projectName);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public Cantidades findProjectByUser(String id, String projectName) throws ProyectoNoExiste {
        User user  = userRepository.findById(id).get();
        Cantidades project = user.getProyectoPorNombre(projectName);
        return project;

    }

    @Override
    public User findById( String id )
    {
        Optional<User> optionalUser = userRepository.findById( id );
        if ( optionalUser.isPresent() )
        {
            return optionalUser.get();
        }
        else
        {
            return  null;

        }
    }

    @Override
    public User findByEmail( String email )
    {
        Optional<User> optionalUser = userRepository.findByEmail( email );
        if ( optionalUser.isPresent() )
        {
            return optionalUser.get();
        }
        else
        {
            return  null;
        }
    }

    @Override
    public List<User> all()
    {
        return userRepository.findAll();
    }

    @Override
    public boolean deleteById( String id )
    {
        if ( userRepository.existsById( id ) )
        {
            userRepository.deleteById( id );
            return true;
        }
        return false;
    }

    @Override
    public User update( UserDto userDto, String id )
    {
        if ( userRepository.findById( id ).isPresent() )
        {
            User user = userRepository.findById( id ).get();
            user.update( userDto );
            userRepository.save( user );
            return user;
        }
        return null;
    }

    public ArrayList<HashMap<String, Cantidades>> allProjects () {
        ArrayList<HashMap<String, Cantidades>> projects = new ArrayList<>();
        List<User> users = all();
        for (User user : users) {
            projects.add(user.getCantidadesDeUsuario());
        }
        return projects;
    }

    public HashMap<String, Cantidades> allProjectsByUser (String id) {
        User user = findById(id);
        return user.getCantidadesDeUsuario();
    }

    public Cantidades updateNameProject (String id, String projectName, String newNameProject) throws ProyectoNoExiste{
        User user = findById(id);
        Cantidades project = user.getProyectoPorNombre(projectName);
        project.setNombre(newNameProject);
        user.updateNameProject(projectName, project);
        userRepository.save(user);
        return project;
    }
}