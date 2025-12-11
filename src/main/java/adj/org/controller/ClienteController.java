package adj.org.controller;

import adj.org.entity.Pessoa;
import adj.org.service.PessoaService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped @Path("/cliente")
public class ClienteController {

    @Inject
    PessoaService service;

    @POST
    @Path("/cadastrar")
    public Response cadastrarCliente(@Valid Pessoa novoCliente){

        try {
            service.cadastrarCliente(novoCliente);
            System.out.println("Cliente Cadastrado com Sucesso!");
            return Response.status(Response.Status.CREATED).build();

        } catch (IllegalArgumentException error) {
            return Response.status(409).entity(error.getMessage()).build();
        }
    }
}
