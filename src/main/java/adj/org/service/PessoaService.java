package adj.org.service;

import adj.org.entity.Pessoa;
import adj.org.repository.PessoaRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class PessoaService{

    @Inject
    PessoaRepository repository;

    @Transactional
    public void cadastrarCliente(Pessoa novoCliente){

        System.out.println("Cadastrando novo cliente: " + novoCliente);

        String cpfOriginal = novoCliente.cpf;

        String cpfFormatado = cpfOriginal.replaceAll("\\D", ""); // Limpa tudo que não é digito do campo

        if(repository.find("cpf", cpfFormatado).count() > 0){
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");
        }

        novoCliente.cpf = cpfFormatado;

        repository.count("cpf=:cpf and nome=:nome", Parameters.with(":cpf", valor).and(":nome",valor));


        repository.persist(novoCliente);
    }
}