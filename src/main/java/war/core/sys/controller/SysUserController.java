package war.core.sys.controller;

import war.common.utils.PageUtils;
import war.common.utils.Query;
import war.common.utils.R;
import war.common.validator.ValidatorUtils;
import war.common.validator.group.AddGroup;
import war.core.sys.entity.SysUserEntity;
import war.core.sys.service.SysUserService;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Auther: Aaron
 * @Date: 2018/7/10 11:06
 * @Description: 系统用户
 */
@RestController
@RequestMapping("/sys")
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;

	/**
	 *
	 * 功能描述:
	 *
	 * 入参测试{
	 "username":"Aaron",
	 "password":"123456",
	 "deptId":1,
	 "mobile":"13578965466",
	 "email":"3434@qq.com",
	 "salt":"3455",
	 "status":1
	 }
	 * @param:  * @param user
	 * @return: war.common.utils.R
	 * @auther: Aaron
	 * @date: 2018/7/10 11:07
	 */
//	@RequestMapping(value = "/users",method = RequestMethod.POST)
	@PostMapping("/users")
	public R save(@RequestBody SysUserEntity user){

		ValidatorUtils.validateEntity(user, AddGroup.class);
		sysUserService.save(user);
		return R.ok("保存成功");

	}


	/**
	 *
	 * 功能描述: 修改用户
	 *
	 * 入参测试{
	 "userId":2
	 "username":"Test2"
	 }
	 * @param:  * @param user
	 * @return: war.common.utils.R
	 * @auther: Aaron
	 * @date: 2018/7/10 11:07
	 */
//	@RequestMapping(value = "/users",method = RequestMethod.PATCH)
	@PatchMapping("/users")
	public R update(@RequestBody SysUserEntity user){

//		ValidatorUtils.validateEntity(user, UpdateGroup.class);

		user.setCreateUserId(user.getUserId());
		sysUserService.update(user);
		return R.ok("更新成功");
	}


//	@RequestMapping(value = "/users",method = RequestMethod.DELETE)
	@DeleteMapping("/users")
	public R delete(@RequestBody Long[] userIds){
		/**
		 *
		 * 功能描述: 删除用户
		 *
		 * 入参例子：[2,3]
		 * @param:  * @param userIds
		 * @return: war.common.utils.R
		 * @auther: Aaron
		 * @date: 2018/7/10 11:08
		 */
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}

		sysUserService.deleteBatch(userIds);


		return R.ok("删除成功");
	}


	/**
	 *
	 * 功能描述: 获取所有用户列表
	 *
	 * @param:  * @param params
	 * @return: war.common.utils.R
	 * @auther: Aaron
	 * @date: 2018/7/10 11:08
	 */
	@GetMapping("/users")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<SysUserEntity> userList = sysUserService.queryList(query);
		int total = sysUserService.queryTotal(query);

		PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

		return R.ok().put("page", pageUtil);
	}

	/**
	 *
	 * 功能描述: 用户信息
	 *
	 * @param:  * @param userId
	 * @return: war.common.utils.R
	 * @auther: Aaron
	 * @date: 2018/7/10 11:08
	 */
	@GetMapping(value = "/users/{userId}")
	public R info(@PathVariable("userId") Long userId){

		SysUserEntity user = sysUserService.queryObject(userId);
		return R.ok().put("user", user);
	}

}
