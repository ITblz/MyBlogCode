//将日期转化为指定的格式
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

//提取文章中的第一个图片链接
function getImgHref(html) {
    $("footer").append('<div hidden class="hidden-text">'+html+'</div>');
    var img = $("footer div img")[0]
    var img = $("footer div img")[0];
    src = $(img).attr("src");
    $("footer div[class='hidden-text']").remove()
    return src;
}

//处理文章的预HTML字符串 获得摘要 120个字符
function getArticleDigest(html,length) {
    var txt = html.replace(/<[^<>]+>/g,'');
    var res = txt.substring(0,length);
    return txt.trim();
}

//获取 当前url的参数
function getParamValue(name){
    var search = document.location.search;
    var pattern = new RegExp("[?&]"+name+"\=([^&]+)", "g");
    var matcher = pattern.exec(search);
    var items = null;
    if(null != matcher){
        try{
            items = decodeURIComponent(decodeURIComponent(matcher[1]));
        }catch(e){
            try{
                items = decodeURIComponent(matcher[1]);
            }catch(e){
                items = matcher[1];
            }
        }
    }
    return items;
};

//加载标签
function loadTags(tag){
    html = '<label class="label  col-xs-6 col-md-4"><a href="articleList.html?tag='+tag.name+'">#'+tag.name+'</a></label>';
    $("#tags").append(html);
}

//加载分类集
function loadSorts(sort) {
    html = '<label class="col-xs-6 col-sm-6 label"><a href="articleList.html?sort='+sort.name+'">'+sort.name+'</a></label>';
    $("#sorts").append(html);
}

//url地址前缀
var urlpre = window.location.protocol+"//"+window.location.host+"/";

//请求所有标签
function ajaxGetAllTags() {
    console.log("urlpre",urlpre)
    //请求所有标签
    url=urlpre + "tag/findAll";
    $.get(url,function (data) {
        //console.log("数据",data)
        $.each(data,function (index,item) {
            loadTags(item);
        });
    })
}

//请求文章总数
function ajaxGetArticleTotal() {
    url=urlpre + "article/total";
    $.get(url,function (data) {
        $("#articleTotalText").text(data);
    })
}

//请求访问总数
function ajaxGetVisitorTotal() {
    url=urlpre + "article/total";
    $.get(url,function (data) {
        $("#visitorTotalText").text(data);
    })
}

//请求评论总数
function ajaxGetCommentTotal() {
    url=urlpre + "comment/total";
    $.get(url,function (data) {
        $("#commentTotalText").text(data)
    })
}

//请求获得分类集
function ajaxGetAllSorts() {
    url=urlpre + "sort/findAll";
    $.get(url,function (data) {
        //console.log("数据",data)
        $.each(data,function (index,item) {
            loadSorts(item);
        });
    })
}

//搜索执行函数
function clickSearch(){
    var t = $("#searchText").val();
    window.location.href="articleList.html?search="+t;
}

