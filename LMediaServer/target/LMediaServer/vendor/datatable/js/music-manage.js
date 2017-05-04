$(function (){
	var $wrapper = $('#div-table-container');
	var $table = $('#table-user');
	
	var _table = $table.dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
		ajax : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
			//手动控制遮罩
			$wrapper.spinModal();
			//封装请求参数
			var param = userManage.getQueryCondition(data);
			$.ajax({
		            type: "POST",
		            url: "get",
		            cache : false,	//禁用缓存
		            data: param,	//传入已封装的参数
		            dataType: "json",
		            success: function(result) {
		            	//setTimeout仅为测试遮罩效果
		            	setTimeout(function(){
		            		//异常判断与处理
		            		if (result.errorCode) {
		            			$.dialog.alert("查询失败。错误码："+result.errorCode);
		            			return;
							}
		            		
		            		//封装返回数据，这里仅演示了修改属性名
		            		var returnData = {};
			            	returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
			            	returnData.recordsTotal = result.total;
			            	returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
			            	returnData.data = result.pageData;
			            	//关闭遮罩
			            	$wrapper.spinModal(false);
			            	//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
			            	//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
			            	callback(returnData);
		            	},200);
		            },
		            error: function(XMLHttpRequest, textStatus, errorThrown) {
		                $.dialog.alert("查询失败");
		                $wrapper.spinModal(false);
		            }
		        });
		},
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            {
            	className : "ellipsis",	//文字过长时用省略号显示，CSS实现
            	data: "songName",
            	width : "80px"	,
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
            	className : "ellipsis",
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
            	data: "artist",
            	//固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
				//切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。
				width : "60px"			
            },
			{
            	className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "album",
				width : "80px",
				/*render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?"在线":"离线");
				}*/
			},
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "year",
				width : "60px"
			},
			
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "fileName",
				width : "90px"
			},
			
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "duration",
				width : "50px"
			},
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "fileSize",
				width : "60px"
			},
			
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "fileUrl",
				width : "80px"
			},
			{
				sortable:false,
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				className : "ellipsis",	
				data : "pictureUrl",
				//render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				render: function (data, type, row, meta) {
                   return "<img src='" + data + "' width='50px' height='50px' />";

               },
				width : "60px"
			},
			
			{
				className : "ellipsis",	
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				data : "uploadTime",
				width : "80px"
			}
        ],
        "createdRow": function ( row, data, index ) {
        	//行渲染回调,在这里可以对该行dom元素进行任何操作
        	//给当前行加样式
        	if (data.role) {
        		$(row).addClass("info");
			}
        	//给当前行某列加样式
        	$('td', row).eq(12).addClass(data.status?"text-success":"text-error");
        	//不使用render，改用jquery文档操作呈现单元格
            var $btnEdit = $('<button type="button" class="btn btn-small btn-primary btn-edit">修改</button>');
            var $btnDel = $('<button type="button" class="btn btn-small btn-danger btn-del">删除</button>');
            $('td', row).eq(12).append($btnEdit).append($btnDel);
            
        },
        "drawCallback": function( settings ) {
        	//渲染完毕后的回调
        	//清空全选状态
			$(":checkbox[name='cb-check-all']",$wrapper).prop('checked', false);
        	//默认选中第一行
        	$("tbody tr",$table).eq(0).click();
        }
	})).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象


	
	$("#btn-del").click(function(){
		var arrItemId = [];
        $("tbody :checkbox:checked",$table).each(function(i) {
        	var item = _table.row($(this).closest('tr')).data();
        	arrItemId.push(item);
        });
		userManage.deleteItem(arrItemId);
	});
	
	$("#btn-simple-search").click(function(){
		userManage.fuzzySearch = true;
		
		//reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
//		_table.ajax.reload();
//		_table.draw(false);
		_table.draw();
	});
	
	$("#btn-advanced-search").click(function(){
		userManage.fuzzySearch = false;
		_table.draw();
	});
	
	$("#btn-save-add").click(function(){
		userManage.addItemSubmit();
	});
	
	$("#btn-save-edit").click(function(){
		userManage.editItemSubmit();
	});
	
	//行点击事件
	$("tbody",$table).on("click","tr",function(event) {
		$(this).addClass("active").siblings().removeClass("active");
		//获取该行对应的数据
		var item = _table.row($(this).closest('tr')).data();
		userManage.currentItem = item;
		userManage.showItemDetail(item);
    });
	
	$table.on("change",":checkbox",function() {
		if ($(this).is("[name='cb-check-all']")) {
			//全选
			$(":checkbox",$table).prop("checked",$(this).prop("checked"));
		}else{
			//一般复选
			var checkbox = $("tbody :checkbox",$table);
			$(":checkbox[name='cb-check-all']",$table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
		}
    }).on("click",".td-checkbox",function(event) {
    	//点击单元格即点击复选框
    	!$(event.target).is(":checkbox") && $(":checkbox",this).trigger("click");
    }).on("click",".btn-edit",function() {
    	//点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.currentItem = item;
		userManage.editItemInit(item);
	}).on("click",".btn-del",function() {
		//点击删除按钮
		var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.deleteItem([item]);
	});
	
	$("#toggle-advanced-search").click(function(){
		$("i",this).toggleClass("fa-angle-double-down fa-angle-double-up");
		$("#div-advanced-search").slideToggle("fast");
	});
	
	$("#btn-info-content-collapse").click(function(){
		$("i",this).toggleClass("fa-minus fa-plus");
		$("span",this).toggle();
		$("#user-view .info-content").slideToggle("fast");
	});
	
	$("#btn-view-edit").click(function(){
		userManage.editItemInit(userManage.currentItem);
	});
	
	$(".btn-cancel").click(function(){
		userManage.showItemDetail(userManage.currentItem);
	});
});

