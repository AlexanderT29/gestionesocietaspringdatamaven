package com.example.gestionesocietaspringdatamaven.repository;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Societa;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

public interface SocietaRepository extends CrudRepository<Societa, Long>, QueryByExampleExecutor<Societa> {

    public Societa findByRagioneSociale(String string);

    @EntityGraph(attributePaths =  {"dipendenti"})
    public Societa findWithDipendentiById(Long societaId);

    @NativeQuery(value = "SELECT DISTINCT s.* FROM societa s " +
            "JOIN dipendente d ON s.id = d.societa_id " +
            "JOIN dipendente_progetto dp ON d.id = dp.dipendente_id " +
            "JOIN progetto p ON dp.progetto_id = p.id " +
            "WHERE p.duratainmesi > 12")
    public List<Societa> getListSocietaWithDurataProgettoSuperioreAnno();

    @Query("SELECT DISTINCT s FROM Societa s JOIN s.dipendenti d WHERE d.dataAssunzione < s.dataFondazione")
    List<Societa> findSocietaConDipendentiAnomali();


}
