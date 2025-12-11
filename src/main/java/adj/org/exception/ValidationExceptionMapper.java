package adj.org.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {

        // 1. Pega todas as mensagens de erro (caso tenha mais de um campo errado)
        String mensagem = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage) // Pega só o texto da mensagem
                .collect(Collectors.joining(", ")); // Junta com vírgula se tiver mais de um

        // 2. Monta um JSON simples
        Map<String, String> erroJson = new HashMap<>();
        erroJson.put("error", mensagem); // { "error": "A idade mínima é 18 anos" }

        // 3. Retorna com Status 400 (Bad Request)
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(erroJson)
                .build();
    }
}