package com.pozpl.nerannotator.auth.dao.repo;

import com.pozpl.nerannotator.auth.dao.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);

	Optional<User> findByUsername(String username);

	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);

	@Query("select u FROM User AS u")
	Page<User> list(Pageable pageable);

	@Override
	void delete(User user);
}
