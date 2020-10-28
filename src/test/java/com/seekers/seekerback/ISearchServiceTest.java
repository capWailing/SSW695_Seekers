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
        mapList = (List<Map<String, Object>>) JSON.parse("[{\"created_at\":\"2020-10-25T23:32:30.000Z\",\"text\":\"201026 #TEN Instagram Update \\n\\n#WayV #WeiShenV #威神V #李永钦 https://t.co/uLcmnCOgn8\"},{\"created_at\":\"2020-10-28T21:42:06.000Z\",\"text\":\"(T/N: this is the MV for \\\"More\\\" by K/DA)\"}]");
        System.out.println(iSearchService.bulkUpsert("twitter", idList, mapList));

        iSearchService.close();
    }
}
