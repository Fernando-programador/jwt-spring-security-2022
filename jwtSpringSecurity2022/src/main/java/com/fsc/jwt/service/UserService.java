package com.fsc.jwt.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fsc.jwt.Dto.UserDto;
import com.fsc.jwt.exception.ResourceNotFoundException;
import com.fsc.jwt.mapper.DozerMapper;
import com.fsc.jwt.models.User;
import com.fsc.jwt.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService  {

	private Logger logger = Logger.getLogger(UserService.class.getName());

	@Autowired
	private UserRepository userRepository;

	

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("find one user by name" + username + "!!!");
		var user = userRepository.findByUsername(username);
		if (user != null) {

			return user;

		} else {
			throw new UsernameNotFoundException("Username " + username + "not found");
		}
	}

	public UserDto readId(Long id) {
		logger.info("Service findById");

		var entity = userRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("This is id " + id + " no exists in repository"));

		var dto = DozerMapper.parseObject(entity, UserDto.class);

		return dto;
	}
	
	public UserDto create(UserDto userDto) {
		logger.info("Service create");
		userDto.setId(null);

		var entity = DozerMapper.parseObject(userDto, User.class);

		var dto = DozerMapper.parseObject(entity, UserDto.class);

		dto.setId(entity.getId());

		return dto;

	}




	
	
}
