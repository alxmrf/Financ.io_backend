package adj.org.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Entity @Getter @Setter @AllArgsConstructor @ToString @NoArgsConstructor
public class Pessoa {

    @Id
    @NotBlank(message = "O CPF é obrigatório")
    public String cpf;

    @NotBlank(message = "O nome é obrigatório")
    public String nome;

    @NotBlank(message = "O E-mail é obrigatório")
    @Email(message = "Digite um e-mail válido")
    public String email;

    @Min(value = 18, message = "A idade mínima é 18 anos")
    public int idade;
}
