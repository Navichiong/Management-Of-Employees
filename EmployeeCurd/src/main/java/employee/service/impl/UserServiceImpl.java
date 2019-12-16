package employee.service.impl;


import employee.dao.UserMapper;
import employee.pojo.User;
import employee.pojo.UserExample;
import employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public long handleUserLogin(User user) {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(user.getUsername());
        criteria.andPasswordEqualTo(user.getPassword());
        return userMapper.countByExample(example);
    }

    @Override
    public long handleCountByUsername(String username) {

        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        return userMapper.countByExample(example);
    }


    @Override
    public int handleRegisterUser(User user) {

        return userMapper.insertSelective(user);
    }

    @Override
    public int handleDeleteUser(User user) {
        long l = this.handleUserLogin(user);
        if (l <= 0) {
            return -1;
        } else {
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            criteria.andPasswordEqualTo(user.getPassword());
            int i = userMapper.deleteByExample(example);
            if (i <= 0) {
                return -2;
            } else {
                return 1;
            }
        }
    }

    @Override
    public int handleUpdateUser(User user, String newPassword) {
        long l = this.handleUserLogin(user);
        if (l <= 0) {
            // 代表原来的账号密码有误
            return -1;
        } else {
            user.setPassword(newPassword);
            UserExample example = new UserExample();
            UserExample.Criteria criteria = example.createCriteria();
            criteria.andUsernameEqualTo(user.getUsername());
            int i = userMapper.updateByExampleSelective(user, example);
            if (i <= 0) {
                // 代表发生某些异常
                return -2;
            } else {
                return i;
            }
        }
    }
}
