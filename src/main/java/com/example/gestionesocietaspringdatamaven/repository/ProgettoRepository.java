package com.example.gestionesocietaspringdatamaven.repository;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import com.example.gestionesocietaspringdatamaven.model.Progetto;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.time.LocalDate;
import java.util.List;

public interface ProgettoRepository extends CrudRepository<Progetto, Long>, QueryByExampleExecutor<Progetto> {


    public List<Progetto> findDistinctByDipendenteSet_redditoAnnuoLordoGreaterThan(int example);

    @NativeQuery(value = "SELECT DISTINCT p.* " +
            "FROM progetto p " +
            "JOIN dipendente_progetto dp ON p.id = dp.progetto_id " +
            "JOIN dipendente d ON dp.dipendente_id = d.id " +
            "WHERE d.redditoannuolordo >= :example")
    public List<Progetto> getListaDipendentiConRALMAggioreDi(int example);

    public List<Progetto> findDistinctByDipendenteSet_Societa_DataChiusuraIsNotNull();
}
