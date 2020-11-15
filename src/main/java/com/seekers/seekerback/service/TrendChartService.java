package com.seekers.seekerback.service;


import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kennycason.kumo.WordFrequency;
import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class TrendChartService {

    public static CategoryDataset createDataSet() {



        List<Map<String, Object>> text1_json = new ArrayList<>();

        List<String> text3 = new ArrayList<>();

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery("twitter");
        for (String e : result) {
//            System.out.println(e);
//            System.out.println(iSearchService.get("twitter", e));
            text1_json.add(iSearchService.get("twitter", e));
        }


        iSearchService.close();
        //System.out.println(text1_json);

        Map<String, Integer> map = new HashMap<String, Integer>();
        String date = "";
        int temp = 0;

        for (Map<String, Object> e : text1_json) {
            date = e.get("created_at").toString().substring(0,10);
            //System.out.println(e.get("created_at").toString().substring(0,10));

            if (map.get(date) == null) {
                map.put(date, 1);
            } else {
                temp = map.get(date)+1;
                map.put(date, temp);

            }



        }







        // 实例化DefaultCategoryDataset对象
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        // 向数据集合中添加数据
        for (Map.Entry<String, Integer> entry: map.entrySet()) {

            String key = entry.getKey();

            Integer value = entry.getValue();
            dataSet.addValue(value, "tweet", key);


        }

        return dataSet;
    }
    /**
     * 创建JFreeChart对象
     * @return chart
     */
    public static ByteArrayOutputStream createChart() throws IOException {
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); //创建主题样式
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); //设置标题字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); //设置图例的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); //设置轴向的字体
        ChartFactory.setChartTheme(standardChartTheme);//设置主题样式
        // 通过ChartFactory创建JFreeChart
        JFreeChart chart = ChartFactory.createBarChart3D(
                "Tweet Trend Chart", //图表标题
                "Date", //横轴标题
                "tweet count",//纵轴标题
                createDataSet(),//数据集合
                PlotOrientation.VERTICAL, //图表方向
                true,//是否显示图例标识
                false,//是否显示tooltips
                false);//是否支持超链接



        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ChartUtilities.writeChartAsJPEG(outputStream, chart, 600, 400);

        return outputStream;
    }

}