package com.example.gestionesocietaspringdatamaven.repository;

import com.example.gestionesocietaspringdatamaven.model.Dipendente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface DipendenteRepository extends CrudRepository<Dipendente, Long>, QueryByExampleExecutor {
}
