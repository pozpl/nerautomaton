package com.pozpl.nerannotator.auth.dao.repo;

import com.pozpl.nerannotator.auth.dao.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role findByName(String name);

	@Override
	void delete(Role role);
}
