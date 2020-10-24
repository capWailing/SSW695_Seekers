package com.seekers.seekerback.util.database;

import java.util.List;
import java.util.Map;

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
	public boolean createDatabase();

	/**
	 * 
	 * @Description: 获取单篇文章 @param @param database @param @param
	 *               table @param @param id @param @return @param @throws
	 *               SearchException @author yzt @return Map
	 *               <String,Object> @throws
	 */
	public Map<String, Object> get(String database, String id);

	/**
	 * 获取单篇(Json格式)
	 * 
	 * @param database
	 * @param id
	 * @return
	 */
	public String getByJson(String database, String id);

	/**
	 * 获取文章id
	 * 
	 * @param indices
	 * @param mappingType
	 * @param key
	 * @param value
	 * @return
	 */
	public String getId(String indices, String mappingType, String key, String value);

	/**
	 * 查询所有记录的id
	 * 
	 * @param index
	 * @return
	 */
	public List<String> idQuery(String index);

	/**
	 * 
	 * @Description: 删除单篇文章(单机版) @param @param database @param @param
	 *               table @param @param id @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String delete(String database, String id);

	/**
	 * 
	 * @Description: 插入单篇文章(单机版) @param @param database @param @param
	 *               table @param @param id @param @param
	 *               object @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public boolean upsert(String database, String id, Map<String, Object> object);

	/**
	 * 插入单篇文章(单机版,Json格式)
	 * 
	 * @param indices
	 * @param docId
	 * @param updateStr
	 */
	public boolean upsertByJson(String indices, String docId, String updateStr);

	/**
	 * 
	 * @Description: 批量插入多篇文章 @param @param database @param @param
	 *               table @param @param idList @param @param
	 *               objectList @param @return @param @throws
	 *               SearchException @author yzt @return String @throws
	 */
	public String bulkUpsert(String database, List<String> idList, List<Map<String, Object>> objectList);

	/**
	 * 
	 * @Description: 关闭es客户端 @param @author yzt @return void @throws
	 */
	public void close();

}
