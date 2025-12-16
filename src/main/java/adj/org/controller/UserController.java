package adj.org.controller;

import adj.org.entity.User;
import adj.org.models.DeleteUserDto;
import adj.org.models.FindUserDto;
import adj.org.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.Optional;

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
            return Response.status(Response.Status.CREATED).entity("Usuário Cadastrado com Sucesso!").build();

        } catch (IllegalArgumentException error) {
            return Response.status(409).entity(error.getMessage()).build();
        }
    }

    @POST
    @Path("/consultar")
    public Response findUser(FindUserDto searchData){

        if(searchData.cpf == null){
            searchData.cpf = "";
        }
        if(searchData.email == null){
            searchData.email = "";
        }

        try {
            var usersFound = service.findUser(searchData);
            System.out.println("Usuário(s) Encontrado(s) com Sucesso!");
            return Response.status(Response.Status.OK).entity(usersFound).build();

        } catch (IllegalArgumentException error) {
            return Response.status(409).entity(error.getMessage()).build();
        }
    }

    @DELETE
    @Path("/deletar")
    public Response deleteUser(DeleteUserDto deleteData){

        if(deleteData.id == null){
            deleteData.id = null;
        }
        if(deleteData.cpf == null){
            deleteData.cpf = "";
        }

        try {
            service.deleteUser(deleteData);
            System.out.println("Usuário Removido com Sucesso!");
            return Response.status(Response.Status.OK).entity("Usuário Removido com Sucesso!").build();

        } catch (IllegalArgumentException error) {
            return Response.status(Response.Status.NOT_ACCEPTABLE)
                    .entity(error.getMessage())
                    .build();
        } catch (NotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(e.getMessage())
                    .build();
        }
    }
}
