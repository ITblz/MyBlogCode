var urlpre = window.location.protocol+"//"+window.location.host+"/";
function initTable(TableInitParams) {
    var $table = $("#"+TableInitParams.tableId);
    $table.bootstrapTable({
        url: urlpre+TableInitParams.tableType+'/showList.do',        //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        striped: true,                      //是否显示行间隔色
        toolbar: '#'+TableInitParams.tableId+"-toolbar",   //工具按钮用哪个容器
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        sidePagination: 'client',           //分页方式：client客户端分页，server服务端分页（*）
        responseHandler: function(res){
            return res.rows;
        },
        pagination: true,                   //是否显示分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页
        pageSize: 5,                        //每页的记录行数（*）
        pageList: [5, 10, 25, 50],          //可供选择的每页的行数（*）
        search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        minimumCountColumns: 2,             //最少允许的列数
        showRefresh: true,                  //是否显示刷新按钮
        clickToSelect: true,                //是否启用点击选中行
//      showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        cardView: false,                    //是否显示详细视图
        detailView: TableInitParams.detailView,                  //是否显示父子表
        detailFormatter:"detailFormatter", //2，定义详情显示函数
        idField: TableInitParams.idField,
        columns: TableInitParams.columns,
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                //pageSize: params.limit,                         //页面大小
                //page: (params.offset / params.limit) + 1,   //页码
                //sort: params.sort,      //排序列名
                //sortOrder: params.order //排位命令（desc，asc）
            };
            return temp;
        },
        onLoadSuccess: function () {
            console.log("数据加载成功！");
        },
        onLoadError: function () {
            console.log("数据加载失败！");
        },
        onDblClickRow: function (row, $element,field) {

        },
        onClickCell: function(field, value, row, $element) {
            var index = $element.parent().data('index');
            //console.log("单击索引"+index)
        }

    });
    //格式化单元格样式
    function paramsMatter(value,row,index) {
        var span=document.createElement('span');
        span.setAttribute('title',value);
        span.innerHTML = value;
        return span.outerHTML;
    }
    //格式化单元格文本输出样式
    function formatTableUnit(value, row, index) {
        return {
            css: {
                "white-space": 'nowrap',
                "text-overflow": 'ellipsis',
                "overflow": 'hidden'
            }
        }
    }

}

/**
 * 初始化表格工具条中的按钮事件
 * @param TableInitParams
 */
function initToolbar(TableInitParams) {
    //添加按钮点击事件绑定
    $('#'+TableInitParams.tableId+'-toolbar-btn-add').click(function () {
            $("#modal-"+TableInitParams.tableType+"-add").modal("show")
    })
    //修改按钮点击事件绑定
    $('#'+TableInitParams.tableId+'-toolbar-btn-edit').click(function () {
        var selectedLine = $('#'+TableInitParams.tableId).bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        }else if(selectedLine.length>1){
            alert("只能选中一行！")
        } else {
            var name = selectedLine[0].name;
            if (TableInitParams.tableType == 'tag'){
                $("#edit-tag-text").val(name);
            } else if(TableInitParams.tableType == 'sort'){
                $("#edit-sort-text").val(name);
            }else if (TableInitParams.tableType == 'theme') {
                $("#edit-theme-text").val(name);
            }
            $("#modal-"+TableInitParams.tableType+"-edit").modal("show")
        }

    })
    //审核按钮点击事件绑定
    $('#'+TableInitParams.tableId+'-toolbar-btn-check').click(function () {
        var selectedLine = $('#'+TableInitParams.tableId).bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        } else {
            $("#modal-"+TableInitParams.tableType+"-check").modal("show")
        }

    })
    //删除按钮点击事件绑定
    $('#'+TableInitParams.tableId+'-toolbar-btn-delete').click(function () {
        var selectedLine = $('#'+TableInitParams.tableId).bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        } else {
            var isDel = confirm("是否删除所选数据");
            if (isDel){
                console.log("准备删除数据")
                var data = {
                    "ids":packIds(getIdSelections(TableInitParams.tableType))
                }
                //向后台发送批量删除请求
                ajaxBatchDelete(data,TableInitParams.tableType,getIdSelections(TableInitParams.tableType))
            } else {
                console.log("并不删除数据")
            }
        }
    })
}

/**
 * 获得表格中选中项的id
 * @param tableIdType
 * @returns {*}
 */
function getIdSelections(tableIdType) {
    return $.map($("#"+tableIdType+"Table").bootstrapTable('getSelections'), function (row) {
        return row.id
    });
}

/**
 * 遍历获得表格中的索引
 * @param tableIdType 表格ID
 * @param startIndex 开始索引
 * @param id  行索引的id
 * @returns 返回索引
 */
function getIndex(tableIdType,startIndex,id){
    var indexTemp;
    var allTableData = $("#"+tableIdType+"Table").bootstrapTable('getData');
    for(var i = startIndex; i < allTableData.length; i++) {
        indexTemp = i;
        //如果此行中有与你想获取的相同，则跳出循环，indexTemp是你要的行索引
        if(allTableData[i].id == id) {
            break;
        }
    }
    return indexTemp;
}

/**
 * 表格详情展示
 * @param index
 * @param row
 * @returns {string}
 */
function detailFormatter(index, row) {
    var html = []
    $.each(row, function (key, value) {
        if(key == 0)
            return;
        if(key == 'date'){
            value = new Date(value).format("yyyy-MM-dd hh:mm:ss")
        }
        html.push('<p><b>' + key + ':</b> ' + value + '</p>');
    });
    return html.join('');
}
//批量处理数据时，对ids的打包处理
function packIds(map) {
    var list = "";
    $.each(map,function (index,value) {
        list = list + value + ",";
    })
    return list.substring(0,list.length-1);
}
//通过按钮的点击事件
function changeChecked(tableType) {
    var selectedLine = $("#"+tableType+"Table").bootstrapTable('getSelections');
    var curIndex = 0;
    //循环找到 已经选中的行有哪些
    for(var i=0;i<selectedLine.length;i++)
    {
        if (selectedLine[i].checked == 0){
            curIndex = getIndex(tableType,0,selectedLine[i].id);
            //更新表格数据
            $('#themeTable').bootstrapTable("updateRow",{
                index:curIndex,
                replace:true,
                row:{
                    checked:1
                }
            });
        }

    }
}

function addData(type) {
    var name = null;
    var id = getIdSelections(type)[0];
    if (type == 'tag'){
        name = $("#add-tag-text");
    } else if(type == 'sort'){
        name = $("#add-sort-text");
    }else if (type == 'theme') {
        name = $("#add-theme-text");
    }
    var nameVal = name.val();
    if (nameVal == null || nameVal == ""){
        alert("输入为空，请重新输入");
        return;
    }

    list = {
        'name': nameVal
    }
    $("#modal-"+type+"-add").modal("hide")
    ajaxAddData(list,type)
    name.val("")

}

function editData(type) {
    var name = null;
    var id = getIdSelections(type)[0];
    var list = "";
    if (type == 'tag'){
        name = $("#edit-tag-text");
    } else if(type == 'sort'){
        name = $("#edit-sort-text");
    }else if (type == 'theme') {
        name = $("#edit-theme-text");
    }
    var nameVal = name.val();
    list = {
        'id':id,
        'name':nameVal
    }
    $("#modal-"+type+"-edit").modal("hide")
    ajaxUpdateData(list,type)
    name.val("")
}
