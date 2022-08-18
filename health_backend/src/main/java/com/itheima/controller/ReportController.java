package com.itheima.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Pzi
 * @create 2022-08-13 14:21
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    @Reference
    private SetmealService setmealService;

    /**
     * 返回去年一年中每个月的会员数量
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        HashMap<String, List> map = null;
        try {
            Calendar calendar = Calendar.getInstance();
            //  获取本月之前的12个月
            calendar.add(Calendar.MONTH, -12);
            ArrayList<String> list = new ArrayList<>();
            map = new HashMap<>();
            //  months membercounts
            //  调用servcie查询每个月的member总数量
            for (int i = 0; i < 12; i++) {
                System.out.println(calendar);
                calendar.add(Calendar.MONTH, 1);
                list.add(new SimpleDateFormat("yyyy.MM").format(calendar.getTime()) + "." + calendar.getActualMaximum(calendar.DATE));
            }
            map.put("months", list);
            List membercountsList = reportService.getMemberReportEvMonth(list);
            map.put("membercounts", membercountsList);
        } catch (Exception e) {
            return new Result(true, e.getMessage());
        }

        return new Result(true, "查询会员数量成功！", map);
    }

    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
//        Map map = reportService.getSetmealReport();
        List<Map<String, Integer>> list = setmealService.selectNameAndCount();
        ArrayList<String> nameList = new ArrayList<>();
        for (Map<String, Integer> m : list) {
            nameList.add(String.valueOf(m.get("name")));
        }
        HashMap<String, List> retMap = new HashMap<>();
        retMap.put("setmealNames", nameList);
        retMap.put("setmealCount", list);
        return new Result(true, "查询所有套餐数据成功！", retMap);
    }

    @RequestMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 导出Excel报表
     *
     * @return
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //远程调用报表服务获取报表数据
            Map<String, Object> result = reportService.getBusinessReportData();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //获得Excel模板文件绝对路径
            HttpSession session = request.getSession();
            session.getServletContext();
            request.getContextPath();

            String temlateRealPath = request.getSession().getServletContext().getRealPath("template") +
                    File.separator + "report_template.xlsx";

            //读取模板文件创建Excel表格对象
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(temlateRealPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for (Map map : hotSetmeal) {//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                String proportion = (String) map.get("proportion");
                row = sheet.getRow(rowNum++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion);//占比
            }

            //通过输出流进行文件下载
            ServletOutputStream out = response.getOutputStream();
            //服务器告诉浏览器它发送的数据属于什么文件类型
            response.setContentType("application/vnd.ms-excel");
            //当Content-Type 的类型为要下载的类型时 , 这个信息头会告诉浏览器这个文件的名字和类型
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            workbook.write(out);

            //  空缓冲区的数据流，强制把缓冲区的数据输出
            out.flush();
            out.close();
            workbook.close();

            return null;
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }

    @GetMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response) {
        try {
            //  使用request拿到PDF模板的位置
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") +
                    File.separator + "health_business3.jrxml";
            String jasperPath =
                    request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jasper";

            Map<String, Object> result = reportService.getBusinessReportData();
            //  jas中field可以遍历 para传的是map
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //  使用jas操作
            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperPath, result,
                            new JRBeanCollectionDataSource(hotSetmeal));

            ServletOutputStream out = response.getOutputStream();

            //  使用response输出到浏览器
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");

            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint, out);

            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }

    }


}

