package edu.escuelaing.ieti.app.controller.project;

import com.fasterxml.jackson.core.JsonProcessingException;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import edu.escuelaing.ieti.app.repository.document.User;
import edu.escuelaing.ieti.app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/v1/projects")
public class ProjectController {
    private final UserService userService;

    public ProjectController( @Autowired UserService userService ) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ArrayList<HashMap<String, Cantidades>> allProjects = userService.allProjects();
        if (!allProjects.isEmpty()) {
            return new ResponseEntity<>(allProjects, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No hay proyectos",HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAuthorsProjects (@PathVariable String id) {
        return ResponseEntity.ok(userService.allProjectsByUser(id));
    }

    @GetMapping( "/{id}/{projectName}" )
    public ResponseEntity<?> findByName(@PathVariable String id, @PathVariable String projectName) {
        try {
            return ResponseEntity.ok(userService.findProjectByUser(id, projectName));
        } catch (ProyectoNoExiste e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<User> addProject(@PathVariable String id, @RequestBody String projectName ) throws JsonProcessingException {
        System.out.println("Entré ");
        //System.out.println(project.getNombre());
        return ResponseEntity.ok(userService.addProject(id, projectName));
    }

    @PutMapping("/{id}/{projectName}")
    public ResponseEntity<?> updateNameProject(@PathVariable String id, @PathVariable String projectName, @RequestBody String newNameProject){
        try{
            return ResponseEntity.ok(userService.updateNameProject(id, projectName, newNameProject));
        } catch (ProyectoNoExiste e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }


}
