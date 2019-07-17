package cn.zx.mapper;


import cn.zx.model.UserModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * The interface User mapper.
 *
 * @author HelloWood
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return user by username
     */
    UserModel getUserByUsername(@Param("username") String username);

   /* //**
     * Gets user by username and password.
     *
     * @param username the username
     * @param password the password
     * @return the user by username and password
     *//*
    UserModel getUserByUsernameAndPassword(@Param("username") String username,
                                           @Param("password") String password);*/
}
