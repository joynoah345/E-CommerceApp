package org.ecomapp.app.Repository;

import org.ecomapp.app.Dto.UserRequest;
import org.ecomapp.app.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
