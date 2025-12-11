package adj.org.controller;

import adj.org.entity.User;
import adj.org.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped @Path("/usuario")
public class UserController {

    @Inject
    UserService service;

    @POST
    @Path("/cadastrar")
    public Response registerUser(@Valid User newUser){

        try {
            service.registerUser(newUser);
            System.out.println("Usuário Cadastrado com Sucesso!");
            return Response.status(Response.Status.CREATED).build();

        } catch (IllegalArgumentException error) {
            return Response.status(409).entity(error.getMessage()).build();
        }
    }
}
