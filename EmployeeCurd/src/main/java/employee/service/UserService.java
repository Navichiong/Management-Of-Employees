package employee.service;

import employee.pojo.User;

public interface UserService {

    long handleUserLogin(User user);

    long handleCountByUsername(String username);

    int handleRegisterUser(User user);

    int handleDeleteUser(User user);

    int handleUpdateUser(User user,String newPassword);

}
