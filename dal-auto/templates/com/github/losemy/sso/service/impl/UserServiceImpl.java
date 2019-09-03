package com.github.losemy.sso.service.impl;

import com.github.losemy.sso.dal.model.UserDO;
import com.github.losemy.sso.dal.dao.UserDAO;
import com.github.losemy.sso.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lose
 * @since 2019-08-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDAO, UserDO> implements UserService {

}
