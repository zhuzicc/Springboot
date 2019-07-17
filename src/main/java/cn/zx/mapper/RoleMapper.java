package cn.zx.mapper;

import cn.zx.model.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaoxiang
 * @date 2019/6/20 23:26
 */
@Repository
@Mapper
public interface RoleMapper {

    List<Role> findRoleByUserId();
}
