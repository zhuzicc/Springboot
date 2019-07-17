package cn.zx.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.zx.model.Role;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author zhaoxiang
 * @date 2019/6/19 14:37
 */
public class UserModel  {
    private Integer id;
    private String username;
    private String password;
    private Integer enabled;

    private List<Role> roles;

    public UserModel() {
    }

    public UserModel(Integer id, String username, String password, Integer enabled) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
