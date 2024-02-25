package edu.escuelaing.ieti.app.repository.document;

import edu.escuelaing.ieti.app.controller.user.UserDto;
import edu.escuelaing.ieti.app.exception.ProyectoNoExiste;
import edu.escuelaing.ieti.app.model.Cantidades;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;


import java.util.*;

@Document
public class User
{
    @Id
    String id;

    String name;

    String lastName;

    @Indexed( unique = true )
    String email;

    String passwordHash;

    List<RoleEnum> roles;

    Date createdAt;


    HashMap<String, Cantidades> cantidadesDeUsuario = new HashMap<String, Cantidades>();

    public User()
    {
    }


    public User( UserDto userDto )
    {
        name = userDto.getName();
        lastName = userDto.getLastName();
        email = userDto.getEmail();
        createdAt = new Date();
        roles = new ArrayList<>( Collections.singleton( RoleEnum.USER ) );
        //TODO uncomment this line
        // passwordHash = BCrypt.hashpw( userDto.getPassword(), BCrypt.gensalt() );
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getEmail()
    {
        return email;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public HashMap<String, Cantidades> getCantidadesDeUsuario() {
        return cantidadesDeUsuario;
    }

    public Cantidades getProyectoPorNombre(String nombreProyecto) throws ProyectoNoExiste {
        if (cantidadesDeUsuario.containsKey(nombreProyecto)) {
            return cantidadesDeUsuario.get(nombreProyecto);
        }
        throw new ProyectoNoExiste("No existe un proyecto con el nombre: " + nombreProyecto);
    }

    public Cantidades addProyecto(String nombreProyecto)  {
        cantidadesDeUsuario.put(nombreProyecto, new Cantidades(nombreProyecto));
        return cantidadesDeUsuario.get(nombreProyecto);
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public List<RoleEnum> getRoles()
    {
        return roles;
    }

    public void update( UserDto userDto )
    {
        this.name = userDto.getName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        //TODO uncomment these lines
        /*if ( userDto.getPassword() != null )
        {
            this.passwordHash = BCrypt.hashpw( userDto.getPassword(), BCrypt.gensalt() );
        }*/
    }

}
