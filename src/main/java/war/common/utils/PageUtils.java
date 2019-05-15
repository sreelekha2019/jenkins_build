package war.common.utils;

import java.io.Serializable;
import java.util.List;


/**
 * @Auther: Aaron
 * @Date: 2018/7/10 10:59
 * @Description: 分页工具类
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	//总记录数
	private int totalCount;
	//每页记录数
	private int pageSize;
	//总页数
	private int totalPage;
	//当前页数
	private int currPage;
	//列表数据
	private List<?> list;

	/**
	 *
	 * 功能描述: 分页
	 *
	 * @param:  * @param list
	 * @param totalCount
	 * @param pageSize
	 * @param currPage
	 * @return:
	 * @auther:
	 * @date:
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {

		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
	}

	/**
	 *
	 * 功能描述: 获取总记录数
	 *
	 * @param:  * @param
	 * @return: int
	 * @auther: Aaron
	 * @date: 2018/7/10 11:00
	 */
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *
	 * 功能描述: 获取每页记录数
	 *
	 * @param:  * @param
	 * @return: int
	 * @auther: Aaron
	 * @date: 2018/7/10 11:01
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 *
	 * 功能描述: 获取总页数
	 *
	 * @param:  * @param
	 * @return: int
	 * @auther: Aaron
	 * @date: 2018/7/10 11:02
	 */
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
	
}
