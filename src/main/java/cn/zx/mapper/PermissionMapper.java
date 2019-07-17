package cn.zx.mapper;

import cn.zx.model.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhaoxiang
 * @date 2019/6/21 10:02
 */
@Repository
public interface PermissionMapper {

    List<Permission> findAll();

    List<Permission> findByAdminUserId(int userId);
}
