package com.itsight.repository;

import com.itsight.domain.UbPeru;
import com.itsight.domain.dto.UbPeruDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UbPeruRepository extends JpaRepository<UbPeru, String> {

    @Query("SELECT NEW com.itsight.domain.dto.UbPeruDTO(U.dep, U.ub) FROM UbPeru U WHERE U.pro = '00' AND U.dis = '00'")
    List<UbPeruDTO> findDeps();

    @Query("SELECT NEW com.itsight.domain.dto.UbPeruDTO(U.pro, U.ub) FROM UbPeru U WHERE U.dep = '15' AND U.pro <> '00' AND U.dis = '00'")
    List<UbPeruDTO> findProvs();

    @Query("SELECT NEW com.itsight.domain.dto.UbPeruDTO(U.dis, U.ub) FROM UbPeru U WHERE U.dep = '15' AND U.pro = '01' AND U.dis <> '00'")
    List<UbPeruDTO> findDist();

    @Query("SELECT NEW com.itsight.domain.dto.UbPeruDTO(U.pro, U.ub) FROM UbPeru U WHERE U.dep = ?1 AND U.pro <> '00' AND U.dis = '00'")
    List<UbPeruDTO> findProvsByDepId(String depId);

    @Query("SELECT NEW com.itsight.domain.dto.UbPeruDTO(U.dis, U.ub) FROM UbPeru U WHERE U.dep = ?1 AND U.pro = ?2 AND U.dis <> '00'")
    List<UbPeruDTO> findDistByDepIdAndProvId(String depId, String provId);

}
