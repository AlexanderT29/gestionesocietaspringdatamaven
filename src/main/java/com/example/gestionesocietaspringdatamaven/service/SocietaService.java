package com.example.gestionesocietaspringdatamaven.service;


import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Societa;

import java.time.LocalDate;
import java.util.List;

public interface SocietaService {

    public List<Societa> listAllSocieta();

    public Societa caricaSingolaSocieta(Long id);

    public void inserisciSocieta(Societa societaInput);

    public void aggiorna(Societa societaInput);

    public void rimuovi(Long idSocieta);

    public List<Societa> findByExample(Societa societaInput);

    public void removeConEccezione(Societa societa);

    public List<Dipendente> getListaDipendentiDaSocieta(Long societaId);

    public List<Societa> getListaSocietaConProgettiDurataMaggioreUnAnno();

    List<Societa> cercaSocietaAnomale();

    }
