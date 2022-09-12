package br.com.fiap.epictaskapi.service;

import java.util.Optional;

import br.com.fiap.epictaskapi.dto.request.UserSaveForm;
import br.com.fiap.epictaskapi.dto.request.UserUpdateForm;
import br.com.fiap.epictaskapi.dto.response.UserResponse;
import br.com.fiap.epictaskapi.exception.custom.ResourceNotFoundException;
import br.com.fiap.epictaskapi.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.fiap.epictaskapi.model.User;
import br.com.fiap.epictaskapi.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserFactory userFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Page<UserResponse> listAll(Pageable pageable) {
        Page<User> pageUser = userRepository.findAll(pageable);
        Page<UserResponse> pageUserResponse = pageUser.map(user -> userFactory.convertEntityInResponse(user));
        return pageUserResponse;
    }

    public UserResponse save(UserSaveForm userSaveForm) {
        User user = userFactory.convertSaveFormInEntity(userSaveForm);
        user.setPassword(passwordEncoder.encode(userSaveForm.getSenha()));
        user = userRepository.save(user);
        UserResponse userResponse = userFactory.convertEntityInResponse(user);
        return userResponse;
    }

    public UserResponse findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("USER NOT FOUND EXCEPTION");
        }
        return userFactory.convertEntityInResponse(optionalUser.get());
    }

    public void deleteById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("USER NOT FOUND EXCEPTION");
        }
        userRepository.deleteById(id);
    }

    public UserResponse update(UserUpdateForm userUpdateForm, Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()){
            throw new ResourceNotFoundException("USER NOT FOUND EXCEPTION");
        }
        User userBancoDeDados = optionalUser.get();
        User userAtualizado = userFactory.convertUpdateFormInEntity(userUpdateForm, userBancoDeDados);
        userAtualizado = userRepository.save(userAtualizado);
        UserResponse userResponse = userFactory.convertEntityInResponse(userAtualizado);
        return userResponse;
    }
}
