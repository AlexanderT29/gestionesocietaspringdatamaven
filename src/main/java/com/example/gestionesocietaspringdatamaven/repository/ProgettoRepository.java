package com.example.gestionesocietaspringdatamaven.repository;

import com.example.gestionesocietaspringdatamaven.model.Progetto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

public interface ProgettoRepository extends CrudRepository<Progetto, Long>, QueryByExampleExecutor<Progetto> {
}
