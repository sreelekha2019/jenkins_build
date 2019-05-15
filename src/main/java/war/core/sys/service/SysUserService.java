package war.core.sys.service;


import war.core.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * @Auther: Aaron
 * @Date: 2018/7/10 11:09
 * @Description: 系统用户
 */
public interface SysUserService {

//
	/**
	 * 根据用户ID，查询用户
	 * @param userId
	 * @return
	 */
	SysUserEntity queryObject(Long userId);

	/**
	 * 查询用户列表
	 */
	List<SysUserEntity> queryList(Map<String, Object> map);

	/**
	 * 查询总数
	 */
	int queryTotal(Map<String, Object> map);

	/**
	 * 保存用户
	 */
	void save(SysUserEntity user);
	
	/**
	 * 修改用户
	 */
	void update(SysUserEntity user);

	/**
	 * 删除用户
	 */
	void deleteBatch(Long[] userIds);
//
//	/**
//	 * 修改密码
//	 * @param userId       用户ID
//	 * @param password     原密码
//	 * @param newPassword  新密码
//	 */
//	int updatePassword(Long userId, String password, String newPassword);
}
