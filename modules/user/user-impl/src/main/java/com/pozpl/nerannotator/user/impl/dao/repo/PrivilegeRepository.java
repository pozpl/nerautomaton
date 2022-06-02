package com.pozpl.nerannotator.user.impl.dao.repo;

import auth.dao.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

	Optional<Privilege> findByName(String name);

	@Override
	void delete(Privilege privilege);
}
