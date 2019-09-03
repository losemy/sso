package com.github.losemy.sso.dal.dao;

import com.github.losemy.sso.dal.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lose
 * @since 2019-08-26
 */
@Mapper
public interface UserDAO extends BaseMapper<UserDO> {

    /**
     * 获取user是否存在
     * @param user
     * @return
     */
    UserDO selectByNameAndPassword(UserDO user);
}
