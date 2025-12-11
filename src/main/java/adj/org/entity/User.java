package adj.org.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;


@Entity @Getter @Setter @AllArgsConstructor @ToString
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;

    @NotBlank(message = "O CPF é obrigatório")
    public String cpf;

    @NotBlank
    public LocalDate register_data;

    public User(){
        this.register_data = LocalDate.now();
    }

    @NotBlank(message = "O nome é obrigatório")
    public String nome;

    @NotBlank(message = "O E-mail é obrigatório")
    @Email(message = "Digite um e-mail válido")
    public String email;

    @Min(value = 18, message = "A idade mínima é 18 anos")
    public int idade;
}
