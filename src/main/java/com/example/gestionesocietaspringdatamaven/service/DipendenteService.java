package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public interface DipendenteService {

    public List<Dipendente> listAllDipendeni();

    public Dipendente caricaSingoloDipendente(Long id);

    public void inserisciDipendente(Dipendente dipendente);

    public void aggiorna(Dipendente dipendente);

    public void rimuovi(Long id);

    public void scollegaDipendenteDaSocieta(Long idUtente);

    public void collegaDipendenteAProgetti(Dipendente dipendente, List<Progetto> progetti);

    public void collegaDipendenteAProgetto(Dipendente dipendente, Progetto progetto);

    public Dipendente findAnzianoInSocietaStoricheSuProgettiLunghi(LocalDate dataLimite, int durataMinima);

    public Dipendente findAnzianoInSocietaStoricheSuProgettiLunghiSpring(LocalDate dataLimite, int durataMinima);

}
