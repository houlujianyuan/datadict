package com.qk365.datadict.service;

import com.qk365.datadict.po.Users;

import java.util.List;

public interface UserService {


    void insertUser(Users users,String dbKey);
    Users selectOne(Users users,String dbKey);
    List<Users> selectList(Users users,String dbKey);
}
