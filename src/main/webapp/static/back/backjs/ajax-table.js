var urlpre = window.location.protocol+"//"+window.location.host+"/";
/**
 * 批量删除数据的ajax请求
 * @param data
 * @param urltype
 * @param tableIdType
 * @param ids 需要移除表格的数据id列表
 */
function ajaxBatchDelete(data,tableType,ids) {
    $.ajax ({
        url: urlpre+tableType+"/batchDelete.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            alert(result.msg)
            if (result.status == "success" || result.status == "exception"){
                    $("#"+tableType+"Table").bootstrapTable('remove', {
                        field:'id',
                        values:ids
                    });
            }
        }});
}

/**
 * 添加数据
 */
function ajaxAddData(data,tableType) {
    $.ajax ({
        url: urlpre+tableType+"/add.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            alert(result.msg)
            if (result.status == "success"){
                $("#"+tableType+"Table").bootstrapTable("refresh");
            }
        }});
}

/**
 * 修改数据
 */
function ajaxUpdateData(data,tableType) {

    $.ajax ({
        url: urlpre+tableType+"/update.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            alert(result.msg)
            if (result.status == "success"){
                $("#"+tableType+"Table").bootstrapTable("refresh");
            }
        }});
}