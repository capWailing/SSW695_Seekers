package com.seekers.seekerback.service;

import java.awt.Font;
import java.io.FileOutputStream;

import org.jfree.chart.*;
import org.jfree.data.general.DefaultPieDataset;

public class RelationGraphServiceExample {
    public RelationGraphServiceExample() {
    }

    public static FileOutputStream main(String[] args) {
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
                "CityInfoPort公司组织架构图", dpd, true, true, false);
        ChartFrame pieFrame = new ChartFrame("CityInfoPort公司组织架构图", pieChart);

        FileOutputStream fos_png = null;
        try {
            fos_png = new FileOutputStream("1.png");
            ChartUtilities.writeChartAsPNG(fos_png,pieChart,640,480,null);
            fos_png.close();
        } catch (Exception ignored) {
        }
        pieFrame.pack();
        pieFrame.setVisible(true);
        return fos_png;
    }
}

//RE: "(?<=^|(?<=[^a-zA-Z0-9-_\\.]))@([A-Za-z]+[A-Za-z0-9-_]+)"
//public class RelationGraphService {
//
//}
