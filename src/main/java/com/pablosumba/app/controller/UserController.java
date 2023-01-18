package com.pablosumba.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pablosumba.app.entity.User;
import com.pablosumba.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService service;
	
	@PostMapping
	public ResponseEntity<User> create (@RequestBody User user){
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<User> read (@PathVariable(value = "id") Long userId){
		Optional<User> oUser = service.findById(userId);
		
		if (!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(userId);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<User> update (@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
		Optional<User> user = service.findById(userId);
		
		if (!user.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		user.get().setNombre(userDetails.getNombre());
		user.get().setClave(userDetails.getClave());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEstado(userDetails.getEstado());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user.get()));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<User> delete (@PathVariable(value = "id") Long userId){
		if (!service.findById(userId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		service.deleteById(userId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<User> readAll(){
		
		List<User> users = StreamSupport
				.stream(service.findAll().spliterator(), false)
				.collect(Collectors.toList());
		return users;
	}
}
