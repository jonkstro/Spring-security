package com.educandoweb.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.educandoweb.course.entities.User;


@Repository // Opcional, pode botar ou não
public interface UserRepository extends JpaRepository<User, Long> {
    // Buscar usuário pelo email:
    // Tá sendo usado UserDetails pois vai ser usado depois pelo security
    UserDetails findByEmailIgnoreCase(String email);
}
