package adj.org.controller;

import adj.org.entity.User;
import adj.org.models.FindUserDto;
import adj.org.models.UpdateUserDto;
import adj.org.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error.getMessage())
                    .build();
        }
    }


    @GET @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id){

        var userFound = service.getUserById(id);

        System.out.println("Usuário Encontrado com Sucesso! (Único)");

        return Response.ok(userFound).build();
    }


    @PUT @Path("/editar/{id}")
    public Response updateUser(@PathParam("id") Long id, UpdateUserDto userInfo){

        userInfo.id = id;

        var userFound = service.getUserById(id);

        if(userInfo.cpf == null){
            userInfo.cpf = userFound.cpf;
        }
        if(userInfo.nome == null){
            userInfo.nome = userFound.nome;
        }
        if(userInfo.idade == 0){
            userInfo.idade = userFound.idade;
        }
        if(userInfo.email == null){
            userInfo.email = userFound.email;
        }

        service.updateUser(userInfo);

        return Response.status(Response.Status.OK)
                .entity("Usuário atualizado com sucesso!")
                .build();

    }

    @DELETE
    @Path("/editar/{id}")
    public Response deleteUser(@PathParam("id") Long id){

        try {
            service.deleteUser(id);
            System.out.println("Usuário Removido com Sucesso!");
            return Response.status(Response.Status.OK)
                    .entity("Usuário Removido com Sucesso!")
                    .build();

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