var userManage = {
	currentItem : null,
	fuzzySearch : true,
	getQueryCondition : function(data) {
		var param = {};
		//组装排序参数
		if (data.order&&data.order.length&&data.order[0]) {
			switch (data.order[0].column) {
			case 1:
				param.orderColumn = "songName";
				break;
			case 2:
				param.orderColumn = "artist";
				break;
			case 3:
				param.orderColumn = "album";
				break;
			case 4:
				param.orderColumn = "year";
				break;
			case 5:
				param.orderColumn = "fileName";
				break;
			case 6:
				param.orderColumn = "duration";
				break;
			case 7:
				param.orderColumn = "fileSize";
				break;
			case 8:
				param.orderColumn = "uploadTime";
				break;
			default:
				param.orderColumn = "id";
				break;
			}
			param.orderDir = data.order[0].dir;
		}
		//组装查询参数
		param.fuzzySearch = userManage.fuzzySearch;
		if (userManage.fuzzySearch) {
			param.fuzzy = $("#fuzzy-search").val();
		}else{
			param.songName = $("#songName-search").val();
			param.artist = $("#artist-search").val();
			param.album = $("#album-search").val();
			param.year = $("#year-search").val();
			param.fileName = $("#fileName-search").val();
		}
		//组装分页参数
		param.startIndex = data.start;
		param.pageSize = data.length;
		
		param.draw = data.draw;
		
		return param;
	},
	showItemDetail : function(item) {
		$("#user-view").show().siblings(".info-block").hide();
		if (!item) {
			$("#user-view .prop-value").text("");
			return;
		}
		$("#songName-view").text(item.songName);
		$("#aritst-view").text(item.artist);
		$("#album-view").text(item.album);
		$("#year-view").text(item.year);
		$("#duration-view").text(item.duration);
		$("#uploadTime-view").text(item.uploadTime);
		$("#fileName-view").text(item.fileName);
		$("#fileSize-view").text(item.fileSzie);
        $("#fileUrl-view").text(item.fileUrl);
		$("#pic_url-view").text(item.pictureUrl);
	},
	/*addItemInit : function() {
		$("#form-add")[0].reset();
		
		$("#user-add").show().siblings(".info-block").hide();
	},*/
	editItemInit : function(item) {
		if (!item) {
			return;
		}
		$("#form-edit")[0].reset();
		$("#id-edit").val(item.id);
		$("#songName-edit").val(item.songName);
		$("#artist-edit").val(item.artist);
		$("#album-edit").val(item.album);
		$("#year-edit").val(item.year);
		$("#pic_url-edit").val(item.pictureUrl);
		$("#user-edit").show().siblings(".info-block").hide();
	},
	/*addItemSubmit : function() {
		$.dialog.tips('保存当前添加用户');
	},*/
	editItemSubmit : function() {
		
		var id=$("#id-edit").val();
		var name=$("#name-edit").val();
		var author=$("#author-edit").val();
		var publish=$("#publish-edit").val();
		var catg=$("#catg-edit").val();
		var isbn=$("#isbn-edit").val();
		var sum=$("#sum-edit").val();
		var ret=$("#ret-edit").val();
		var new_num=$("#new_num-edit").val();
		var barcode=$("#barcode-edit").val();
		var call_num=$("#call_num-edit").val();
		var pic_url=$("#pic_url-edit").val();
		if(id==""||name==""||catg==""||author==""||publish==""||sum==""||isbn==""||call_num==""||barcode==""||new_num==""||ret=="")
		 {
		 	alert("输入不能为空");
		 	return;
		 }
		//alert(id+name+author+publish+isbn+catg+sum+ret+new_num+barcode+call_num);
		var s=$("#pic_file-edit").val();

		$.ajax({
            type: "POST",
            url: "editBook.action",
            cache : false,	//禁用缓存
            data: "id="+id+"&name="+name+"&author="+author+"&publish="+publish+"&catg="+catg+"&isbn="+isbn+"&sum="+sum+"&ret="+ret+"&barcode="+barcode+"&call_num="+call_num+"&new_num="+new_num+"&pic_url="+pic_url,	//传入已封装的参数
            success: function(result) {
            	if(result==0)
            		$.dialog.tips('修改成功');
            	else
            		$.dialog.tips('修改失败');
            	//_table.draw();
            		
            }/*,
            error: function(XMLHttpRequest, textStatus, errorThrown) {
                //$.dialog.alert("修改失败");
            }*/
        });
	
		
		if(s!=""&&s!=null){
			//alert("YES");
			$.ajaxFileUpload({  
		        
		        url:"editImage.action?id="+id, 
		        fileElementId: 'pic_file-edit',
		        dataType:'text',  
		        success:function(data, status){  
		        	$.dialog.tips('修改成功');
		        },  
		        error:function(data, status, e){ 
		            alert("上传图片失败");  
		        }  
		        
			});
		}
	},
	deleteItem : function(selectedItems) {
		var message;
		if (selectedItems&&selectedItems.length) {
			if (selectedItems.length == 1) {
				message = "确定要删除 '"+selectedItems[0].name+"' 吗?";
				//alert(JSON.stringify(selectedItems));
				
				
			}else{
				message = "确定要删除选中的"+selectedItems.length+"项记录吗?";
				//alert(JSON.stringify(selectedItems));
				
			}
			$.dialog.confirmDanger(message, function(){
				//$.dialog.tips('执行删除操作');
				$.ajax({
		            type: "POST",
		            url: "deleteBook.action",
		            cache : false,	//禁用缓存
		            data: "jsonArray="+JSON.stringify(selectedItems),
		            success: function(result) {
		            	if(result>0)
		            		$.dialog.tips('删除成功');
		            	else
		            		$.dialog.tips('删除失败');
		            	//_table.draw();
		            		
		            }/*,
		            error: function(XMLHttpRequest, textStatus, errorThrown) {
		                //$.dialog.alert("修改失败");
		            }*/
		        });
				
				
			});
		}else{
			$.dialog.tips('请先选中要操作的行');
		}
	}
};