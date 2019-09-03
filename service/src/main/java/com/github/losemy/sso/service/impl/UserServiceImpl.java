package com.github.losemy.sso.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.losemy.sso.dal.dao.UserDAO;
import com.github.losemy.sso.dal.model.UserDO;
import com.github.losemy.sso.service.UserService;
import com.github.losemy.sso.service.dto.UserDTO;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDAO, UserDO> implements UserService {

    @Override
    public UserDO findByNameAndPassword(UserDTO user) {
        return baseMapper.selectByNameAndPassword(user.convert(UserDO.class));
    }
}
