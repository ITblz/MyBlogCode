var urlpre = window.location.protocol+"//"+window.location.host+"/";
$(function () {
    var $table = $("#commentTable");
    $table.bootstrapTable({
        url: urlpre+'comment/showCommentList.do',        //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        striped: true,                      //是否显示行间隔色
        toolbar: '#commentTable-toolbar',   //工具按钮用哪个容器
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
        detailView: true,                  //是否显示父子表
        detailViewIcon:false,//3，隐藏图标列
        detailViewByClick:true,//4,隐藏图标列
        detailFormatter:"detailFormatter", //2，定义详情显示函数
        idField: 'id',
        columns: [
            {
                checkbox: true
            }, {
                field: 'id',
                title: 'id',
                sortable: true,
                width: 60,
                align:'center'
            }, {
                field: 'name',
                title: '名字'
            },{
                field:'content',
                title:'内容',
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            },{
                field:'date',
                title:'日期',
                sortable: true,
                formatter:function(value,row,index){
                    var res = new Date(value).format("yyyy-MM-dd hh:mm:ss")
                    var span=document.createElement('span');
                    span.setAttribute('title',res);
                    span.innerHTML = res;
                    return span.outerHTML;
                    //return res;
                },
                cellStyle:formatTableUnit
            },{
                field:'email',
                title:'邮箱',
                cellStyle:formatTableUnit,
                formatter:paramsMatter
            },{
                field:'checked',
                title:'是否审核',
                sortable: true,
                formatter:function(value,row,index){
                    if(value == 1)
                        return '是'
                    return '否';
                },
                align:'center',
            },{
                field:'like_count',
                title:'点赞量',
                sortable: true,
                align:'center',
            }

        ],
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
    //让评论通过的按钮
    $('#btn_comment_edit').click(function () {
        var selectedLine = $table.bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        } else {
            $("#modal-comment-check").modal("show")
        }

    })
    $('#btn_comment_delete').click(function () {
        var selectedLine = $table.bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        } else {
            var isDel = confirm("是否删除所选数据");
            if (isDel){
                console.log("准备删除数据")
                var data = {
                    "ids":packIds(getIdSelections("comment"))
                }
                //向后台发送批量删除请求
                ajaxBatchDelete(data,"comment","comment",getIdSelections("comment"))
            } else {
                console.log("并不删除数据")
            }
        }
    })

})

//通过按钮的点击事件
function changeChecked() {
    var selectedLine = $("#commentTable").bootstrapTable('getSelections');
    var curIndex = 0;
    var list = "";
    var indexList = new Array();
    //循环找到 已经选中的行有哪些
    for(var i=0;i<selectedLine.length;i++)
    {
        if (selectedLine[i].checked == 0){
            curIndex = getIndex("comment",0,selectedLine[i].id);
            list = list + selectedLine[i].id+",";
            indexList.push(curIndex);
        }

    }
    list = list.substring(0,list.length-1);
    var data = {
        "ids":list
    }
    ajaxBatchUpdateCheckedFiled(data,"comment",indexList)
}
