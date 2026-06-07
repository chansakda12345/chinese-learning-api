package com.sakda.chineselearning.mapper;

import org.mapstruct.Mapper;

import com.sakda.chineselearning.dto.LoginResponse;
import com.sakda.chineselearning.dto.RegisterResponse;
import com.sakda.chineselearning.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
	RegisterResponse toRegisterResponse(User user);
	
	LoginResponse toLoginResponse(User user);

}
