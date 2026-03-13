package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Societa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BatteriaDiTestService {

    @Autowired
    private SocietaService societaService;

    public void testInserisceSocieta(){
        System.out.println("testInserisciSocieta .... inizio");
        Long nowInMillisecondi = LocalDate.now().toEpochDay();

        Societa societa = new Societa("Societa" + nowInMillisecondi, "via " + nowInMillisecondi, LocalDate.now(), LocalDate.of(2050, 1, 1));
        if(societa.getId() != null){
            throw new RuntimeException("testInserisciSocieta failed: transient object con id valorizzato");
        }
        societaService.inserisciSocieta(societa);
        if(societa.getId() == null || societa.getId() < 1){
            throw new RuntimeException("testInserisciSocieta failed: inserimento fallito");
        }
        System.out.println("testInserisciSocieta .... OK");
    }

    public void testFindByExample(){
        System.out.println("testFindByExample ... inizio");
        Societa societa1 = new Societa("VGroup", "via lagopatria ì42", LocalDate.of(2021, 11, 20), null);
        societaService.inserisciSocieta(societa1);
        Societa societa2 = new Societa("Gigi Group", "via gomma 43", LocalDate.of(2017, 10, 19), LocalDate.of(2020, 5, 20));
        societaService.inserisciSocieta(societa2);
        Societa societa3 = new Societa("Gattini Group", "via fusa 29", LocalDate.of(2000, 10, 5), LocalDate.of(2024, 4, 12));
        societaService.inserisciSocieta(societa3);
        Societa example = new Societa("Group", null, null, null);
        List<Societa> result = societaService.findByExample(example);
        if(result.size() != 3){
            throw new RuntimeException("testFindByExample failed: societa non trovate");
        }

        System.out.println("testFindByExample ... OK");
    }
}
