package com.example.gestionesocietaspringdatamaven.service;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Societa;
import com.example.gestionesocietaspringdatamaven.repository.SocietaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional(readOnly = true)
public class SocietaServiceImpl  implements  SocietaService{

    @Autowired
    private SocietaRepository societaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Societa> listAllSocieta() {
        return (List<Societa>) societaRepository.findAll();
    }

    @Override
    public Societa caricaSingolaSocieta(Long id) {
        return societaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void inserisciSocieta(Societa societaInput) {
        if (societaRepository.findByRagioneSociale(societaInput.getRagioneSociale()) != null){
            throw new RuntimeException("Societa già inserita!");
        }
        societaRepository.save(societaInput);
    }

    @Override
    @Transactional
    public void rimuovi(Long idSocieta) {
        Societa societaDaRImuovere = societaRepository.findById(idSocieta).orElse(null);
        if (societaDaRImuovere == null || societaDaRImuovere.getDipendenti().size() != 0){
            throw new RuntimeException("La società ha dei dipendenti! Impossibile rimuovere!");
        }
        societaRepository.delete(societaDaRImuovere);
    }

    @Override
    @Transactional
    public void aggiorna(Societa societaInput) {
        societaRepository.save(societaInput);
    }

    @Override
    public List<Societa> findByExample(Societa societaInput) {

        Map<String, Object> parameterMap = new HashMap<>();
        List<String> whereClauses = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("select s from Societa s where s.id = s.id");
        if(StringUtils.isNoneEmpty(societaInput.getRagioneSociale())){
            whereClauses.add("s.ragioneSociale like :ragionesociale");
            parameterMap.put("ragionesociale", "%" + societaInput.getRagioneSociale() + "%");
        }

        if(StringUtils.isNoneEmpty(societaInput.getIndirizzo())){
            whereClauses.add("s.indirizzo like :indirizzo");
            parameterMap.put("indirizzo", "%" + societaInput.getIndirizzo() + "%");
        }

        queryBuilder.append(!whereClauses.isEmpty() ? " and " : "");
        queryBuilder.append(StringUtils.join(whereClauses, " and "));
        TypedQuery<Societa> typedQuery = entityManager.createQuery(queryBuilder.toString(), Societa.class);

        for(String key : parameterMap.keySet()){
            typedQuery.setParameter(key, parameterMap.get(key));
        }

        return typedQuery.getResultList();
    }

    @Override
    public void removeConEccezione(Societa societa) {
        societaRepository.delete(societa);
        throw new RuntimeException("Eccezione di prova transazione");
    }

    @Override
    public List<Dipendente> getListaDipendentiDaSocieta(Long societaId) {
        if(societaId == null){
            throw new RuntimeException("Input non valido!");
        }

        Societa societa = societaRepository.findWithDipendentiById(societaId);
        if (societa == null) {
            throw new RuntimeException("Società non trovata con ID: " + societaId);
        }

        return societa.getDipendenti();
    }

    @Override
    public List<Societa> getListaSocietaConProgettiDurataMaggioreUnAnno() {
        return societaRepository.getListSocietaWithDurataProgettoSuperioreAnno();
    }


}
