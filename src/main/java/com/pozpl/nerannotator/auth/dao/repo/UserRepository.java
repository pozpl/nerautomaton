package com.pozpl.nerannotator.auth.dao.repo;

import com.pozpl.nerannotator.auth.dao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

	@Override
	void delete(User user);
}
