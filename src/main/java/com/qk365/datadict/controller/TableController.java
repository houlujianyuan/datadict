package com.qk365.datadict.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qk365.datadict.dto.*;
import com.qk365.datadict.po.TableInfo;
import com.qk365.datadict.service.CommonService;
import com.qk365.datadict.service.DataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 6154876
 */
@Controller
@RequestMapping("/api")
public class TableController {


    @Autowired
    private CommonService commonService;
    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 加载表信息
     *
     * @param model
     * @return
     */
    @GetMapping("/left")
    String left(Model model, HttpServletRequest request) {
        String dbKey = request.getParameter("dbKey");
        model.addAttribute("list", dataSourceService.left(dbKey));
        model.addAttribute("dbKey", dbKey);
        return "main/left";
    }

    /**
     * 加载列信息
     *
     * @param tablename
     * @param model
     * @return
     */
    @GetMapping("/table/{tablename}/{id}")
    String table(@PathVariable("tablename") String tablename,
                 @PathVariable("id") String id,
                 Model model, String dbKey, HttpServletRequest request) {
        String explain = "请输入表说明";

            List<Map<String, Object>> tableList = dataSourceService.findTableName(dbKey, tablename);
            if (tablename.equals("1") && id.equals("1")) {
                tablename = tableList.get(0).get("name") + "";
                id = tableList.get(0).get("id") + "";
                explain = tableList.get(0).get("comment") + "";
            } else {
                for (Map<String, Object> map : tableList) {
                    if (map.get("name").equals(tablename)) {
                        if (map.get("comment") != null && !map.get("comment").equals("")) {
                            explain = map.get("comment") + "";
                        }
                    }
                }
            }

        List<TableInfo>    list = dataSourceService.findTableInfo(tablename, dbKey);


        model.addAttribute("list", list);

        model.addAttribute("tablename", tablename);

        model.addAttribute("explain", explain);

        model.addAttribute("id", id);
        model.addAttribute("dbKey", dbKey);
        return "main/table";
    }

    /**
     * 新增或修改表说明
     *
     * @param tableName
     * @param explain
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/editTableExplain")
    public ResultVO editTableExplain(@RequestParam("tableName") String tableName,
                                     @RequestParam("explain") String explain,
                                     @RequestParam("oldVal") String oldVal, HttpServletRequest request
    ) {

        String dbKey = request.getParameter("dbKey");
        dataSourceService.editTableExplain(tableName,explain,oldVal,dbKey);
        ResultVO resultVo = new ResultVO(0, "成功", "");
        return resultVo;
    }

    /**
     * 新增或修改列说明
     *
     * @param tableName
     * @param explain
     * @param columnName
     * @return
     */
    @ResponseBody
    @PostMapping("/editColumnExplain")
    public ResultVO editColumnExplain(@RequestParam("tableName") String tableName,
                                      @RequestParam("explain") String explain,
                                      @RequestParam("columnName") String columnName,
                                      @RequestParam("oldVal") String oldVal,
                                      @RequestParam("dbKey") String dbKey, HttpServletRequest request) {



        dataSourceService.editColumnExplain(tableName,explain,columnName,oldVal,dbKey);

        ResultVO resultVo = new ResultVO(0, "成功", "");
        return resultVo;
    }


    @GetMapping("/list/{tableName}")
    String list(@PathVariable("tableName") String tableName, Model model) {

        model.addAttribute("tableName", tableName);

        QueryInfoDto query = new QueryInfoDto();
        query.setTableName(tableName);

        List<Map<String, Object>> list = commonService.findList(query, "");

        Map<String, Object> obj = null;

        if (list != null && list.size() > 0) {
            obj = list.get(0);
        }

        List<String> head = new ArrayList<String>();
        for (Map.Entry<String, Object> entry : obj.entrySet()) {
            head.add(entry.getKey());
        }
        //1.表头

        //2.sql

        //3.表格样式

        //4.查询名称

        //5.样式type

        //6.引用js
        model.addAttribute("head", head);
        model.addAttribute("list", list);
        return "list";
    }


    @GetMapping("/initList")
    String initList(@RequestParam("id") String id, @RequestParam("tableName") String tableName, Model model
            , @RequestParam("dbKey") String dbKey, HttpServletRequest request) {
        List<TableInfo> list = dataSourceService.findTableInfo(tableName, dbKey);
        JSONObject json = null;
        JSONArray array = new JSONArray();
        for (TableInfo table : list) {
            json = new JSONObject();
            json.put("field", table.getColumnName());
            json.put("title", table.getColumnName());
            json.put("width", 100);
            array.add(json);
        }
        System.out.println(array.toJSONString());
        model.addAttribute("cols", array.toJSONString());
        model.addAttribute("tableName", tableName);
        /*  model.addAttribute("list", list);*/
        model.addAttribute("dbKey", dbKey);
        return "main/initList";
    }

