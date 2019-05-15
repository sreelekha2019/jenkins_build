package war.generation.controller;

import war.common.utils.PageUtils;
import war.common.utils.Query;
import war.common.utils.R;
import war.generation.service.SysGeneratorService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.springframework.stereotype.Controller;

/**
 * @Auther: Aaron
 * @Date: 2018/7/19 14:22
 * @Description: 代码生成器,可用resuful api请求，可用main方法
 */
@RestController
@RequestMapping("/generator")
public class SysGeneratorController {
	@Autowired
	private SysGeneratorService sysGeneratorService;
	

	/**
	 *
	 * 功能描述: 获取可生成代码的数据库表
	 *
	 * @param:  * @param null
	 * @return:
	 * @auther: Aaron
	 * @date: 2018/7/19 14:22
	 */
	@GetMapping("/")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		Query query = new Query(params);
		List<Map<String, Object>> list = sysGeneratorService.queryList(query);
		int total = sysGeneratorService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(list, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);

	}
	

	/**
	 *
	 * 功能描述: 生成代码
	 *
	 * @param:  * @param null
	 * @return:
	 * @auther: Aaron
	 * @date: 2018/7/19 14:21
	 */
//	@RequestMapping("/code")

	@GetMapping("/code")
	public R code(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String[] tableNames = new String[]{};
		String tables = request.getParameter("tables");
		tableNames = tables.split(",");
//		tableNames = JSON.parseArray(tables).toArray(tableNames);
		String mainPath = request.getParameter("mainPath");
		String packageName = request.getParameter("package");
		String moduleName = request.getParameter("moduleName");

		HashMap<String,String> configMap = new HashMap<>();
		if (mainPath !=null && mainPath != "") {
			configMap.put("mainPath",mainPath);
		}
		if (packageName !=null && packageName != "") {
			configMap.put("package",packageName);
		}
		if (moduleName !=null && moduleName != "") {
			configMap.put("moduleName",moduleName);
		}

		byte[] data = sysGeneratorService.generatorCode(tableNames,configMap);

		response.reset();
        response.setHeader("Content-Disposition", "attachment; filename=\"generation.zip\"");
        response.addHeader("Content-Length", "" + data.length);
        response.setContentType("application/octet-stream; charset=UTF-8");

        IOUtils.write(data, response.getOutputStream());
		return R.ok("代码已经生成");
	}




}
