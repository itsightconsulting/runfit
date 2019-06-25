package com.itsight.repository;

import com.itsight.domain.FacebookUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacebookUserRepository extends JpaRepository<FacebookUser, Integer> {

    FacebookUser findByFacebookId(Integer facebookUserId);
}
