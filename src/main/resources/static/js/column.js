 
function edit(e,columnName){
	var oldVal=$(e).html();
	var html =" <textarea style=\"width:100%;height:100%\" class=\"editTextarea\">"+oldVal+"</textarea>";
	$(e).html(html);
	
	//获得焦点
	$(".editTextarea").val("").focus().val(oldVal);
	
	//失去焦点事件
	$(".editTextarea").blur(function(){
		$(e).html($(this).val());
		
		//当值改变是触发保存
		if(oldVal!=$(this).val()){
			postEdit($(this).val(),columnName,oldVal);
		}
	});
}

function editTable(e){
	var oldVal=$(e).html();
	var html =" <input type=\"text\" style=\"width:200px\" id=\"editExplain\" class=\"editExplain\" value=\""+oldVal+"\">";
	$(e).html(html);
	
	$(".editExplain").val("").focus().val(oldVal);
	
	$(".editExplain").blur(function(){
		$(e).html($(this).val());
		debugger
		//当值改变是触发保存
		if(oldVal!=$(this).val()){

			postEditTable($(this).val(),oldVal);
		}
	});
}
//提交后端
function postEdit(val,columnName,oldVal){ 
	var tableName=$("#tableName").val();
	layer.confirm('确认修改？', {
		  offset: 't',
		  anim: 6,
		  btn: ['确认','取消'] //按钮
		}, function(){
		  //保存 
		  $.ajax({
	            type: 'post',
	            url: "/api/editColumnExplain",
	            data: {"tableName":tableName,"columnName":columnName,"explain":val,"oldVal":oldVal},
	            dataType: "json",
	            success: function(msg) {
	                if(msg.result==0){
	                	layer.alert("成功！",{ offset: 't', anim: 6});
	                }else {
	                	layer.alert(msg.message,{ offset: 't', anim: 6});
	                }
	            },error: function(request) {
	    			    layer.alert("系统异常，联系管理员",{ offset: 't', anim: 6});
	    		}
	        });
			
		}, function(){
		  //取消
	});
}


//提交后端
function postEditTable(val,oldVal){
	debugger
	var tableName=$("#tableName").val();
	var dbKey = $("#dbKey").val()
	layer.confirm('确认修改？', {
		  offset: 't',
		  anim: 6,
		  btn: ['确认','取消'] //按钮
		}, function(){
		  //保存 
		  $.ajax({
	            type: 'post',
	            url: "/api/editTableExplain",
	            data: {"tableName":tableName,"explain":val,"oldVal":oldVal,"dbKey":dbKey},
	            dataType: "json",
	            success: function(msg) {
	                if(msg.result==0){
	                	layer.alert("成功！",{ offset: 't', anim: 6});
	                }else {
	                	layer.alert(msg.message,{ offset: 't', anim: 6});
	                }
	            },error: function(request) {
	    			    layer.alert("系统异常，联系管理员",{ offset: 't', anim: 6});
	    		}
	        });
			
		}, function(){
		  //取消
	});
}

function generateEntityClasses(tableId,tableName){
	  //保存 
	  $.ajax({
          type: 'post',
          url: "/generateEntityClasses",
          data: {"tableName":tableName,"tableId":tableId},
          dataType: "json",
          success: function(msg) {
              if(msg.result==0){
              	layer.alert(msg.message,{ offset: 't', anim: 6});
              }else {
              	layer.alert(msg.message,{ offset: 't', anim: 6});
              }
          },error: function(request) {
  			    layer.alert("系统异常，联系管理员",{ offset: 't', anim: 6});
  		  }
      });
		
}
