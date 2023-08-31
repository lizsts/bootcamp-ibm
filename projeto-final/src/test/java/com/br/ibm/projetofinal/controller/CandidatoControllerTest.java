package com.br.ibm.projetofinal.controller;


import com.br.ibm.projetofinal.domain.Candidato;
import com.br.ibm.projetofinal.domain.CandidatoDTO;
import com.br.ibm.projetofinal.domain.CodCandidatoDTO;
import com.br.ibm.projetofinal.service.CandidatoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
class CandidatoControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CandidatoService service;

    @Test
    @DisplayName("Deve cadastrar um candidato pelo nome")
    public void testStart() throws Exception {

        CandidatoDTO nomeCandidato = new CandidatoDTO("Liz");
        Candidato novoCandidato = new Candidato();

        when(service.iniciarProcesso(any())).thenReturn(novoCandidato);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/start").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(nomeCandidato))).andExpect(MockMvcResultMatchers.status().isCreated());

    }

    @Test
    @DisplayName("Deve dar erro de requisição por nome inválido")
    public void testStartErro() throws Exception {

        CandidatoDTO nomeCandidato = new CandidatoDTO("Liz");
        Candidato novoCandidato = new Candidato();
        byte campoErrado = 0;

        when(service.iniciarProcesso(any())).thenReturn(novoCandidato);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/start").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsBytes(campoErrado))).andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    @DisplayName("Deve marcar entrevista para candidato pelo id")
    public void testSchedule() throws Exception {

        CodCandidatoDTO codCandidato = new CodCandidatoDTO(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(codCandidato)))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    @DisplayName("Deve dar erro do tipo de entrada para id do candidato")
    public void testScheduleError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("Qualquer texto")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve desqualificar candidato pelo id")
    public void testDisqualify() throws Exception {
        CodCandidatoDTO codCandidato = new CodCandidatoDTO(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/disqualify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(codCandidato)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve dar erro de requisição por campo id inválido")
    public void testDisqualifyError() throws Exception {
        CodCandidatoDTO codCandidato = new CodCandidatoDTO(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/disqualify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve aprovar candidato pelo id")
    public void testApprove() throws Exception {
        CodCandidatoDTO codCandidato = new CodCandidatoDTO(1);

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(codCandidato)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve dar erro de solicitação por id inválido")
    public void testApproveError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/hiring/approve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString("Qualquer texto")))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve mostrar o atual status do candidato pelo id")
    public void testStatus() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/hiring/status/candidate/{codCandidato}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve dar erro por parametro de path inválido")
    public void testStatusError() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/hiring/status/candidate/{codCandidato}", "tolinho")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve retornar a lista de candidatos aprovados")
    public void testApprovedList() throws Exception {
        List<String> aprovados = new ArrayList<>();

        aprovados.add("Liz");
        aprovados.add("Otto");

        when(service.obterAprovados()).thenReturn(aprovados);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/hiring/approved")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve retornar erro de tipo do header para lista de candidatos aprovados")
    public void testApprovedListError() throws Exception {
        List<String> aprovados = new ArrayList<>();

        aprovados.add("Liz");
        aprovados.add("Otto");

        when(service.obterAprovados()).thenReturn(aprovados);

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/hiring/approved")
                        .accept(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable());
    }

}