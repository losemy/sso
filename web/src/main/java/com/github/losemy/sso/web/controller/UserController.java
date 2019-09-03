package com.github.losemy.sso.web.controller;


import com.github.losemy.sso.dal.model.UserDO;
import com.github.losemy.sso.service.UserService;
import com.github.losemy.sso.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDO> getAllUsers(){
        return userService.list();
    }

    @PostMapping("/add")
    public boolean addUser(@RequestBody @Validated(UserDTO.Add.class) UserDTO user){
        return userService.save(user.convert(UserDO.class));
    }

    @PostMapping("update")
    public boolean updateUser(@RequestBody @Validated(UserDTO.Add.class) UserDTO user){
        return userService.updateById(user.convert(UserDO.class));
    }
}

