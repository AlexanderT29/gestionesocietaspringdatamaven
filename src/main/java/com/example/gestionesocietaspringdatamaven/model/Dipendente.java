package com.example.gestionesocietaspringdatamaven.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "dipendente")
public class Dipendente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome")
    private String nome;
    @Column(name = "cognome")
    private String cognome;
    @Column(name = "dataassunzione")
    private LocalDate dataAssunzione;
    @Column(name = "redditoannuolordo")
    private int redditoAnnuoLordo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "societa_id", nullable = false)
    private Societa societa = null;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    @JoinTable(name = "dipendente_progetto", joinColumns = @JoinColumn(name = "dipendente_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "progetto_id", referencedColumnName = "id"))
    private Set<Progetto> progettoSet = new HashSet<Progetto>();

    public Dipendente() {
    }

    public Dipendente(String nome, String cognome, LocalDate dataAssunzione, int redditoAnnuoLordo, Societa societa) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataAssunzione = dataAssunzione;
        this.redditoAnnuoLordo = redditoAnnuoLordo;
        this.societa = societa;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataAssunzione() {
        return dataAssunzione;
    }

    public void setDataAssunzione(LocalDate dataAssunzione) {
        this.dataAssunzione = dataAssunzione;
    }

    public int getRedditoAnnuoLordo() {
        return redditoAnnuoLordo;
    }

    public void setRedditoAnnuoLordo(int redditoAnnuoLordo) {
        this.redditoAnnuoLordo = redditoAnnuoLordo;
    }

    public Societa getSocieta() {
        return societa;
    }

    public void setSocieta(Societa societa) {
        this.societa = societa;
    }

    public Set<Progetto> getProgettoSet() {
        return progettoSet;
    }

    public void setProgettoSet(Set<Progetto> progettoSet) {
        this.progettoSet = progettoSet;
    }

    public void addToProgetti(Progetto progetto){
        this.progettoSet.add(progetto);
        progetto.getDipendenteSet().add(this);
    }
}
