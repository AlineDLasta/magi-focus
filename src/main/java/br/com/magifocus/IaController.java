
package br.com.magifocus;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {
        "http://127.0.0.1:5500",
        "http://localhost:5500"
})
@RestController
@RequestMapping("/api/ia")
public class IaController {

    @PostMapping("/perguntar")
    public Map<String, String> perguntar(@RequestBody Map<String, String> dados) {

        RestClient client = RestClient.create();

        Map resposta = client.post()
                .uri("http://localhost:11434/api/chat")
                .body(Map.of(
                        "model", "llama3.2:1b",
                        "messages", List.of(
                                Map.of(
                                        "role", "user",
                                        "content", dados.get("pergunta")
                                )
                        ),
                        "stream", false
                ))
                .retrieve()
                .body(Map.class);

        Map mensagem = (Map) resposta.get("message");

        return Map.of("resposta", (String) mensagem.get("content"));
    }
}
