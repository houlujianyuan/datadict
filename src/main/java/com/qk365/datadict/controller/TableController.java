package com.qk365.datadict.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.afterturn.easypoi.view.PoiBaseView;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qk365.datadict.dao.DataSourceListMapper;
import com.qk365.datadict.dto.*;
import com.qk365.datadict.po.DataSourceList;
import com.qk365.datadict.po.TableInfo;
import com.qk365.datadict.po.Users;
import com.qk365.datadict.service.CommonService;
import com.qk365.datadict.service.MySqlService;
import com.qk365.datadict.service.SqlServerService;
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
    private SqlServerService sqlServerService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private DataSourceListMapper dataSourceListMapper;
    @Autowired
    private MySqlService mySqlService;

    /**
     * 加载表信息
     *
     * @param model
     * @return
     */
    @GetMapping("/left")
    String left(Model model, HttpServletRequest request) {
        List<Map<String, Object>> list = new ArrayList<>();
        String dbKey = request.getParameter("dbKey");
        Users users = (Users) request.getSession().getAttribute("user");
        //根据dbkey查询数据库类型
        DataSourceList dataSourceList = new DataSourceList();
        dataSourceList.setDatabaseName(dbKey);
      /*  dataSourceList.setUserId(users.getId());*/
        DataSourceList dataSourceList1 = dataSourceListMapper.selectOne(dataSourceList);
        //mysql
        if (dataSourceList1.getType() == 1) {
            list = mySqlService.findTableName(dbKey, dbKey);
            if (list != null && list.size() > 0) {
                list.stream().forEach(dd -> {
                    dd.put("explain", dd.get("comment"));
                });
            }
        } else if (dataSourceList1.getType() == 2) {
            //sqlServer
            list = sqlServerService.findTableName(dbKey);
        } else if (dataSourceList1.getType() == 3) {
            //sqLite
        }

        model.addAttribute("list", list);
        model.addAttribute("dbKey", dbKey);
        return "main/left";
    }

    /**
     * AJAX加载TABLE
     *
     * @return
     */
/*    @ResponseBody
    @RequestMapping(value = "/leftAjax")
    public ResultVO leftAjax(String dbKey) {
        List<Map<String, Object>> list = sqlServerService.findTableName(dbKey);
        //按照开头字母 分组 排序
        if (list == null || list.size() < 1) {
            return new ResultVO(1, "无表数据", null);
        }

        Map<String, List<Map<String, Object>>> tabMap = new HashMap<String, List<Map<String, Object>>>();

        //按照字母分组
        String firstChar = "";
        List<Map<String, Object>> charList = null;
        for (Map<String, Object> map : list) {
            firstChar = map.get("name").toString().substring(0, 1).toUpperCase();
            charList = tabMap.get(firstChar);
            if (charList == null) {
                charList = new ArrayList<Map<String, Object>>();
                charList.add(map);
                tabMap.put(firstChar, charList);
            } else {
                charList.add(map);
            }
        }
        return new ResultVO(0, "成功", JSONObject.toJSON(tabMap));
    }*/

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
        Integer dataType = this.findDbType(dbKey, request);

        //默认表
        if (dataType == 1) {
            //mysql
            List<Map<String, Object>> tableList = mySqlService.findTableName(dbKey, dbKey);
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

        } else if (dataType == 2) {
            //sqlServer
            List<Map<String, Object>> tableList = sqlServerService.findTableName(dbKey);
            if (tablename.equals("1") && id.equals("1")) {
                tablename = tableList.get(0).get("name") + "";
                id = tableList.get(0).get("id") + "";
                explain = tableList.get(0).get("explain") + "";
            } else {
                for (Map<String, Object> map : tableList) {
                    if (map.get("name").equals(tablename)) {
                        if (map.get("explain") != null && !map.get("explain").equals("")) {
                            explain = map.get("explain") + "";
                        }
                    }
                }
            }
        }

        List<TableInfo> list = new ArrayList<>();
        if (dataType == 1) {
            list = mySqlService.findTableInfo(dbKey, tablename);
            list.stream().forEach(d -> {
                d.setExplain(d.getComment());
                if (d.getPk().equals("PRI")) {
                    d.setPk("1");
                }
                if (d.getIdentification().equals("auto_increment")) {
                    d.setIdentification("1");
                }
                if (null == d.getDefaultValue()) {
                    d.setDefaultValue("");
                }
                if (d.getEmpty().equals("NO")) {
                    d.setEmpty("");
                } else {
                    d.setEmpty("1");
                }
            });
        } else if (dataType == 2) {
            list = sqlServerService.findTableInfo(Long.valueOf(id), dbKey);
        }


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
        Integer dataType = this.findDbType(dbKey, request);
        if (dataType == 1) {
            mySqlService.editTableExplain(tableName, explain, dbKey);
        } else if (dataType == 2) {

            if (oldVal.equals("请输入表说明")){
                sqlServerService.addTableExplain(tableName,explain,dbKey);
            }else {
                sqlServerService.editTableExplain(tableName, explain, dbKey);
            }
         /*   sqlServerService.insertEditTableInfo(tableName, oldVal, explain, "", "1", dbKey);*/
        }

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



        Integer dataType = this.findDbType(dbKey, request);
        if (dataType == 1) {
            mySqlService.editColumnExplain(tableName, explain,columnName,dbKey);
        } else if (dataType == 2) {

            if (oldVal.equals("")){
                sqlServerService.addColumnExplain(tableName, explain, columnName, dbKey);

            }else {
                sqlServerService.editColumnExplain(tableName, explain, columnName, dbKey);
            }
        }

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
        Integer dataType = this.findDbType(dbKey, request);
        List<TableInfo> list = new ArrayList<>();
        if (dataType == 1) {
            list = mySqlService.findTableInfo(dbKey, tableName);
        } else if (dataType == 2) {
            list = sqlServerService.findTableInfo(Long.valueOf(id), dbKey);
        }
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
        Integer dataType = this.findDbType(dbKey, request);
        List<TableInfo> list = new ArrayList<>();
        if (dataType == 1) {
            list = mySqlService.findTableInfo(dbKey, tablename);
        } else if (dataType == 2) {
            list = sqlServerService.findTableInfo(Long.valueOf(id), dbKey);
        }
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
        List<TableInfo> list = new ArrayList<>();
        Integer dataType = this.findDbType(dbKey, request);
        if (dataType == 1) {
            list = mySqlService.findTableInfo(dbKey, tableName);
        } else if (dataType == 2) {
            list = sqlServerService.findTableInfo(Long.valueOf(tableId), dbKey);
        }
        String result = sqlServerService.generateEntityClasses(list, tableName, dbKey);

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

    private Integer findDbType(String dbKey, HttpServletRequest request) {
        //根据dbkey查询数据库类型
       /* Users users = (Users) request.getSession().getAttribute("user");*/
        DataSourceList dataSourceList = new DataSourceList();
        dataSourceList.setDatabaseName(dbKey);
     /*   dataSourceList.setUserId(users.getId());*/
        DataSourceList dataSourceList1 = dataSourceListMapper.selectOne(dataSourceList);
        return dataSourceList1.getType();
    }
}
