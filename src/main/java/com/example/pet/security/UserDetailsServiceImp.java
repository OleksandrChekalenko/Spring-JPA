package com.example.pet.security;

import com.example.pet.entity.Person;
import com.example.pet.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailsServiceImp implements UserDetailsService {

  @Autowired
  private PersonRepository personRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Person person = personRepository.findByEmail(email).orElseThrow(() ->
        new UsernameNotFoundException("Person doesn`t exist"));
    return SecurityUser.fromPerson(person);
  }
}
