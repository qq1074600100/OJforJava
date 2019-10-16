package com.maple.oj.service;

import com.maple.oj.beans.User;
import com.maple.oj.common.PasswordException;
import com.maple.oj.common.UserException;
import com.maple.oj.dao.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private LoginMapper loginDao;

    //判断用户名是否存在
    public boolean userExists(String username) {
        User user = loginDao.selectUserByName(username);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //查找数据库，比对用户名和密码，登录
    public boolean login(String username, String password) throws UserException, PasswordException {
        User user = loginDao.selectUserByName(username);
        if (user == null) {
            throw new UserException("用户名不存在");
        }
        if (!password.equals(user.getPassword())) {
            throw new PasswordException("密码错误");
        }
        return true;
    }

    //给数据库添加一个用户
    public boolean addUser(String username, String password, boolean isManager) {
        loginDao.insertUser(new User(username, password, isManager));
        return true;
    }

    public User getUserByName(String username) {
        return loginDao.selectUserByName(username);
    }
}
