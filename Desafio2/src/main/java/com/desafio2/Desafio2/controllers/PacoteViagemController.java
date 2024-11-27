package com.desafio2.Desafio2.controllers;

import com.desafio2.Desafio2.entities.Destino;
import com.desafio2.Desafio2.entities.PacoteViagem;
import com.desafio2.Desafio2.entities.dtos.PacoteViagemNewDTO;
import com.desafio2.Desafio2.services.PacoteViagemService;
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
@Tag(name="1. Pacote de Viagem") //https://stackoverflow.com/a/73729139
@RequestMapping("/pacoteviagem")
public class PacoteViagemController {

    @Autowired
    private PacoteViagemService service;

    @GetMapping("/buscar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200",content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = Destino.class)))})})
    public ResponseEntity<?> buscar(){
        try {
            List<PacoteViagem> entidades = service.buscar();
            return ResponseEntity.ok(entidades);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/buscar/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PacoteViagem.class)))}),
            @ApiResponse(responseCode = "404")
    })
    public ResponseEntity<?> buscarPorId(@PathVariable Long id){
        try {
            Optional<PacoteViagem> entidade = service.buscarPorId(id);
            if(entidade.isPresent())
                return ResponseEntity.ok(entidade.get());
            else
                return ResponseEntity.notFound().build();

        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cadastrar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PacoteViagem.class)))})})
    public ResponseEntity<?> cadastrar(@RequestBody PacoteViagemNewDTO entidade){
        try {
            PacoteViagem entidadeCriada = service.cadastrar(entidade);
            return ResponseEntity.ok(entidadeCriada);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/atualizar")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
            array = @ArraySchema(schema = @Schema(implementation = PacoteViagem.class)))})})
    public ResponseEntity<?> atualizarPut(@RequestBody PacoteViagem entidade){
        try {
            PacoteViagem entidadeAtualizada = service.atualizar(entidade);
            return ResponseEntity.ok(entidadeAtualizada);
        } catch(Exception ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping(path = "/atualizar/{id}", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PacoteViagem.class)))}),
            @ApiResponse(responseCode = "404"),
            @ApiResponse(responseCode = "400")
    })
    public ResponseEntity<?> atualizarPatch(@PathVariable Long id, @RequestBody String jsonPatchBody){

        try {
            ObjectMapper objectMapper = new ObjectMapper();

            //Busca no banco de dados pela entidade com o Id informado
            Optional<PacoteViagem> entidade = service.buscarPorId(id);
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
                    case "nome": {
                        ((ObjectNode) jsonNodeCopy).put(field.getKey(), field.getValue().asText());
                        break;
                    }
                    default: return ResponseEntity.badRequest().build();
                }
            }

            // Converte a cópia para json string, depois para a entidade e em seguida salva no banco de dados
            String jsonStringCopy = objectMapper.writeValueAsString(jsonNodeCopy);
            PacoteViagem entityCopy = objectMapper.readValue(jsonStringCopy, PacoteViagem.class);
            PacoteViagem entity = service.atualizar(entityCopy);

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
