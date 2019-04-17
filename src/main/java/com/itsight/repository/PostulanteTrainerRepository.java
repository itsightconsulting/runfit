package com.itsight.repository;

import com.itsight.domain.PostulanteTrainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostulanteTrainerRepository extends JpaRepository<PostulanteTrainer, Integer> {

    PostulanteTrainer findByCorreo(String correo);
}
