package war.common.utils;

import war.common.xss.SQLFilter;
import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Auther: Aaron
 * @Date: 2018/7/10 11:04
 * @Description: 查询参数
 */
public class Query extends LinkedHashMap<String, Object> {
	private static final long serialVersionUID = 1L;
	//当前页码
    private int page=1;
    //每页条数
    private int limit=10;

    public Query(Map<String, Object> params){

        if(params !=null && params.size()>0){
            this.putAll(params);
            //分页参数
            this.page = Integer.parseInt(params.get("page")==null?"1":params.get("page").toString());
            this.limit = Integer.parseInt(params.get("limit")==null?"1":params.get("limit").toString());
            //防止SQL注入（因为orderValue,order是通过拼接SQL实现排序的，会有SQL注入风险）
            String orderValue = params.get("orderValue")==null?"" :(String)params.get("orderValue");
            String order = params.get("order")==null?"":(String)params.get("order");
            if(StringUtils.isNotBlank(orderValue)){
                this.put("orderValue", SQLFilter.sqlInject(orderValue));
            }

            if(StringUtils.isNotBlank(order)){
                this.put("order", SQLFilter.sqlInject(order));
            }

            this.put("offset", (page - 1) * limit);
            this.put("page", page);
            this.put("limit", limit);

        }



    }


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
