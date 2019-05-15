package war.core.goods.entity;

import java.io.Serializable;
import java.util.Date;


/**
 * @Auther: Aaron
 * @Date: 2018-07-24 14:38:01
 * @Description: 商品表
 */
public class GoodsEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//
	private Long goodsId;
	//货品名字
	private String goodsName;
	//总库存
	private Integer totol;
	//已售
	private Integer sold;
	//剩余
	private Integer surplus;
	//创建时间
	private Date createTime;

	/**
	 * 设置：
	 */
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	/**
	 * 获取：
	 */
	public Long getGoodsId() {
		return goodsId;
	}
	/**
	 * 设置：货品名字
	 */
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	/**
	 * 获取：货品名字
	 */
	public String getGoodsName() {
		return goodsName;
	}
	/**
	 * 设置：总库存
	 */
	public void setTotol(Integer totol) {
		this.totol = totol;
	}
	/**
	 * 获取：总库存
	 */
	public Integer getTotol() {
		return totol;
	}
	/**
	 * 设置：已售
	 */
	public void setSold(Integer sold) {
		this.sold = sold;
	}
	/**
	 * 获取：已售
	 */
	public Integer getSold() {
		return sold;
	}
	/**
	 * 设置：剩余
	 */
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	/**
	 * 获取：剩余
	 */
	public Integer getSurplus() {
		return surplus;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
}
