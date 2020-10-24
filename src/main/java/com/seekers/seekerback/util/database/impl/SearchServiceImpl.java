package com.seekers.seekerback.util.database.impl;


import com.seekers.seekerback.util.database.ISearchService;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author yzt
 * @ClassName: SearchServiceImpl
 * @Description: elasticsearch
 */

public class SearchServiceImpl implements ISearchService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchServiceImpl.class);
    private RestHighLevelClient client;

    /**
     * @Description: create a client for elasticsearch
     * @Param: [host, port]
     * @return:
     * @Author: Zituo Yan
     */
    public SearchServiceImpl(String host, Integer port) {
        // es client
        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port))
        );
    }

    public Map<String, Object> get(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        GetResponse response;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
            return response.getSourceAsMap();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public String getByJson(String index, String id) {
        GetRequest request = new GetRequest(index, id);
        GetResponse response;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
            return response.getSourceAsString();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public String getId(String index, String type, String key, String value) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(index);
        MatchQueryBuilder match = QueryBuilders.matchQuery(key, value);
        sourceBuilder.query(match);
        searchRequest.source(sourceBuilder);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            if (hits == null || hits.length == 0) {
                return null;
            } else {
                return hits[0].getId();
            }
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return null;
    }

    public List<String> idQuery(String indices) {
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(indices);
        QueryBuilder match = QueryBuilders.matchAllQuery();
        sourceBuilder.query(match);
        searchRequest.source(sourceBuilder);
        SearchResponse response = null;
        try {
            response = client.search(searchRequest, RequestOptions.DEFAULT);
            SearchHit[] hits = response.getHits().getHits();
            int count = hits.length;
            List<String> list = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                list.add(hits[i].getId());
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String delete(String index, String id) {
        DeleteRequest request = new DeleteRequest(index, id);
        DeleteResponse deleteResponse = null;
        try {
            deleteResponse = client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return deleteResponse.getId();
    }

    public boolean createDatabase() {
        CreateIndexRequest request = new CreateIndexRequest("Seekers");
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );
        request.mapping(
                "{\n" +
                        "  \"properties\": {\n" +
                        "    \"id\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "    }," +
                        "      \"text\": {\n" +
                        "      \"type\": \"text\"\n" +
                        "      }\n" +
                        "  }\n" +
                        "}",
                XContentType.JSON);
        try {
            CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
            return createIndexResponse.isAcknowledged();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return false;
    }

    public boolean upsert(String database, String id, Map<String, Object> object) {
        UpdateRequest request = new UpdateRequest(database, id).doc(object);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            return true;
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            return true;
        } else {
            return false;
        }
    }


    public boolean upsertByJson(String database, String id, String mappStr) {
        UpdateRequest request = new UpdateRequest(database, id).doc(mappStr, XContentType.JSON);
        UpdateResponse updateResponse = null;
        try {
            updateResponse = client.update(request, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
                return true;
            } else return updateResponse.getResult() == DocWriteResponse.Result.UPDATED;
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
        return false;
    }

    public String bulkUpsert(String database, List<String> idList, List<Map<String, Object>> objectList) {
        if (idList.isEmpty() || objectList.size() != idList.size()) {
            return null;
        }

        BulkRequest bulkRequest = new BulkRequest();
        for (int i = 0; i < idList.size(); i++) {
            Map<String, Object> itemMap = objectList.get(i);

            bulkRequest.add(new UpdateRequest(database, idList.get(i)).doc(itemMap));
        }
        BulkResponse bulkResponse = null;
        try {
            bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            if (bulkResponse.hasFailures()) {
                return bulkResponse.buildFailureMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";

    }

    public void close() {
        try {
            client.close();
        } catch (IOException e) {
            LOGGER.info(e.getMessage());
        }
    }

}
