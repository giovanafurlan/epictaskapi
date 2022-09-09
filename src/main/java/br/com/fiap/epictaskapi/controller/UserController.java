package br.com.fiap.epictaskapi.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    // Listar todos os usuários 
    @GetMapping
    public Page<User> listAll(@PageableDefault(size = 10) Pageable pageable){
        return userService.listAll(pageable);
    }

    // Detalhes do usuário
    @GetMapping("{id}")
    @Cacheable("user")
    public ResponseEntity<User> show(@PathVariable Long id){
        return ResponseEntity.of( userService.findById(id) );   
    }

    // Cadastrar usuário
    @PostMapping
    @CacheEvict(value="users", allEntries = true)
    public ResponseEntity<User> create(@RequestBody @Valid User user){
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    // Deletar usuário
    @DeleteMapping("{id}")
    @CacheEvict(value="users", allEntries = true)
    public ResponseEntity<Object> destroy(@PathVariable Long id){
        Optional<User> optional = userService.findById(id);

        if (optional.isEmpty()) return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

        userService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }





}
