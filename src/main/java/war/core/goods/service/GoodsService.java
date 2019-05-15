package war.core.goods.service;

import war.core.goods.entity.GoodsEntity;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aaron
 * @Date: 2018-07-24 14:38:01
 * @Description: 商品表
 */
public interface GoodsService {
	
	GoodsEntity queryObject(Long goodsId);
	
	List<GoodsEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(GoodsEntity goods);
	
	void update(GoodsEntity goods);
	
	void delete(Long goodsId);
	
	void deleteBatch(Long[] goodsIds);
}
