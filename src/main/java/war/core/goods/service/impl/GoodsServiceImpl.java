package war.core.goods.service.impl;

import war.core.goods.dao.GoodsDao;
import war.core.goods.entity.GoodsEntity;
import war.core.goods.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @Auther: Aaron
 * @Date: 2018-07-24 14:38:01
 * @Description: 商品表
 */

@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
	@Autowired
	private GoodsDao goodsDao;
	
	@Override
	public GoodsEntity queryObject(Long goodsId){
		return goodsDao.queryObject(goodsId);
	}
	
	@Override
	public List<GoodsEntity> queryList(Map<String, Object> map){
		return goodsDao.queryList(map);
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return goodsDao.queryTotal(map);
	}
	
	@Override
    @Transactional
	public void save(GoodsEntity goods){
		goodsDao.save(goods);
	}
	
	@Override
    @Transactional
	public void update(GoodsEntity goods){
		goodsDao.update(goods);
	}
	
	@Override
    @Transactional
	public void delete(Long goodsId){
		goodsDao.delete(goodsId);
	}
	
	@Override
    @Transactional
	public void deleteBatch(Long[] goodsIds){
		goodsDao.deleteBatch(goodsIds);
	}
	
}
