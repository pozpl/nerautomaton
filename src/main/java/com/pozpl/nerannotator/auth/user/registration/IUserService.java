package com.pozpl.nerannotator.auth.user.registration;

import com.pozpl.nerannotator.auth.dao.model.User;

public interface IUserService {

	User registerNewUserAccount(UserDto accountDto);
}
