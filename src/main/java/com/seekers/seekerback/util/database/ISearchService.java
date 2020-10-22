package com.seekers.seekerback.util.database;

import java.util.List;
import java.util.Map;


import com.seekers.seekerback.entity.PagedList;
import com.seekers.seekerback.exception.SearchException;

/**
 * 
 * @ClassName: ISearchService
 * @Description: 全文检索接口
 * @author yzt
 * @date 2016年8月25日 上午11:46:07
 */
public interface ISearchService {
	/**
	 * 
	 * @Description: 新建database @param @param
	 *               databaseName @param @return @author yzt @return
	 *               boolean @throws
	 */
	public boolean createDatabase(String databaseName);

	/**
	 * 
	 * @Description: 新建table @param @param database @param @param mappStr
	 *               mapping 映射关系 json格式 @param @param
	 *               table @param @return @author yzt @return boolean @throws
	 */
	public boolean createTable(String database, String table, String mappStr);

	/**
	 * 
	 * @Description: 获取单篇文章 @param @param database @param @param
	 *               table @param @param id @param @return @param @throws
	 *               SearchException @author yzt @return Map
	 *               <String,Object> @throws
	 */
	public Map<String, Object> get(String database, String table, String id) throws SearchException;

	/**
	 * 获取单篇(Json格式)
	 * 
	 * @param database
	 * @param table
	 * @param id
	 * @return
	 */
	public String getByJson(String database, String table, String id);

	/**
	 * 获取文章id
	 * 
	 * @param indices
	 * @param mappingType
	 * @param key
	 * @param value
	 * @return
	 */
	public String getId(String indices, String mappingType, String key, String value) throws SearchException;

	/**
	 * 查询所有记录的id
	 * 
	 * @param index
	 * @param type
	 * @return
	 */
	public List<String> idQuery(String index, String type);

	/**
	 * 
	 * @Description: 删除单篇文章(单机版) @param @param database @param @param
	 *               table @param @param id @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String delete(String database, String table, String id) throws SearchException;

	/**
	 * 
	 * @Description: 插入单篇文章(单机版) @param @param database @param @param
	 *               table @param @param id @param @param
	 *               object @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String upsert(String database, String table, String id, Map<String, Object> object) throws SearchException;

	/**
	 * 插入单篇文章(单机版,Json格式)
	 * 
	 * @param indices
	 * @param mappingType
	 * @param docId
	 * @param updateStr
	 * @throws SearchException
	 */
	public String upsertByJson(String indices, String mappingType, String docId, String updateStr)
			throws SearchException;

	/**
	 * 
	 * @Description: 批量插入多篇文章 @param @param database @param @param
	 *               table @param @param idList @param @param
	 *               objectList @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String bulkUpsert(String database, String table, List<String> idList, List<Map<String, Object>> objectList)
			throws SearchException;

	/**
	 * 
	 * @Description: 批量插入多篇文章 @param @param index @param @param
	 *               type @param @param objectList @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String bulkUpsert(String index, String type, List<Object> objectList) throws SearchException;

	/**
	 * 
	 * @Description: 修改单篇文章(单机版) @param @param database @param @param
	 *               table @param @param id @param @param
	 *               object @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String update(String database, String table, String id, Map<String, Object> object) throws SearchException;

	/**
	 * 
	 * @Description: 批量修改多篇文章 @param @param database @param @param
	 *               table @param @param idList @param @param
	 *               objectList @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String bulkUpdate(String database, String table, List<String> idList, List<Map<String, Object>> objectList)
			throws SearchException;

	/**
	 * 
	 * @Description: 分页获取指定条件的实体 @param @param databases @param @param
	 *               tables @param @param
	 *               searchBuilder @param @return @param @throws
	 *               SearchException @author yzt @return PagedList<Map
	 *               <String,Object>> @throws
	 */
	public PagedList pageList(String databases, String tables, ISearchBuilder searchBuilder) throws SearchException;

	public PagedList pageListQuick(String databases, String tables, String dsl, long startPos, long pageNo,
			int pageSize, String orderBy, String[] fields, float minScore) throws SearchException;

	/**
	 * 计算满足指定检索条件的实体数
	 * 
	 * @param databases
	 * @param tables
	 * @param builder
	 *            实体检索条件
	 * @return 满足条件的实体数
	 * @throws SearchException
	 *             检索失败时
	 */
	public long count(String databases, String tables, ISearchBuilder builder) throws SearchException;

	/**
	 * 
	 * 计算满足指定检索条件的实体数
	 * 
	 * @param databases
	 * @param tables
	 * @param dsl
	 *            检索条件
	 * @return 满足条件的实体数
	 * @throws SearchException
	 *             检索失败时
	 */
	public long count(String databases, String tables, String dsl, float minScore) throws SearchException;

	/**
	 * 
	 * @Description: 关闭es客户端 @param @author yzt @return void @throws
	 */
	public void close();

}
