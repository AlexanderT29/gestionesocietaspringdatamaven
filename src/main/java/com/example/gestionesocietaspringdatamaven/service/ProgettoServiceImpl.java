package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;
import com.example.gestionesocietaspringdatamaven.repository.ProgettoRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProgettoServiceImpl implements ProgettoService{

    @Autowired
    private ProgettoRepository progettoRepository;

    private EntityManager entityManager;

    @Override
    public List<Progetto> listAllProgetti() {
        return (List<Progetto>) progettoRepository.findAll();
    }

    @Override
    public Progetto caricaSingoloProgetto(Long id) {
        return progettoRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void inserisciProgetto(Progetto progetto) {
        progettoRepository.save(progetto);
    }

    @Override
    @Transactional
    public void aggiorna(Progetto progetto) {
        progettoRepository.save(progetto);
    }

    @Override
    @Transactional
    public void rimuovi(Long progettoId) {
        Progetto progettoDaEliminare = progettoRepository.findById(progettoId).orElse(null);
        if(progettoDaEliminare == null) throw new RuntimeException("Id inserito non valido!");
        progettoRepository.delete(progettoDaEliminare);
    }

    @Override
    @Transactional
    public void collegaProgettoADipendenti(Progetto progetto, List<Dipendente> dipendenti) {
        if (dipendenti == null || dipendenti.size() == 0){
            throw  new RuntimeException("Non ci sono dipendenti nella lista o la lista è nulla!");
        }
        for (Dipendente dipendente: dipendenti){
            dipendente.addToProgetti(progetto);
        }
        progettoRepository.save(progetto);
    }

    @Override
    public List<Progetto> findDistinctByDipendenteSet_redditoAnnuoLordoGreaterThan(int example) {
        return progettoRepository.findDistinctByDipendenteSet_redditoAnnuoLordoGreaterThan(example);
    }

    @Override
    public List<Progetto> getListaDipendentiConRALMAggioreDi(int example) {
        return progettoRepository.getListaDipendentiConRALMAggioreDi(example);
    }
}
