Date.prototype.format = function(fmt){
    var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };

    if(/(y+)/.test(fmt)){
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }

    for(var k in o){
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(
                RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }

    return fmt;
}

//获得摘要
function getArticleDigest(html,length) {
    var txt = html.replace(/<[^<>]+>/g,'');
    var res = txt.substring(0,length);
    return txt.trim();
}

//批量处理数据时，对ids的打包处理
function packIds(map) {
    var list = "";
    $.each(map,function (index,value) {
        list = list + value + ",";
    })
    return list.substring(0,list.length-1);
}
var urlpre = window.location.protocol+"//"+window.location.host+"/";
/**
 * 批量删除数据的ajax请求
 * @param data
 * @param urltype
 * @param tableIdType
 * @param ids 需要移除表格的数据id列表
 */
function ajaxBatchDelete(data, urltype,tableIdType,ids) {
    $.ajax ({
        url: urlpre+urltype+"/batchDelete.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            alert(result.msg)
            if (result.status == "success" || result.status == "exception"){

                if (tableIdType == "tag" || tableIdType == "sort"){
                    $("#"+tableIdType+"Table").bootstrapTable('remove', {
                        field:tableIdType+'_id',
                        values:ids
                    });
                }else {
                    $("#"+tableIdType+"Table").bootstrapTable('remove', {
                        field:'id',
                        values:ids
                    });
                }
            }
        }});
}

/**
 * 批量更新通过
 * @param data 后台传输的数据
 * @param urltype 处理url的类型
 * @param indexList 更新表格数据的索引
 */
function ajaxBatchUpdateCheckedFiled(data,urltype,indexList){
    $.ajax ({
        url: urlpre+urltype+"/batchUpdateCheckedFiled.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            alert(result.msg)
            if (result.status == "success" || result.status == "exception"){
                $.each(indexList,function (index,value) {
                    //更新表格数据
                    $('#'+urltype+'Table').bootstrapTable("updateRow",{
                        index:value,
                        replace:true,
                        row:{
                            checked:1
                        }
                    });
                })
            }
        }});
}
/**
 * 获得表格中选中项的id
 * @param tableIdType
 * @returns {*}
 */
function getIdSelections(tableIdType) {
    if (tableIdType == 'tag') {
        return $.map($("#"+tableIdType+"Table").bootstrapTable('getSelections'), function (row) {
            return row.tag_id
        });
    }
    if (tableIdType == "sort"){
        return $.map($("#"+tableIdType+"Table").bootstrapTable('getSelections'), function (row) {
            return row.sort_id
        });
    }
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