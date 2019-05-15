package war.core.goods.controller;

import war.common.utils.PageUtils;
import war.common.utils.Query;
import war.common.utils.R;
import war.core.goods.entity.GoodsEntity;
import war.core.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @Auther: Aaron
 * @Date: 2018-07-24 14:38:01
 * @Description: 商品表
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
	@Autowired
	private GoodsService goodsService;
	
	@GetMapping("/goods")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);

		List<GoodsEntity> goodsList = goodsService.queryList(query);
		int total = goodsService.queryTotal(query);
		
		PageUtils pageUtil = new PageUtils(goodsList, total, query.getLimit(), query.getPage());
		
		return R.ok().put("page", pageUtil);
	}
	
	
	/**
	 * 信息
	 */
	@GetMapping("/goods/{goodsId}")
	public R info(@PathVariable("goodsId") Long goodsId){
		GoodsEntity goods = goodsService.queryObject(goodsId);
		
		return R.ok().put("goods", goods);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/goods")
	public R save(@RequestBody GoodsEntity goods){
		goodsService.save(goods);
		
		return R.ok();
	}
	
	/**
	 * 修改
	 */
	@PatchMapping("/goods")
	public R update(@RequestBody GoodsEntity goods){
		goodsService.update(goods);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@DeleteMapping("/goods")
	public R delete(@RequestBody Long[] goodsIds){
		goodsService.deleteBatch(goodsIds);
		
		return R.ok();
	}
	
}
