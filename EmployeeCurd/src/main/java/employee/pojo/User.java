package employee.pojo;

import javax.validation.constraints.Pattern;

public class User {
    private Integer id;

    @Pattern(regexp = "^[A-Za-z0-9_-]{3,16}$",message = "用户名只能由3~16位的英文及数字组合")
    private String username;

    @Pattern(regexp = "^[A-Za-z0-9_-]{6,18}$",message = "密码只能由6~18位英文及数字组合")
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }
}