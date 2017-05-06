var _table;
$(function (){
    var $wrapper = $('#div-table-container');
    var $table = $('#table-user');

    _table = $table.dataTable($.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
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
                data: "videoName",
                //width : "120px"	,
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
                className : "ellipsis",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                data : "fileSize",
                //width : "80px"
                render: function (data, type, row, meta) {
                    if (data === 0) return '0 B';
                    var k = 1000, // or 1024
                        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
                        i = Math.floor(Math.log(data) / Math.log(k));
                    return (data / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
                },
            },
            {
                sortable:false,
                className : "ellipsis",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                data : "fileUrl",
                //width : "120px"
            },

            {
                className : "ellipsis",
                render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
                data : "uploadTime",
                //width : "80px"
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

    /*$("#btn-advanced-search").click(function(){
        userManage.fuzzySearch = false;
        _table.draw();
    });

    $("#btn-save-add").click(function(){
        userManage.addItemSubmit();
    });*/
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
                    param.orderColumn = "videoName";
                    break;
                case 2:
                    param.orderColumn = "fileSize";
                    break;
                case 4:
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
            /*param.songName = $("#songName-search").val();
            param.artist = $("#artist-search").val();
            param.album = $("#album-search").val();
            param.fileName = $("#fileName-search").val();*/
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
        $("#videoName-view").text(item.videoName);

        $("#fileUrl-view").text(item.fileUrl);

        $("#uploadTime-view").text(item.uploadTime);

        $("#fileSize-view").text(item.fileSize);
    },
    deleteItem : function(selectedItems) {
        var message;
        if (selectedItems&&selectedItems.length) {
            if (selectedItems.length == 1) {
                message = "确定要删除 '"+selectedItems[0].videoName+"' 吗?";
            }else{
                message = "确定要删除选中的"+selectedItems.length+"项记录吗?";
            }
            var adminId=$("#adminIdInput").val();
            var token=$("#tokenInput").val();
            $.dialog.confirmDanger(message, function(){
                //$.dialog.tips('执行删除操作');
                $.ajax({
                    type: "POST",
                    url: "delete",
                    cache : false,	//禁用缓存
                    data: "needDeleteJsonArray="+JSON.stringify(selectedItems)+"&adminId="+adminId+"&token="+token,
                    success: function(result) {
                        _table.ajax.reload();

                    },
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        $.dialog.alert("XMLHTTPRequest失败");
                    }
                });


            });
        }else{
            $.dialog.tips('请先选中要操作的行');
        }
    }
};