package br.com.ada.cineSpring.model;

import java.util.List;

public class FilmeAtoresForm {
    private Filme filme;
    private List<Integer> atoresIds;

    public FilmeAtoresForm(){

    }

    public Filme getFilme() {
        return filme;
    }

    public void setFilme(Filme filme) {
        this.filme = filme;
    }

    public List<Integer> getAtoresIds() {
        return atoresIds;
    }

    public void setAtoresIds(List<Integer> atoresIds) {
        this.atoresIds = atoresIds;
    }
}
