package cn.zx.service;

import cn.zx.mapper.PermissionMapper;
import cn.zx.mapper.UserMapper;
import cn.zx.model.Permission;
import cn.zx.model.Role;
import cn.zx.model.UserModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaoxiang
 * @date 2019/6/19 14:27
 */
@Service("userDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private HttpSession session;

    private final static Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    /**
     * 通过用户名加载用户信息，重写该方法用于记住密码后通过cookie登录
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userMapper.getUserByUsername(username);
        if (user != null){
            List<Permission> permissions = permissionMapper.findByAdminUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (Permission permission :permissions){
                if (permission != null && permission.getName() != null){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    //1：此处将权限信息添加到 GrantedAuthority 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        }else{
            throw new UsernameNotFoundException("用户名不存在");
        }
        /*List<SimpleGrantedAuthority> authorities = getAuthority(user.getRoles());
        User user1 = new User(user.getUsername(),user.getPassword(),authorities);*/
    }

    public List<SimpleGrantedAuthority> getAuthority(List<Role> roles){
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        for (Role role:roles){
            list.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println(role.getName());
        }
        return list;
    }

    /**
     * 校验用户信息并将用户信息放在 Session 中
     *
     * @param username
     * @param user
     */
    private void validateUser(String username, UserModel user) {
        if (user == null) {
            logger.error("user {} login failed, username or password is wrong", username);
            throw new BadCredentialsException("Username or password is not correct");
        } else if (user.getEnabled() == 0) {
            logger.error("user {} login failed, this account had expired", username);
            throw new AccountExpiredException("Account had expired");
        }
        // TODO There should add more logic to determine locked, expired and others status

        logger.info("user {} login success", username);
        // 当用户信息有效时放入 Session 中
        session.setAttribute("user", user);
    }

}