    @ResponseBody
    @PostMapping("/queryAjaxList")
    public Object applyAjaxList(QueryInfoDto query, HttpServletRequest request) {
        String dbKey = request.getParameter("dbKey");
        try {
//			List<Map<String,Object>>  list =commonService.findList(query);
            ResultVO<Paging<Map<String, Object>>> result = commonService.queryAjaxList(query, dbKey);
            return setResponsePageVo(result);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public JSONObject setResponsePageVo(ResultVO<Paging<Map<String, Object>>> result) {
        JSONObject json = new JSONObject();
        json.put("code", 200);
        json.put("totals", result.getData().getTotalCount());
        json.put("list", result.getData().getItemList());
        return json;
    }

    @ResponseBody
    @PostMapping("/queryAjaxList2")
    public JSONObject applyAjaxList2(HttpServletRequest request) {
        String currentPage = request.getParameter("pageno");
        //获取前台每页显示数据
        String pageSize = request.getParameter("pagesize");
        String tableName = request.getParameter("tableName");
        String dbKey = request.getParameter("dbKey");
        QueryInfoDto query = new QueryInfoDto();
        query.setPageNumber(Integer.parseInt(currentPage));
        query.setPageSize(Integer.parseInt(pageSize));
        query.setTableName(tableName);
        JSONObject json = new JSONObject();
        try {
//			List<Map<String,Object>>  list =commonService.findList(query);
            PaginationSupport result = commonService.queryAjaxList2(query, dbKey);
            json.put("page", result);
            json.put("result", "0");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }


    @RequestMapping("/export/{tablename}/{id}")
    public void export(@PathVariable("id") String id,
                       @PathVariable("tablename") String tablename,
                       HttpServletResponse response,
                       ModelMap modelMap, HttpServletRequest request
    ) {
        // 查询列表数据
        String dbKey = request.getParameter("dbKey");
        List<TableInfo> list = dataSourceService.findTableInfo(tablename, dbKey);
        List<TableInfoExp> listExp = JSONArray.parseArray(JSONObject.toJSONString(list), TableInfoExp.class);

        for (TableInfoExp table : listExp) {
//		    	自增	主键	允许空
            if ("1".equals(table.getIdentification())) {
                table.setIdentification("√");
            } else {
                table.setIdentification("×");
            }

            if ("1".equals(table.getPk())) {
                table.setPk("√");
            } else {
                table.setPk("×");
            }

            if ("1".equals(table.getEmpty())) {
                table.setEmpty("√");
            } else {
                table.setEmpty("×");
            }
        }
        ExportParams params = new ExportParams();
        params.setType(ExcelType.XSSF);
        modelMap.put(NormalExcelConstants.DATA_LIST, listExp); // 数据集合
        modelMap.put(NormalExcelConstants.CLASS, TableInfoExp.class);// 导出实体
        modelMap.put(NormalExcelConstants.PARAMS, params);// 参数
        modelMap.put(NormalExcelConstants.FILE_NAME, tablename + "表信息");// 文件名称
        PoiBaseView.render(modelMap, request, response, NormalExcelConstants.EASYPOI_EXCEL_VIEW);
    }


    /**
     * 生成实体类
     *
     * @param tableName
     * @param
     * @return
     */
    @RequestMapping(value = "/generateEntityClasses/{tableId}/{tableName}")
    public void generateEntityClasses(@PathVariable("tableName") String tableName,
                                      @PathVariable("tableId") String tableId,
                                      HttpServletResponse response,
                                      ModelMap modelMap, HttpServletRequest request
    ) {
        String dbKey = request.getParameter("dbKey");
        List<TableInfo> list = dataSourceService.findTableInfo(tableName, dbKey);


        String result = dataSourceService.generateEntityClasses(list, tableName, dbKey);

        String fileName = getClassNameByTableName(tableName) + ".java";

        ServletOutputStream outputStream;
        try {
            outputStream = response.getOutputStream();
            fileName = this.filenameEncoding(fileName, request);
            response.setContentType("text/html;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            BufferedOutputStream buff = new BufferedOutputStream(outputStream);
            buff.write(result.getBytes("UTF-8"));
            buff.flush();
            buff.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getClassNameByTableName(String tableName) {
        String[] strs = tableName.split("_");
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            buf.append(this.toUpperCaseFirstOne(strs[i].toLowerCase()));
        }
        return buf.toString();
    }

    //首字母转大写
    public String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0))) {
            return s;
        } else {
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
        }
    }

    public String filenameEncoding(String filename, HttpServletRequest request) throws IOException {
        String agent = request.getHeader("User-Agent"); //获取浏览器
        if (agent.contains("Firefox")) {
            BASE64Encoder base64Encoder = new BASE64Encoder();
            filename = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8"))
                    + "?=";
        } else if (agent.contains("MSIE")) {
            filename = URLEncoder.encode(filename, "utf-8");
        } else if (agent.contains("Safari")) {
            filename = new String(filename.getBytes("utf-8"), "ISO8859-1");
        } else {
            filename = URLEncoder.encode(filename, "utf-8");
        }
        return filename;
    }

}
