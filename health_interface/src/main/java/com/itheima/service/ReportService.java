package com.itheima.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Pzi
 * @create 2022-08-13 20:33
 */
public interface ReportService {
    List getMemberReportEvMonth(ArrayList<String> list) throws Exception;

    Map getSetmealReport();

    /**
     * 查询运营数据情况
     * @return
     */
    Map<String, Object> getBusinessReportData();

}
