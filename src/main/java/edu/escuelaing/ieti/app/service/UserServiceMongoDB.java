package edu.escuelaing.ieti.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.escuelaing.ieti.app.controller.user.UserDto;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import edu.escuelaing.ieti.app.repository.UserRepository;
import edu.escuelaing.ieti.app.repository.document.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return userRepository.save( new User( userDto ) );
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
    public Cantidades findProjectByUser(String projectName, String id) throws ProyectoNoExiste {
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
}