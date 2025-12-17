package adj.org.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
public class ErrorMessageDto {
    private String campo;
    private String mensagem;
}
