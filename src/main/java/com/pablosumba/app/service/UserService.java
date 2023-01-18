package com.pablosumba.app.service;

import java.awt.print.Pageable;
import java.util.Optional;

import org.springframework.data.domain.Page;
import com.pablosumba.app.entity.User;

public interface UserService {
	public Iterable<User> findAll();
	
	public Page<User> findAll(Pageable pageable);
	
	public Optional<User> findById(Long id);
	
	public User save(User user);
	
	public void deleteById(Long id);

}
