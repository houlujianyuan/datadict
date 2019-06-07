package com.qk365.datadict.dao;

import com.qk365.datadict.po.Users;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author zhaoge
 */
public interface UserMapper extends Mapper<Users> {

   /* @Insert("INSERT INTO users(username, password) VALUES(#{username}, #{password})")
    //@SelectKey(statement = "SELECT seq id FROM datadict.sqlite WHERE (name = 'users')", before = false, keyProperty = "id", resultType = int.class)
    int insert(Users users);*/


}
