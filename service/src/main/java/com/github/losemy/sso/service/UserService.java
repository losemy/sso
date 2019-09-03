package com.github.losemy.sso.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.losemy.sso.dal.model.UserDO;
import com.github.losemy.sso.service.dto.UserDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
public interface UserService extends IService<UserDO> {

    /**
     * 查看用户是否存在
     * @param user
     * @return
     */
    UserDO findByNameAndPassword(UserDTO user);
}
