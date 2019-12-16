package employee.controller;

import employee.pojo.Return;
import employee.pojo.User;
import employee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 用户登录
    @ResponseBody
    @RequestMapping(value = "/login")
    public Return userLogin(User user, @RequestParam(value = "checkCode") String checkCode, HttpSession session) {

        String code = (String) session.getAttribute("checkCode");
        System.out.println("\\n\\n\\n ============>" + code);

        // 保证验证码的唯一性
        session.removeAttribute("checkCode");

        // 验证验证码
        if (checkCode != null && checkCode.trim().length() > 0) {

            // 验证用户名
            if (checkCode.equalsIgnoreCase(code)) {
                long l = userService.handleUserLogin(user);
                if (l > 0) {
                    session.setAttribute("username", user.getUsername());
                    return Return.success();
                } else {
                    Return error = Return.error();
                    error.setMsg("用户名或密码有误！");
                    return error;
                }
            } else {
                Return error = Return.error();
                error.setMsg("验证码输入不正确！");
                return error;
            }
        } else {
            Return error = Return.error();
            error.setMsg("验证码输入不正确！");
            return error;
        }
    }

    // 用户是否登录
    @ResponseBody
    @RequestMapping(value = "/isLogin")
    public Return isLogin(HttpSession session) {
        Object username = session.getAttribute("username");
        if (username != null) {
            return Return.success();
        } else {
            Return error = Return.error();
            error.addData("isLogin",session.getAttribute("isLogin") );
            session.removeAttribute("isLogin");
            return error;
        }
    }

    // 用户登出
    @RequestMapping(value = "/userLogout")
    public String userLogout(HttpSession session) {

        session.invalidate();
        return "redirect:/login_bubble.html";
    }

    // 用户修改密码
    @RequestMapping(value = "/userUpdate")
    @ResponseBody
    public Return userUpdate(@Valid User user, @RequestParam(value = "newPassword") String newPassword, BindingResult result) {

        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();
            Return error = Return.error();
            for (FieldError fieldError : fieldErrors) {
                error.addData(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return error;
        }

        int i = userService.handleUpdateUser(user, newPassword);
        if (i <= 0) {
            Return error = Return.error();
            if (i == -1) {
                error.setMsg("账号或密码有误，请重新输入！");
            } else if (i == -2) {
                error.setMsg("服务器发生未知异常，修改失败！");
            }
            return error;
        } else {
            return Return.success();
        }
    }

    // 用户注销
    @RequestMapping(value = "/userDelete")
    @ResponseBody
    public Return userDelete(User user, HttpSession session) {
        int i = userService.handleDeleteUser(user);
        if (i <= 0) {
            Return error = Return.error();
            if (i == -1) {
                error.setMsg("账号或密码有误，请重新输入！");
            } else if (i == -2) {
                error.setMsg("服务器发生未知异常，删除失败！");
            }
            return error;
        } else {
            session.invalidate();
            return Return.success();
        }
    }

    // 验证用户名是否可用
    @RequestMapping(value = "/checkUsername")
    @ResponseBody
    public Return checkUsername(@RequestParam(value = "username") String username) {

        long l = userService.handleCountByUsername(username);
        if (l > 0) {
            Return error = Return.error();
            error.setMsg("用户名已被注册！");
            return error;
        } else {
            return Return.success();
        }
    }


    // 用户注册
    @RequestMapping(value = "/userRegister")
    @ResponseBody
    public Return userRegister(@Valid User user, @RequestParam(value = "checkCode") String checkCode, HttpSession session, BindingResult result) {

        Map<String, Object> map = new HashMap<>();
        // 数据验证
        if (result.hasErrors()) {
            Return error = Return.error();
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            error.addData("errorMsg", map);
            return error;
        }

        String code = (String) session.getAttribute("checkCode");
        System.out.println("\\n\\n\\n ============>" + code);

        // 保证验证码的唯一性
        session.removeAttribute("checkCode");

        // 验证验证码
        if (checkCode != null && checkCode.trim().length() > 0) {

            // 验证用户名
            if (checkCode.equalsIgnoreCase(code)) {
                long l = userService.handleRegisterUser(user);
                if (l > 0) {
                    return Return.success();
                } else {
                    map.put("register_error", "注册失败");
                    return Return.error().addData("errorMsg", map);
                }
            } else {
                map.put("check_code", "验证码有误");
                return Return.error().addData("errorMsg", map);
            }
        } else {
            map.put("check_code", "验证码有误");
            return Return.error().addData("errorMsg", map);
        }
    }
}
