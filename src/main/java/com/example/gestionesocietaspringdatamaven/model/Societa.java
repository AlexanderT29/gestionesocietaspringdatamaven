package com.example.gestionesocietaspringdatamaven.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "societa")
public class Societa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "ragionesociale")
    private String ragioneSociale;
    @Column(name = "indirizzo")
    private String indirizzo;

    @CreationTimestamp
    @Column(name = "datafondazione")
    private LocalDate dataFondazione;

    @Column(name = "datachiusura")
    private LocalDate dataChiusura;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "societa")
    private List<Dipendente> dipendenti = new ArrayList<Dipendente>();


    public Societa() {
    }

    public Societa(String ragioneSociale, String indirizzo, LocalDate dataFondazione, LocalDate dataChiusura) {
        this.ragioneSociale = ragioneSociale;
        this.indirizzo = indirizzo;
        this.dataFondazione = dataFondazione;
        this.dataChiusura = dataChiusura;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRagioneSociale() {
        return ragioneSociale;
    }

    public void setRagioneSociale(String ragioneSociale) {
        this.ragioneSociale = ragioneSociale;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public LocalDate getDataFondazione() {
        return dataFondazione;
    }

    public void setDataFondazione(LocalDate dataFondazione) {
        this.dataFondazione = dataFondazione;
    }

    public LocalDate getDataChiusura() {
        return dataChiusura;
    }

    public void setDataChiusura(LocalDate dataChiusura) {
        this.dataChiusura = dataChiusura;
    }

    public List<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(List<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }


}
