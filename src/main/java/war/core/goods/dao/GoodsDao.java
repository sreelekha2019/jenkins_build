package war.core.goods.dao;

import war.common.base.BaseDao;
import war.core.goods.entity.GoodsEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Auther: Aaron
 * @Date: 2018-07-24 14:38:01
 * @Description: 商品表
 */
@Mapper
public interface GoodsDao extends BaseDao<GoodsEntity> {
	
}
