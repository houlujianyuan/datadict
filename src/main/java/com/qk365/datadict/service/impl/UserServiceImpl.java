package com.qk365.datadict.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.qk365.datadict.dao.UserMapper;
import com.qk365.datadict.po.Users;
import com.qk365.datadict.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaoge
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required=true)
    private UserMapper userMapper;
    @Override
  /*  @DS("#dbKey")*/
    public void insertUser(Users users,String dbKey) {
        userMapper.insertSelective(users);
    }

    @Override
   /* @DS("#dbKey")*/
    public Users selectOne(Users users,String dbKey) {
        return userMapper.selectOne(users);
    }

    @Override
/*    @DS("#dbKey")*/
    public List<Users> selectList(Users users,String dbKey) {
        return userMapper.select(users);
    }
}
