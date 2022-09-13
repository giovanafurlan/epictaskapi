package br.com.fiap.epictaskapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.epictaskapi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    
    Optional<Role> findByName(String nome);
}
