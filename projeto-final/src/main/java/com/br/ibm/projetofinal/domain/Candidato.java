package com.br.ibm.projetofinal.domain;

import java.util.Objects;

public class Candidato {
    private String nome;
    private static int codCandidato;
    private int id;

    public Candidato() {

    }

    public Candidato(String nome) {
        Candidato.codCandidato++;
        id = codCandidato;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;

    }
    public int getCodCandidato() {
        return codCandidato;
    }

    public void setCodCandidato(int codCandidato) {
        this.codCandidato = codCandidato;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidato candidato = (Candidato) o;
        return Objects.equals(nome, candidato.nome);
    }

}
