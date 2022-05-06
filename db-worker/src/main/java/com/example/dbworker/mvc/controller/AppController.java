package com.example.dbworker.mvc.controller;

import com.example.dbworker.mvc.model.User;
import com.example.dbworker.mvc.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {

	private final UserRepository repository;

	@PostMapping(path = "/add-user")
	public void addUser(@RequestParam("firstName") String firstName,
	                    @RequestParam("lastName") String lastName,
	                    @RequestParam("email") String email,
	                    @RequestParam("age") Integer age) {
		var newUser = User.builder()
		                  .firstName(firstName)
		                  .lastName(lastName)
		                  .age(age)
		                  .email(email)
		                  .build();
		repository.save(newUser);
	}

	@GetMapping(path = "/get-user-by-id/{id}")
	public User getUserById(@PathVariable("id") String id) {
		return repository.findById(Long.parseLong(id)).get();
	}

	@PostMapping(path = "/update-by-id/{id}")
	public void updateById(@PathVariable("id") Long id,
	                       @RequestParam("firstName") String firstName,
	                       @RequestParam("lastName") String lastName,
	                       @RequestParam("email") String email,
	                       @RequestParam("age") Integer age) {
		var user = User.builder()
		               .id(id)
		               .firstName(firstName)
		               .lastName(lastName)
		               .age(age)
		               .email(email)
		               .build();
		repository.save(user);
	}

	@GetMapping(path = "/delete/{id}")
	public void deleteById(@PathVariable("id") String id){
		repository.deleteById(Long.parseLong(id));
	}

}
