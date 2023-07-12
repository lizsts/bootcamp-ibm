package com.br.ibm.projetofinal.service;

import com.br.ibm.projetofinal.domain.Candidato;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class CandidatoService {

    ArrayList<Candidato> recebidos = new ArrayList<>();
    ArrayList<Candidato> qualificados = new ArrayList<>();
    ArrayList<Candidato> aprovados = new ArrayList<>();

    String errorMessage = "Candidato não encontrado.";

    public Candidato iniciarProcesso(String nome) {
        String regexNumber = "\\d+";
        String regexChar = "^[\\p{L}\\s]+$";

        if (nome.trim().isEmpty() || nome.matches(regexNumber) || !nome.matches(regexChar)) {
            throw new IllegalArgumentException("Nome inválido.");
        }

        boolean result = recebidos.stream().anyMatch(c -> c.getNome().equals(nome));
        boolean result2 = qualificados.stream().anyMatch(c -> c.getNome().equals(nome));
        boolean result3 = aprovados.stream().anyMatch(c -> c.getNome().equals(nome));

        if (result || result2 || result3) {
            throw new IllegalArgumentException("Candidato já participa do processo.");
        }

        Candidato novoCandidato = new Candidato(nome);
        recebidos.add(novoCandidato);

        return novoCandidato;

    }

    public void marcarEntrevista(int codCandidato) {
        boolean result = recebidos.stream().anyMatch(c -> c.getId() == codCandidato);

        if (!result) {
            throw new IllegalArgumentException(errorMessage);
        }

        Iterator<Candidato> it = recebidos.iterator();
        while (it.hasNext()) {

            Candidato element = it.next();

            if (element.getId() == codCandidato) {
                qualificados.add(element);
                it.remove();

            }
        }
    }

    public void desqualificarCandidato(int codCandidato) {

        boolean desqualificado = false;

        Iterator<Candidato> itR = recebidos.iterator();

        while (itR.hasNext()) {
            if (itR.next().getId() == codCandidato) {
                itR.remove();
                desqualificado = true;
            }
        }

        Iterator<Candidato> itQ = qualificados.iterator();

        while (itQ.hasNext()) {
            if (itQ.next().getId() == codCandidato) {
                itQ.remove();
                desqualificado = true;
            }
        }

        Iterator<Candidato> itA = aprovados.iterator();

        while (itA.hasNext()) {
            if (itA.next().getId() == codCandidato) {
                itA.remove();
                desqualificado = true;
            }
        }

        if (!desqualificado) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public String verificarStatusCandidato(int codCandidato) throws ClassNotFoundException {
        enum status {RECEBIDO, QUALIFICADO, APROVADO}

        for (Candidato i : recebidos) {
            boolean recebido = i.getId() == codCandidato;
            if (recebido) {
                return status.RECEBIDO.name();
            }
        }
        for (Candidato i : qualificados) {
            boolean qualificado = i.getId() == codCandidato;
            if (qualificado) {
                return status.QUALIFICADO.name();
            }
        }
        for (Candidato i : aprovados) {
            boolean aprovado = i.getId() == codCandidato;
            if (aprovado) {
                return status.APROVADO.name();
            }
        }

        throw new ClassNotFoundException(errorMessage);
    }

    public void aprovarCandidato(int codCandidato) {
        boolean result = qualificados.stream().anyMatch(c -> c.getId() == codCandidato);

        if (!result) {
            throw new IllegalArgumentException(errorMessage);
        }

        Iterator<Candidato> it = qualificados.iterator();
        while (it.hasNext()) {

            Candidato element = it.next();

            if (element.getId() == codCandidato) {
                aprovados.add(element);
                it.remove();
            }
        }

    }

    public List<String> obterAprovados() {

        ArrayList<String> candidatosAprovados = new ArrayList<>();

        for (Candidato a : aprovados) {
            candidatosAprovados.add(a.getNome());
        }
        return candidatosAprovados;
    }
}

