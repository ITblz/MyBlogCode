var page = 1;
var rows = 15;
var finalPage = false;
$(document).ready(function () {

    //根据不同的url获取文章列表数据
    ajaxGetArticleList();

    //加载标签
    ajaxGetAllTags();

    ajaxGetArticleTotal();

    ajaxGetVisitorTotal();

    ajaxGetCommentTotal();

    ajaxGetAllSorts();
})
//获取文章列表
function ajaxGetArticleList() {
    $.getJSON(getURL(),function (data) {
        //console.log("数据",data)
        var olderDate = null;
        console.log(data.length)
        if(data.length <= 0){
            finalPage = true;
            page = page -1;
            alert("已经是最后一页");
        }else {
            //先删除子元素
            $("#showArticleList").empty();
            $.each(data,function (index,item) {
                loadArticleList(olderDate,item);
                olderDate = new Date(item.date);
            });
        }

    });
}

//加载文章列表
function loadArticleList(olderDate,article) {

    var newDate = new Date(article.date);
    var oldStr = null;
    var newStr = newDate.format("yyyy.MM.dd");
    var isAppendDateLable = false;//追加 新的日期标签的标志位

    if (olderDate == null){
        isAppendDateLable = true;
    }else {
        oldStr = olderDate.format("yyyy.MM.dd");
        if(oldStr != newStr){
            isAppendDateLable = true;
        }
    }

    if (isAppendDateLable){
        var html1 = '<span class="timeline-label">\n' +
            '<span class="label label-primary">'+newStr+'</span>\n' +
            '</span>';
        $("#showArticleList").append(html1);
    }

    var html2 = '<div class="timeline-item">\n' +
        '<div class="timeline-point timeline-point-success">\n' +
        '      <i class="fa fa-calendar"></i>\n' +
        '</div>\n' +
        ' <div class="timeline-event">\n' +
        '      <div class="timeline-heading">\n' +
        '          <h4><a href="articleDetail.html?article='+article.id+'">'+article.title+'</a> </h4>\n' +
        '      </div>\n' +
        '      <div class="timeline-body">\n' +
        '          <p>'+article.digest+'</p>\n' +
        '      </div>\n' +
        '      <div class="timeline-footer">\n' +
        '          <p class="text-right">'+(newDate.format("hh:mm:ss yyyy.MM.dd"))+'</p>\n' +
        '      </div>\n' +
        '  </div>\n' +
        '</div>';
    $("#showArticleList").append(html2);
}

/**
 * [通过参数名获取url中的参数值]
 * 示例URL:http://htmlJsTest/getrequest.html?uid=admin&rid=1&fid=2&name=小明
 * @param  {[string]} queryName [参数名]
 * @return {[string]}           [参数值]
 */
function getURLParamValue(queryName) {
    var reg = new RegExp("(^|&)" + queryName + "=([^&]*)(&|$)", "i");
    var r = window.location.search.substr(1).match(reg);
    if ( r != null ){
        return decodeURI(r[2]);
    }else{
        return null;
    }
}
//根据URL申请不同的ajax接口 关于文章列表的接口
function getURL(){
    //默认 请求全部文章接口
    var url = window.location.protocol+"//"+window.location.host+"/article/list/";
    var all = getURLParamValue("all");
    var theme = getURLParamValue("theme")
    var tag = getURLParamValue("tag");
    var sort = getURLParamValue("sort");
    var search = getURLParamValue("search");

    if(all != null && all == 'true'){
        url =  url +"all/findall/";
    }else
    if(theme != null){
        url =  url +"theme/"+theme+"/";
    }else
    if(tag != null){
        url =  url +"tag/"+tag+"/";
    }else
    if(sort != null){
        url =  url +"sort/"+sort+"/";
    }else if(search != null){
        url = url +"search/"+search+"/";
    }
    url = url + +page+"/"+rows;
    console.log("请求文章列表的url\n"+url)
    return url;
}
//翻页函数
function changePage(flag) {
    if (flag == 0){
        finalPage =false;
        if (page == 1 || page <= 0){
            alert("已经是第一页")
        } else{
            page = page - 1;
            ajaxGetArticleList();
        }
    } else {
        if(finalPage == true){
            alert("已经是最后一页");
        }else {
            page = page+1;
            ajaxGetArticleList();
        }

    }
}