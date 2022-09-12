package br.com.fiap.epictaskapi.factory;

import br.com.fiap.epictaskapi.dto.request.UserSaveForm;
import br.com.fiap.epictaskapi.dto.request.UserUpdateForm;
import br.com.fiap.epictaskapi.dto.response.UserResponse;
import br.com.fiap.epictaskapi.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {

    @Autowired
    private ModelMapper modelMapper;

    public UserResponse convertEntityInResponse(User user){
        return modelMapper.map(user, UserResponse.class);
    }

    public User convertSaveFormInEntity(UserSaveForm userSaveForm){
        return modelMapper.map(userSaveForm, User.class);
    }

    public User convertUpdateFormInEntity(UserUpdateForm userUpdateForm, User user){
        if (userUpdateForm.getName() != null){
            user.setName(userUpdateForm.getName());
        }
        if (userUpdateForm.getEmail() != null){
            user.setEmail(userUpdateForm.getEmail());
        }
        return user;
    }
}
