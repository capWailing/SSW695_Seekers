package com.seekers.seekerback.service;


import java.awt.Font;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;


public class TrendChartService {

    public static CategoryDataset createDataSet() {
        // 实例化DefaultCategoryDataset对象
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        // 向数据集合中添加数据
        dataSet.addValue(500, "Java图书", "J2SE类");
        dataSet.addValue(100, "Java图书", "J2ME类");
        dataSet.addValue(900, "Java图书", "J2EE类");
        return dataSet;
    }
    /**
     * 创建JFreeChart对象
     * @return chart
     */
    public static JFreeChart createChart() {
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN"); //创建主题样式
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 20)); //设置标题字体
        standardChartTheme.setRegularFont(new Font("宋体", Font.PLAIN, 15)); //设置图例的字体
        standardChartTheme.setLargeFont(new Font("宋体", Font.PLAIN, 15)); //设置轴向的字体
        ChartFactory.setChartTheme(standardChartTheme);//设置主题样式
        // 通过ChartFactory创建JFreeChart
        JFreeChart chart = ChartFactory.createBarChart3D(
                "Java图书销量统计", //图表标题
                "Java图书", //横轴标题
                "销量（本）",//纵轴标题
                createDataSet(),//数据集合
                PlotOrientation.VERTICAL, //图表方向
                true,//是否显示图例标识
                false,//是否显示tooltips
                false);//是否支持超链接
        return chart;
    }

}