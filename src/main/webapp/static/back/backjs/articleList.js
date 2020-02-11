var urlpre = window.location.protocol+"//"+window.location.host+"/";
$(function () {
    var $table = $("#articleListTable");
    $table.bootstrapTable({
        url: urlpre+'article/showArticleList.do',        //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        striped: true,                      //是否显示行间隔色
        toolbar: '#articleList-table-toolbar',                //工具按钮用哪个容器
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
//            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        idField: 'id',


        columns: [
            {
                checkbox: true
            }, {
                field: 'id',
                title: 'id',
                sortable: false
            }, {
                field: 'title',
                title: '标题'
            },{
                field:'author',
                title:'作者',
                sortable: true
            },{
                field:'date',
                title:'发布日期',
                sortable: true,
                formatter:function(value,row,index){
                    var res = new Date(value).format("yyyy-MM-dd hh:mm:ss")
                    return res;
                }
            },{
                field:'views',
                title:'阅读量',
                sortable: true
            },{
                field:'comment_count',
                title:'评论量',
                sortable: true
            },{
                field:'like_count',
                title:'点赞量',
                sortable: true
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
        onDblClickRow: function (row, $element) {

        }

    });

    //添加文章按钮
    $("#articleList-table_btn_add").click(function () {
        var datatext = "撰写文章";
        var $tabContainer = $('#container');
        var $tabId = $tabContainer.data('tabs').find(datatext);

        if($tabId.length>0)
        {
            console.log("find:"+$tabId);
            $tabContainer.data('tabs').showTab(datatext);
        }
        else {
            console.log("no find");
            $("#container").data("tabs").addTab({id:datatext, text: datatext, closeable: true, url: "add-article.html"});
        }
    })

    //修改文章按钮
    $('#articleList-table_btn_edit').click(function () {
        var selectedLine = $table.bootstrapTable('getSelections');
        if(selectedLine.length<1) {
            alert("请选择一行数据进行修改！")
        } else if(selectedLine.length>1){
            alert("只能选择一行数据进行修改！")
        } else{
            var datatext = "修改文章";
            var $tabContainer = $('#container');
            var $tabId = $tabContainer.data('tabs').find(datatext);

            if($tabId.length>0)
            {
                console.log("find:"+$tabId);
                $tabContainer.data('tabs').showTab(datatext);
            }
            else {
                console.log("no find");
                $("#container").data("tabs").addTab({
                    id:"updateArticleID"+selectedLine[0].id,
                    text: datatext,
                    closeable: true,
                    url: "add-article.html"});
            }
        }
    })

    //删除文章按钮
    $('#articleList-table_btn_delete').click(function () {
        var selectedLine = $table.bootstrapTable('getSelections');
        if(selectedLine.length<1)
        {
            alert("请选中一行数据");
        } else {
            var isDel = confirm("是否删除所选数据");
            if (isDel){
                console.log("准备删除数据")
                var data = {
                    "ids":packIds(getIdSelections("articleList"))
                }
                //向后台发送批量删除请求
                ajaxBatchDelete(data,"article","articleList",getIdSelections("articleList"))
            } else {
                console.log("并不删除数据")
            }
        }
    })

    //显示文章详情按钮
    $('#articleList-table_btn_detail').click(function () {
        var selectedLine = $table.bootstrapTable('getSelections');
        if(selectedLine.length<1) {
            alert("请选择一行数据，才能查看详情！")
        } else if(selectedLine.length>1){
            alert("只能查看一行数据的详情信息！")
        } else{
            var title = selectedLine[0].title;
            var html = selectedLine[0].content;

            $("#detail-title").text(title)
            $("#detail-content").html(html)
            $("#modal-articleList-detail").modal("show");
        }
    })

})