package com.example.gestionesocietaspringdatamaven.repository;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDate;
import java.util.List;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>, QueryByExampleExecutor {

    @NativeQuery(value = "SELECT DISTINCT d.* FROM dipendente d " +
            "JOIN societa s ON d.societa_id = s.id " +
            "JOIN dipendente_progetto dp ON d.id = dp.dipendente_id " +
            "JOIN progetto p ON dp.progetto_id = p.id " +
            "WHERE s.datafondazione < :dataLimite " +
            "AND p.duratainmesi >= :durataMinima " +
            "ORDER BY d.dataassunzione ASC LIMIT 1")
    public Dipendente findAnzianoInSocietaStoricheSuProgettiLunghi(LocalDate dataLimite, int durataMinima);

    public Dipendente findFirstBySocieta_DataFondazioneBeforeAndProgettoSet_DurataInMesiGreaterThanEqualOrderByDataAssunzioneAsc(LocalDate dataFondazione, int durataInMesi);

}
