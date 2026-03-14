package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;
import com.example.gestionesocietaspringdatamaven.model.Societa;
import com.example.gestionesocietaspringdatamaven.repository.DipendenteRepository;
import com.example.gestionesocietaspringdatamaven.repository.SocietaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class DipendenteServiceImpl implements DipendenteService {

    @Autowired
    private DipendenteRepository dipendenteRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Dipendente> listAllDipendeni() {
        return (List<Dipendente>) dipendenteRepository.findAll();
    }

    @Override
    public Dipendente caricaSingoloDipendente(Long id) {
        return dipendenteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void inserisciDipendente(Dipendente dipendente) {
        dipendenteRepository.save(dipendente);
    }

    @Override
    @Transactional
    public void aggiorna(Dipendente dipendente) {
        dipendenteRepository.save(dipendente);
    }

    @Override
    @Transactional
    public void rimuovi(Long id) {
        Dipendente dipendenteDaRimuovere = dipendenteRepository.findById(id).orElse(null);
        if(dipendenteDaRimuovere == null){
            throw new RuntimeException("Il dipendente non si trova nella base dati!");
        }
        dipendenteRepository.delete(dipendenteDaRimuovere);
    }

    @Override
    @Transactional
    public void scollegaDipendenteDaSocieta(Long idUtente) {
        Dipendente dipendenteDaScollegare = dipendenteRepository.findById(idUtente).orElse(null);

        dipendenteDaScollegare.setSocieta(null);

        dipendenteRepository.save(dipendenteDaScollegare);
    }



    @Override
    @Transactional
    public void collegaDipendenteAProgetti(Dipendente dipendente, List<Progetto> progetti) {
        Societa societa = dipendente.getSocieta();
        for (Progetto progetto: progetti) {
            if (societa.getDataChiusura() != null && societa.getDataChiusura().isBefore(LocalDate.now().plusMonths(progetto.getDurataInMesi()))) {
                throw new RuntimeException("La data del progetto è superiore alla data chiusura dell'azienda");
            }
            dipendente.addToProgetti(progetto);
        }
        dipendenteRepository.save(dipendente);
    }

    @Override
    @Transactional
    public void collegaDipendenteAProgetto(Dipendente dipendente, Progetto progetto) {
        Societa societa = dipendente.getSocieta();
        if (societa.getDataChiusura() != null && societa.getDataChiusura().isBefore(LocalDate.now().plusMonths(progetto.getDurataInMesi()))) {
            throw new RuntimeException("La data del progetto è superiore alla data chiusura dell'azienda");
        }
        dipendente.addToProgetti(progetto);
        dipendenteRepository.save(dipendente);
    }

    @Override
    public Dipendente findAnzianoInSocietaStoricheSuProgettiLunghi(LocalDate dataLimite, int durataMinima) {
        return dipendenteRepository.findAnzianoInSocietaStoricheSuProgettiLunghi(dataLimite, durataMinima);
    }

    @Override
    public Dipendente findAnzianoInSocietaStoricheSuProgettiLunghiSpring(LocalDate dataLimite, int durataMinima) {
        return dipendenteRepository.findFirstBySocieta_DataFondazioneBeforeAndProgettoSet_DurataInMesiGreaterThanEqualOrderByDataAssunzioneAsc(dataLimite, durataMinima);
    }

    @Override
    @Transactional
    public void collegaDipendenteAProgettoNoCheck(Dipendente dipendente, Progetto progetto) {
        dipendente.addToProgetti(progetto);
        dipendenteRepository.save(dipendente);
    }
}
