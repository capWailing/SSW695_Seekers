package com.seekers.seekerback.util.database;

import java.util.Map;

//import com.seekers.seekerback.util.database.impl.SearchCondition.Conjunction;

/**
 * 
 * 检索参数构造器
 * 
 * @since jacob @ 2014年6月26日 下午12:30:35
 * 
 */
public interface ISearchBuilder extends Cloneable {

	/**
	 * 
	* @ClassName: Operator
	* @Description: 检索运算符  Match为完全匹配 equal为分词匹配
	* @author yzt
	* @date 2016年8月25日 上午11:49:07
	 */
	public enum Operator {
		Equal, NotEqual, Match, Between, Like, NotLike, GreaterThan, GreaterThanOrEqual, LessThan, LessThanOrEqual
	};

	/**
	 * 
	     * @Description:  将检索条件转换为DSL检索表达式
	     * @param @return
	     * @author yzt
	     * @return String
	     * @throws
	 */
	public String asDSL();

	/**
	 * 添加检索条件,对指定字段过滤
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValue
	 *            字段值
	 * @param op
	 *            检索操作符
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder filterField(String fieldName, String fieldValue, Operator op);

	public ISearchBuilder filterField(String fieldName, String fieldValue, Operator op, boolean escape);

	/**
	 * 添加检索条件,对指定字段过滤
	 * 
	 * @param fieldName
	 *            字段名
	 * @param fieldValues
	 *            字段值
	 * @param op
	 *            检索操作符
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder filterField(String fieldName, String[] fieldValues, Operator op);

	public ISearchBuilder filterField(String fieldName, String[] fieldValues, Operator op, boolean escape);

	public ISearchBuilder filterFieldsByWeight(Map<String, Integer> fieldsWeightMap, String keyword, boolean escape);
	
	/**
	 * 
	     * @Description: 添加“与”和“或”条件的子查询
	     * @param @param fieldName
	     * @param @param fieldValue
	     * @param @param op
	     * @param @param conjunction 值为 “Or”或“And”
	     * @param @return
	     * @author yzt
	     * @return ISearchBuilder
	     * @throws
	 */
	//public ISearchBuilder filterFieldByChild(String fieldName, String fieldValue, Operator op, Conjunction conjunction);
	
	//public ISearchBuilder filterFieldByChild(String fieldName, String[] fieldValues, Operator op, Conjunction conjunction);


	/**
	 * 添加原生DSL检索表达式
	 * 
	 * @param dsl
	 *            dsl检索表达式,必须为合法的dsl检索表达式
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder filterByDSL(String dsl);

	/**
	 * 设置检索排序条件
	 * 
	 * @param field
	 *            排序字段
	 * @param desc
	 *            是否降序排列
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder orderBy(String field, boolean desc);

	/**
	 * 设置检索分页条件,只返回指定页的数据
	 * 
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页长
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder page(long pageNo, int pageSize);

	/**
	 * 设置检索分页条件,只返回指定页的数据
	 * 
	 * @param startPos
	 *            分页开始位置
	 * @param pageNo
	 *            页码
	 * @param pageSize
	 *            页长
	 * @return 当前检索条件构造器
	 */
	public ISearchBuilder page(long startPos, long pageNo, int pageSize);

	/**
	 * 获取分页码
	 * 
	 * @return 分页码
	 */
	public long getPageNo();

	/**
	 * 
	 * 设置分页码
	 * 
	 * @param pageNo
	 *            分页码
	 */
	public void setPageNo(long pageNo);

	/**
	 * 获取分页长
	 * 
	 * @return 分页长
	 * @since huangshengbo @ Mar 5, 2012
	 */
	public int getPageSize();

	/**
	 * 设置分页长
	 * 
	 */
	public void setPageSize(int pageSize);

	/**
	 * 获取分页起始位置
	 * 
	 * @return 分页起始位置
	 */
	public long getStartPos();

	/**
	 * 获取排序表达式
	 * 
	 * @return 排序表达式
	 */
	public String getOrderBy();
	
	/**
	 * 获取元搜索关键词
	 * 
	 * @return 元搜索关键词
	 */
	public String getMetaKeywords();

	/**
	 * 设置元搜索关键词
	 * 
	 */
	public void setMetaKeywords(String metaKeywords);
	/**
	 * 
	     * @Description: 获取高亮显示的字段
	     * @param @return
	     * @author yzt
	     * @return String[]
	     * @throws
	 */
	public String[] getHighLightFields();
	
	/**
	 * 
	     * @Description: 设置高亮显示的字段
	     * @param @param highLightFields
	     * @author yzt
	     * @return void
	     * @throws
	 */
	public void setHighLightFields(String ... highLightFields);
	
	/**
	 * 
	     * @Description: 设置最低匹配度
	     * @param @param minScore
	     * @author yzt
	     * @return void
	     * @throws
	 */
	public void setMinScore(float minScore);
	
	public float getMinScore();


}
