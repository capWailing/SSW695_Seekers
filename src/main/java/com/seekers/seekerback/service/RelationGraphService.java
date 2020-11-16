package com.seekers.seekerback.service;

import com.seekers.seekerback.util.database.ISearchService;
import com.seekers.seekerback.util.database.impl.SearchServiceImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RelationGraphService {
    public static ByteArrayOutputStream getGraph(String databaseid){
        databaseid = "twitter";
        //Fetch data from database
        java.util.List<Map<String, Object>> text1_json = new ArrayList<>();

        java.util.List<String> text3 = new ArrayList<>();

        ISearchService iSearchService = new SearchServiceImpl("localhost", 9200);
        List<String> result = iSearchService.idQuery(databaseid);
        for (String e : result) {
//            System.out.println(e);
//            System.out.println(iSearchService.get("twitter", e));
            text1_json.add(iSearchService.get(databaseid, e));
        }



        //generate pie chart
        ByteArrayOutputStream fos_png = null;
        DefaultPieDataset dpd = new DefaultPieDataset();
        dpd.setValue("管理人员", 25);
        dpd.setValue("市场人员", 25);
        dpd.setValue("开发人员", 45);
        dpd.setValue("其他人员", 5);
        // 创建主题样式
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");

        // 设置标题字体
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20));

        // 设置图例的字体
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 15));

        // 设置轴向的字体
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 15));

        // 应用主题样式
        ChartFactory.setChartTheme(standardChartTheme);

        // Create JFreeChart object
        // 参数可以查看源码
        JFreeChart pieChart = ChartFactory.createPieChart(
                "Relation Graph", dpd, true, true, false);
        try {
            fos_png = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsPNG(fos_png,pieChart,640,480,null);
            fos_png.close();
        } catch (Exception ignored) {
        }
        return fos_png;
    }
}
