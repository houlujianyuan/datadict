package com.qk365.datadict.service;

import com.qk365.datadict.po.Users;

import java.util.List;

public interface UserService {


    void insertUser(Users users);
    Users selectOne(Users users);
    List<Users> selectList(Users users);
}
