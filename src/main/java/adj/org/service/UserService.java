package adj.org.service;

import adj.org.entity.User;
import adj.org.models.FindUserDto;
import adj.org.models.UpdateUserDto;
import adj.org.repository.UserRepository;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;
import java.util.List;

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

    public List<User> findUser(FindUserDto searchData){

        var whereQuery = new ArrayList<String>();
        var parameters = new Parameters();

        if(!searchData.cpf.isBlank()){
            whereQuery.add("cpf=:cpf");
            parameters.and("cpf",searchData.cpf);
        }
        if(!searchData.email.isBlank()){
            whereQuery.add("email =: email");
            parameters.and("email",searchData.email);
        }

        if(whereQuery.isEmpty()){
            return repository.findAll().stream().toList();
        }

        var query = String.join(" and ", whereQuery);

        return repository.find(query,parameters).stream().toList();

    }

    public User getUserById(Long id){

        User user = repository.findById(id);

        if(user == null){
            throw new NotFoundException("Não existe um usuário com esse ID");
        }

        return user;
    }

    @Transactional
    public void updateUser(UpdateUserDto userInfo){

        User user = repository.findById(userInfo.id);

        System.out.println(user);

        if (user == null) {
            throw new NotFoundException("Não existe um usuário com esse ID");
        }

        user.setCpf(userInfo.cpf);
        user.setNome(userInfo.nome);
        user.setIdade(userInfo.idade);
        user.setEmail(userInfo.email);
    }

    @Transactional
    public void deleteUser(Long id){

        if(id != null){
            var quantidade = repository.delete("id=:id", Parameters.with("id", id));
            if(quantidade == 0){
                throw new NotFoundException("Não existe usuário com esse ID");
            }
            return;
        }

        throw new IllegalArgumentException("Insira o ID do usuário");
    }
}