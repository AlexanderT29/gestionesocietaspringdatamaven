package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;

import java.util.List;

public interface ProgettoService {

    public List<Progetto> listAllProgetti();

    public Progetto caricaSingoloProgetto(Long id);

    public void inserisciProgetto(Progetto progetto);

    public void aggiorna(Progetto progetto);

    public void rimuovi(Long progettoId);

    public void collegaProgettoADipendenti(Progetto progetto, List<Dipendente> dipendenti);

    public List<Progetto> findDistinctByDipendenteSet_redditoAnnuoLordoGreaterThan(int example);

    public List<Progetto> getListaDipendentiConRALMAggioreDi(int example);

}
