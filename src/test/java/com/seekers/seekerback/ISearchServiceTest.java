package com.seekers.seekerback;

import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;

import java.util.List;

/**
 * program: seeker-back
 * description: simple test
 * author: Zituo Yan
 * create: 2020-10-24
 **/
public class ISearchServiceTest {
    public static void main(String[] args) {
        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        iSearchService.createDatabase();
        List<String> result = iSearchService.idQuery("twitter");
        for (String e: result){
            System.out.println(iSearchService.get("twitter",e));
        }
    }
}
