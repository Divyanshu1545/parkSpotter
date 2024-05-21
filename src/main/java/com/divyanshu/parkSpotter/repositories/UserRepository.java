package com.divyanshu.parkSpotter.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.divyanshu.parkSpotter.models.User;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByEmail(String email);
  
}
