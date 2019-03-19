package com.itsight.repository;

import com.itsight.domain.ListaPlantillaSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ListaPlantillaSimpleRepository extends JpaRepository<ListaPlantillaSimple, Long> {

}
