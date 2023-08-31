package segundoteste;

import java.util.Objects;

public class Candidato {
    protected String nome;
    protected static int codCandidato;
    protected int id;

    public Candidato() {

   }

    public Candidato(String nome) {
        Candidato.codCandidato++;
        id = codCandidato;
        setNome(nome);
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
