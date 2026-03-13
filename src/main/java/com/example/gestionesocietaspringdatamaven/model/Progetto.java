package com.example.gestionesocietaspringdatamaven.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "progetto")
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "duratainmesi")
    private int durataInMesi;

    @ManyToMany(mappedBy = "progettoSet")
    private Set<Dipendente> dipendenteSet = new HashSet<Dipendente>();

    public Progetto() {
    }

    public Progetto(String nome, String cliente, int durataInMesi) {
        this.nome = nome;
        this.cliente = cliente;
        this.durataInMesi = durataInMesi;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public int getDurataInMesi() {
        return durataInMesi;
    }

    public void setDurataInMesi(int durataInMesi) {
        this.durataInMesi = durataInMesi;
    }


    public Set<Dipendente> getDipendenteSet() {
        return dipendenteSet;
    }

    public void setDipendenteSet(Set<Dipendente> dipendenteSet) {
        this.dipendenteSet = dipendenteSet;
    }

    public void addToDipendente(Dipendente dipendente){
        this.dipendenteSet.add(dipendente);
        dipendente.getProgettoSet().add(this);
    }
}
