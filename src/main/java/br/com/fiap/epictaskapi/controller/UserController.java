package br.com.fiap.epictaskapi.controller;

import javax.validation.Valid;

import br.com.fiap.epictaskapi.dto.request.UserSaveForm;
import br.com.fiap.epictaskapi.dto.request.UserUpdateForm;
import br.com.fiap.epictaskapi.dto.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {
    
    @Autowired
    private UserService userService;

    // Listar todos os usuários 
    @GetMapping
    public ResponseEntity<Page<UserResponse>> listAll(@PageableDefault(size = 10) Pageable pageable){
        Page<UserResponse> pageUserResponse = userService.listAll(pageable);
        return ResponseEntity.ok().body(pageUserResponse);
    }

    // Detalhes do usuário
    @GetMapping("{id}")
    @Cacheable("user")
    public ResponseEntity<UserResponse> show(@PathVariable Long id){
        UserResponse userResponse = userService.findById(id);
        return ResponseEntity.ok().body(userResponse);
    }

    // Cadastrar usuário
    @PostMapping
    @CacheEvict(value="users", allEntries = true)
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserSaveForm userSaveForm){
        UserResponse userResponse = userService.save(userSaveForm);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    // Deletar usuário
    @DeleteMapping("{id}")
    @CacheEvict(value="users", allEntries = true)
    public ResponseEntity<?> destroy(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @RequestBody UserUpdateForm userUpdateForm){
        UserResponse userResponse = userService.update(userUpdateForm, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }
}
