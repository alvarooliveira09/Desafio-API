package com.desafio2.Desafio2.controllers;

import com.desafio2.Desafio2.entities.Destino;
import com.desafio2.Desafio2.entities.dtos.DestinoNewDTO;
import com.desafio2.Desafio2.services.DestinoService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@Tag(name="1. Destino") //https://stackoverflow.com/a/73729139
@RequestMapping("/destino")
public class DestinoController {

    @Autowired
    private DestinoService service;

    @GetMapping("/buscar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})})
    public ResponseEntity<?> buscar(){
        try {
            List<Destino> entidades = service.buscar();
            return ResponseEntity.ok(entidades);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Destino.class)))}),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            Optional<Destino> entidade = service.buscarPorId(id);
            if(entidade.isPresent())
                return ResponseEntity.ok(entidade.get());
            else
                return ResponseEntity.notFound().build();

        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/nome/{nome}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})
    })
    public ResponseEntity<?> buscarPorNome(@PathVariable String nome){
        try {
            List<Destino> entidades = service.buscarPorNome(nome);
            return ResponseEntity.ok(entidades);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/localizacao/{localizacao}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})
    })
    public ResponseEntity<?> buscarPorLocalizacao(@PathVariable String localizacao){
        try {
            List<Destino> entidades = service.buscarPorLocalizacao(localizacao);
            return ResponseEntity.ok(entidades);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cadastrar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})})
    public ResponseEntity<?> cadastrar(@RequestBody DestinoNewDTO entidade){
        try {
            Destino entidadeCriada = service.cadastrar(entidade);
            return ResponseEntity.ok(entidadeCriada);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/avaliar/{id}/{nota}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})
    })
    public ResponseEntity<?> avaliar(@PathVariable Long id, @PathVariable int nota){
        try {
            if(nota < 0 || nota > 10){
                return ResponseEntity.badRequest().build();
            }
            Optional<Destino> entidade = service.buscarPorId(id);
            if(entidade.isPresent()) {
                int notaQuantidade = entidade.get().getQuantidadedenotas();
                if(notaQuantidade == 0){
                    entidade.get().setNota(nota);
                    entidade.get().setQuantidadedenotas(1);
                } else {
                    double notaAtual = entidade.get().getNota();
                    entidade.get().setNota(((notaAtual * notaQuantidade) + nota) / (notaQuantidade + 1));
                    entidade.get().setQuantidadedenotas(notaQuantidade + 1);
                }
                Destino destinoComNotaAtualizada = service.atualizar(entidade.get());
                return ResponseEntity.ok(destinoComNotaAtualizada);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/atualizar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})})
    public ResponseEntity<?> atualizarPut(@RequestBody Destino entidade){
        try {
            Destino entidadeAtualizada = service.atualizar(entidade);
            return ResponseEntity.ok(entidadeAtualizada);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(path = "/atualizar/{id}", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Destino.class)))}),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<?> atualizarPatch(@PathVariable Long id, @RequestBody String jsonPatchBody){

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //Busca no banco de dados pela entidade com o Id informado
            Optional<Destino> entidade = service.buscarPorId(id);
            if(entidade.isEmpty())
                return ResponseEntity.notFound().build();

            //Converte a entidade encontrada para o formato json, depois para JsonNode, e em seguida faz uma cópia
            String json = objectMapper.writeValueAsString(entidade.get());
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode jsonNodeCopy = jsonNode.deepCopy();

            //Converte o json string recebido pelo endpoint para o formato JsonNode
            JsonNode jsonNodePatch = objectMapper.readTree(jsonPatchBody);

            //Cria uma lista com os campos do json recebido pelo endpoint
            Iterator<Map.Entry<String, JsonNode>> fields = jsonNodePatch.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                // Passa os valores dos campos do json recebido pelo endpoint,
                // para a copia da entidade encontrada no banco de dados.
                switch (field.getKey()){
                    case "nome", "localizacao": {
                        ((ObjectNode) jsonNodeCopy).put(field.getKey(), field.getValue().asText());
                        break;
                    }
                    default: return ResponseEntity.badRequest().build();
                }
            }

            // Converte a cópia para json string, depois para a entidade e em seguida salva no banco de dados
            String jsonStringCopy = objectMapper.writeValueAsString(jsonNodeCopy);
            Destino entityCopy = objectMapper.readValue(jsonStringCopy, Destino.class);
            Destino entity = service.atualizar(entityCopy);

            return ResponseEntity.ok(entity);

        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/excluir/{id}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",
            description = "Exclusão realizada com sucesso", content = {@Content(mediaType = "text/plain")})})
    public ResponseEntity<?> excluir(@PathVariable Long id){
        try {
            service.excluir(id);
            return ResponseEntity.ok("Exclusão realizada com sucesso");
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }


}
