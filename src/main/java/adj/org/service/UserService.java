package adj.org.service;

import adj.org.entity.User;
import adj.org.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository repository;

    @Transactional
    public void registerUser(User newUser){

        System.out.println("Cadastrando novo cliente: " + newUser);

        String cpfOriginal = newUser.cpf;

        String cpfFormatado = cpfOriginal.replaceAll("\\D", ""); // Limpa tudo que não é digito do campo

        if(repository.find("cpf", cpfFormatado).count() > 0){
            throw new IllegalArgumentException("Já existe um usuário cadastrado com esse CPF");
        }

        newUser.cpf = cpfFormatado;

        repository.persist(newUser);
    }
}