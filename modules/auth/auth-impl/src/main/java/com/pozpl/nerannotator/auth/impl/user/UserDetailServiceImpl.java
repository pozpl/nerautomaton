package com.pozpl.nerannotator.auth.impl.user;

import com.pozpl.neraannotator.user.api.IUserService;
import com.pozpl.neraannotator.user.api.UserIntDto;
import com.pozpl.nerannotator.shared.exceptions.NerServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("userDetailsService")
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

	private final IUserService userService;

	@Autowired
	public UserDetailServiceImpl(IUserService userService) {
		this.userService = userService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserIntDto user;
		try {
			user = userService.findByUserName(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

			return UserDetailsImpl.build(user);
		} catch (NerServiceException e) {
			throw new UsernameNotFoundException(e.getMessage());
		}
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

//	private Collection<? extends GrantedAuthority> getAuthorities(Collection<ERole> roles) {
//
//		return getGrantedAuthorities(getPrivileges(roles));
//	}
//
//	private List<String> getPrivileges(Collection<ERole> roles) {
//
//		final List<String> privileges = new ArrayList<>();
//		final List<Privilege> collection = new ArrayList<>();
//		for (Role role : roles) {
//			collection.addAll(role.getPrivileges());
//		}
//		for (Privilege item : collection) {
//			privileges.add(item.getName());
//		}
//		return privileges;
//	}
//
//	private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
//		final List<GrantedAuthority> authorities = new ArrayList<>();
//		for (String privilege : privileges) {
//			authorities.add(new SimpleGrantedAuthority(privilege));
//		}
//		return authorities;
//	}
}