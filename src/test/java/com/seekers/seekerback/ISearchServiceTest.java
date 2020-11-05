package com.seekers.seekerback;

import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;
import com.alibaba.fastjson.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * program: seeker-back
 * description: simple test
 * author: Zituo Yan
 * create: 2020-10-24
 **/
public class ISearchServiceTest {
    public static void main(String[] args) {
        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
//        iSearchService.createDatabase();
//        List<String> result = iSearchService.idQuery("twitter");
//        for (String e: result) {
//            System.out.println(iSearchService.get("twitter", e));
//        }

        List<String> idList = new ArrayList<>();
        idList.add("1320508566822739969");
        idList.add("1321567946133569536");
        List<Map<String, Object>> mapList = new ArrayList<>();
        mapList = (List<Map<String, Object>>) JSON.parse("[{\"created_at\":\"2020-11-25T23:33:45.000Z\",\"text\":\"@LionessMo\\uD83D\\uDCAF\\uD83D\\uDCAF\\uD83C\\uDF05\\uD83D\\uDEE5\\uD83E\\uDD70\\nbebe \\uD83D\\uDCAF@WayV_official \\uD83D\\uDE0FHehe thank you!! \\uD83D\\uDE0FI just love it so much \\uD83D\\uDE04\\uD83D\\uDE04\\uD83D\\uDE04\\uD83D\\uDE04\"},{\"created_at\":\"2020-10-28T21:42:06.000Z\",\"text\":\"(T/N\\uD83D\\uDE0F\\uD83D\\uDE0F\\uD83D\\uDE0F: this is the MV \\uD83D\\uDD25\\uD83D\\uDD25for \\\"More\\\" by \\uD83D\\uDE33\\uD83D\\uDE33K/DA)\"}]");
        System.out.println(iSearchService.bulkUpsert("twitter", idList, mapList));

        iSearchService.close();
    }
}
