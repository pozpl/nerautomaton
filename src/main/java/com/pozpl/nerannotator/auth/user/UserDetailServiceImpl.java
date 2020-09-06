package com.pozpl.nerannotator.auth.user;

import com.pozpl.nerannotator.auth.dao.model.ERole;
import com.pozpl.nerannotator.auth.dao.repo.RoleRepository;
import com.pozpl.nerannotator.auth.dao.repo.UserRepository;
import com.pozpl.nerannotator.auth.dao.model.Privilege;
import com.pozpl.nerannotator.auth.dao.model.Role;
import com.pozpl.nerannotator.auth.dao.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service("userDetailsService")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserDetailServiceImpl(UserRepository userRepository,
								 RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}

//	@Override
//	public UserDetails loadUserByUsername(String username)
//			throws UsernameNotFoundException {
//
//		final Optional<User> userOpt = userRepository.findByUsername(username);
//		if (! userOpt.isPresent()) {
//			final Optional<Role> roleOpt = roleRepository.findByName(ERole.ROLE_USER);
//			final List<Role> authrities = roleOpt.map(role -> Arrays.asList(role)).orElse(Collections.emptyList());
//			return new org.springframework.security.core.userdetails.User(
//					"", "", true, true, true, true,
//					getAuthorities( authrities ));
//		}
//
//		final User user = userOpt.get();
//
//		return new org.springframework.security.core.userdetails.User(
//				user.getUsername(), user.getPassword(), user.isEnabled(), true, true,
//				true, getAuthorities(user.getRoles()));
//	}

	private Collection<? extends GrantedAuthority> getAuthorities(
			Collection<Role> roles) {

		return getGrantedAuthorities(getPrivileges(roles));
	}

	private List<String> getPrivileges(Collection<Role> roles) {

		final List<String> privileges = new ArrayList<>();
		final List<Privilege> collection = new ArrayList<>();
		for (Role role : roles) {
			collection.addAll(role.getPrivileges());
		}
		for (Privilege item : collection) {
			privileges.add(item.getName());
		}
		return privileges;
	}

	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
		final List<GrantedAuthority> authorities = new ArrayList<>();
		for (String privilege : privileges) {
			authorities.add(new SimpleGrantedAuthority(privilege));
		}
		return authorities;
	}
}