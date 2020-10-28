package com.seekers.seekerback;

import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;
import com.alibaba.fastjson.*;
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
//        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
//        iSearchService.createDatabase();
//        List<String> result = iSearchService.idQuery("twitter");
//        for (String e: result) {
//            System.out.println(iSearchService.get("twitter", e));
//        }
        JSONObject e = JSONObject.parseObject("{\"created_at\":\"2020-10-25T23:32:30.000Z\",\"id\":\"1320508566822739969\",\"text\":\"201026 #TEN Instagram Update \\n\\n#WayV #WeiShenV #威神V #李永钦 https://t.co/uLcmnCOgn8\"}");
        System.out.println(e.get("id"));
//        iSearchService.close();
    }
}
