package com.salih.todo.model.mapper;

import com.salih.todo.model.dto.UserDto;
import com.salih.todo.model.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class AuthMapper {

    public static UserDto mapTo(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public static User mapTo(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public static User updateUser(User entity,UserDto dto){
        entity.setId(dto.getId());
        entity.setUsername(dto.getUsername());
        entity.setEmail(dto.getEmail());
        return entity;
    }



    public static List<UserDto> mapToList(List<User> dtos){
        if(dtos == null){
            return null;
        }
        return dtos.stream().map(AuthMapper::mapTo).collect(Collectors.toList());
    }
}
