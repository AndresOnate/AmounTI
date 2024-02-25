package edu.escuelaing.ieti.app.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import edu.escuelaing.ieti.app.repository.document.User;
import edu.escuelaing.ieti.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping( "/v1/user" )
public class UserController
{
    private final UserService userService;

    public UserController( @Autowired UserService userService )
    {
        this.userService = userService;
    }

    @GetMapping
    public List<User> all()
    {
        return userService.all();
    }

    @GetMapping( "/{id}" )
    public User findById( @PathVariable String id )
    {
        return userService.findById( id );
    }


    @PostMapping
    public ResponseEntity<User> create(@RequestBody UserDto userDto )
    {
        return ResponseEntity.ok( userService.create( userDto ) );
    }

    @PostMapping("/{id}/projects")
    public ResponseEntity<User> addProject(@PathVariable String id, @RequestBody String name ) throws JsonProcessingException {
        System.out.println("Entré ");
        System.out.println(name);
        return ResponseEntity.ok( userService.addProject(id, name) );
    }

    @GetMapping( "/{id}/{projectName}" )
    public ResponseEntity<Cantidades> findById(@PathVariable String id, @PathVariable String projectName) throws ProyectoNoExiste {

        return ResponseEntity.ok(userService.findProjectByUser(projectName, id ));
    }


    @PutMapping( "/{id}" )
    public ResponseEntity<User> update( @RequestBody UserDto userDto, @PathVariable String id )
    {
        return ResponseEntity.ok( userService.update( userDto, id ) );
    }

    @DeleteMapping( "/{id}" )
    public ResponseEntity<Boolean> delete( @PathVariable String id )
    {
        return ResponseEntity.ok( userService.deleteById( id ) );
    }

}
