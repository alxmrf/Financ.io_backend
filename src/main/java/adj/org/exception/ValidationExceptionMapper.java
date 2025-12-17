package adj.org.exception;

import adj.org.models.ErrorMessageDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.ArrayList;
import java.util.List;

@Provider // <--- Isso diz ao Quarkus: "Use esta classe para tratar erros"
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        List<ErrorMessageDto> erros = new ArrayList<>();

        // Itera sobre todas as violações encontradas (ex: Idade < 18, Email inválido)
        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {

            // Pega o nome do campo (ex: "idade", "email")
            String nomeCampo = pegaNomeDoCampo(violation);

            // Pega a mensagem que você definiu na anotação (ex: "A idade mínima é 18 anos")
            String mensagem = violation.getMessage();

            erros.add(new ErrorMessageDto(nomeCampo, mensagem));
        }

        // Retorna status 400 (Bad Request) com a lista de erros em JSON
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(erros)
                .build();
    }

    // Método auxiliar para limpar o nome do caminho da propriedade
    private String pegaNomeDoCampo(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        // As vezes o path vem como "metodo.arg0.idade", queremos só "idade"
        int lastDot = path.lastIndexOf('.');
        return (lastDot != -1) ? path.substring(lastDot + 1) : path;
    }
}