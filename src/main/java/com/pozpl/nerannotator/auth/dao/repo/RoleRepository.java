package com.pozpl.nerannotator.auth.dao.repo;

import com.pozpl.nerannotator.auth.dao.model.ERole;
import com.pozpl.nerannotator.auth.dao.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(ERole name);

	@Override
	void delete(Role role);
}
