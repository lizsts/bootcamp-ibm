package com.br.ibm.projetofinal.controller;

import com.br.ibm.projetofinal.domain.*;
import com.br.ibm.projetofinal.service.CandidatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/hiring/", produces = MediaType.APPLICATION_JSON_VALUE)
@ConfigurationProperties(prefix = "message")
public class CandidatoController {

    @Autowired
    CandidatoService candidatoService;

    @Value("${message.schedule}")
    String messageSchedule;
    @Value("${message.disqualify}")
    String messageDisqualify;
    @Value("${message.approve}")
    String messageApprove;


    @PostMapping(value = "/start", headers = "content-type=application/json")
    public ResponseEntity<Candidato> cadastrarCandidato(@RequestBody CandidatoDTO candidato) {
        Candidato novoCandidato = candidatoService.iniciarProcesso(candidato.nome());
        return new ResponseEntity<Candidato>(novoCandidato, HttpStatus.CREATED);

    }

    @PostMapping(value = "/schedule", headers = "content-type=application/json")
    public ResponseEntity marcarEntrevista(@RequestBody CodCandidatoDTO codCandidato) {

        candidatoService.marcarEntrevista(codCandidato.codCandidato());
        return ResponseEntity.ok().body(messageSchedule);
    }

    @PostMapping(value = "/disqualify", headers = "content-type=application/json")
    public ResponseEntity desqualificarCandidato(@RequestBody CodCandidatoDTO codCandidato) {
        candidatoService.desqualificarCandidato(codCandidato.codCandidato());
        return ResponseEntity.ok().body(messageDisqualify);
    }

    @GetMapping(value = "/status/candidate/{codCandidato}")
    public ResponseEntity<StatusCandidatoDTO> verificarStatus(@PathVariable String codCandidato) throws ClassNotFoundException {
        String status = candidatoService.verificarStatusCandidato(Integer.parseInt(codCandidato));
        StatusCandidatoDTO statusAtual = new StatusCandidatoDTO(status);
        return ResponseEntity.ok(statusAtual);
    }

    @PostMapping(value = "/approve", headers = "content-type=application/json")
    public ResponseEntity aprovarCandidato(@RequestBody CodCandidatoDTO codCandidato) {
        candidatoService.aprovarCandidato(codCandidato.codCandidato());
       return ResponseEntity.ok().body(messageApprove);
    }

    @GetMapping(value = "/approved")
    public ResponseEntity<List<CandidatoAprovadoDTO>> candidatosAprovados() {
        List<String> aprovados = candidatoService.obterAprovados();
        return ResponseEntity.ok(aprovados.stream().map(CandidatoAprovadoDTO::new).toList());
    }


}
