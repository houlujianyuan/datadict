

var pageCurr=1;
 
layui.use(['layer','table'], function(){
    var $ = layui.jquery, layer = layui.layer, table = layui.table;
    var cols=$.parseJSON("["+$("#cols").val()+"]");
    var tableName=$("#tableName").val()
    var dbKey = $("#dbKey").val()
    var screen = $("#screen").val()
    tableIns=table.render({
        elem: '#tableList',
        url:'/queryAjaxList',
        curr: pageCurr,
        method:'POST',
        page: true,
        where: { tableName: tableName,dbKey:dbKey,screen:screen},
        request: {
            pageName: 'pageNumber', //页码的参数名称，默认：page
            limitName: 'pageSize' //每页数据量的参数名，默认：limit
        },response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        }, 
        id:'iaId',
        cols: cols,
        done: function(res, curr, count){
            pageCurr=curr;
            $(".total-num").text(count);
        }
    });
    
});

$('#screen').on('keydown', function (event) {
    if (event.keyCode == 13) {
        var $ = layui.jquery, layer = layui.layer, table = layui.table;
        var cols=$.parseJSON("["+$("#cols").val()+"]");
        var tableName=$("#tableName").val()
        var dbKey = $("#dbKey").val()
        var screen = $("#screen").val()
        tableIns=table.render({
            elem: '#tableList',
            url:'/queryAjaxList',
            curr: pageCurr,
            method:'POST',
            page: true,
            where: { tableName: tableName,dbKey:dbKey,screen:screen},
            request: {
                pageName: 'pageNumber', //页码的参数名称，默认：page
                limitName: 'pageSize' //每页数据量的参数名，默认：limit
            },response:{
                statusName: 'code', //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: 'totals', //数据总数的字段名称，默认：count
                dataName: 'list' //数据列表的字段名称，默认：data
            },
            id:'iaId',
            cols: cols,
            done: function(res, curr, count){
                pageCurr=curr;
                $(".total-num").text(count);
            }
        });

    }
});

 