package com.qk365.datadict.service.impl;

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
    public void insertUser(Users users) {
        userMapper.insertSelective(users);
    }

    @Override
    public Users selectOne(Users users) {
        return userMapper.selectOne(users);
    }

    @Override
    public List<Users> selectList(Users users) {
        return userMapper.select(users);
    }
}
