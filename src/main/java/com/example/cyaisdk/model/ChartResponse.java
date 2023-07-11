package com.example.cyaisdk.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ChartResponse implements Serializable {

    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成的分析结论
     */
    private String genResult;


}