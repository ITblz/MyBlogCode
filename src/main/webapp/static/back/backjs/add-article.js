var urlpre = window.location.protocol+"//"+window.location.host+"/";
function ajaxGetAllThemes() {
    $.get(urlpre+"theme/findAll",function (data) {
        $.each(data,function (index,value) {
            loadHtmlTheme(value)
        })
    })
}
function loadHtmlTheme(theme) {
    var html = '<option value="'+theme.id+'">'+theme.name+'</option>';
    $("#select-themes").append(html);
}
function ajaxGetAllSorts() {
    $.get(urlpre+"sort/findAll",function (data) {
        $.each(data,function (index,value) {
            loadHtmlSort(value)
        })
    })
}
function loadHtmlSort(sort) {
    var html = '<option value="'+sort.id+'">'+sort.name+'</option>';
    $("#select-sorts").append(html);
}

//打包添加文章的请求数据
function packData(html,markdown) {
    var digest = getArticleDigest(html,200);
    var title = $("#input-title").val();
    if (title == ""){
        alert("文章标题不能为空");
        return;
    }
    if (digest == ''){
        alert("文章内容不能为空");
        return;
    }
    var data = {
        'title':$("#input-title").val(),
        'digest':getArticleDigest(html,200),
        'content':html,
        'author':$("#input-author").val(),
        'date':new Date(),
        'theme_id':$("#select-themes option:selected").val(),
        'sort_id':$("#select-sorts option:selected").val(),
        'markdown':markdown
    }
    return data;
}
//发送添加文章请求
function ajaxAddArticle(data) {
    $.ajax ({
        url: urlpre+"article/add.do",
        type: "POST",
        data: JSON.stringify(data),
        dataType: "json",
        contentType: "application/json; charset=utf-8",
        success: function(result){
            if(result != -1){
                alert("添加成功！")
                console.log("添加成功，文章的ID是"+result);
                changeTabToArticleList();
            }else {
                alert("添加失败！")
            }
        }});
}
//发送添加标签数据的请求
function ajaxDealArticleTags() {

}
//跳转到文章列表的Tab
function changeTabToArticleList() {
    var $tabContainer = $('#container');

    var obj ={
        'id':"文章列表",
        'url':"articleList.html",
        'text':'文章列表',
        'closeable': true
    }
    $tabContainer.data('tabs').remove("撰写文章")
    $tabContainer.data('tabs').reload(obj)
}

/**
 * 初始化界面
 * 关于去判断到底是修改文章的界面
 * 还是关于添加文章的界面
 */
function initAddArticle() {
    var editor = editormd("test-editor", {
        placeholder: '本编辑器支持Markdown编辑，左边编写，右边预览',
        width: "100%",
        height: 940,
        path: "/static/editor/lib/",
        emoji: true,
        taskList: true,
        saveHTMLToTextarea: true,    // 保存 HTML 到 Textarea
        searchReplace: true,
        flowChart: true,             // 开启流程图支持，默认关闭
        sequenceDiagram: true,       // 开启时序/序列图支持，默认关闭,
        tocm: true,         // Using [TOCM]
        tex: true,                   // 开启科学公式TeX语言支持，默认关闭
        /**上传图片相关配置如下*/
        imageUpload : true,
        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
        imageUploadURL : window.location.protocol+"//"+window.location.host+"/fileUpload/editorMDImageFileUpload",//注意你后端的上传图片服务地址
    });
    var tabId = $("#container").data("tabs").getCurrentTabId();
    if(tabId == "撰写文章"){
        $("#btn_article_update").text("发布文章");
        $("#btn_article_update").bind('click', function () {
            var html = editor.getPreviewedHTML();
            var markdown = editor.getMarkdown();
            //组织 向后台传送的数据
            var data = packData(html,markdown);
            //发送添加文章的请求
            ajaxAddArticle(data)
        });
    }else {
        $("#btn_article_update").text("修改文章");
        var articleId = tabId.substr(15,5)
        console.log("跳转到修改文章的界面，文章的ID是"+articleId);
        $.get(urlpre+"article/markdown.do/"+articleId,function (data) {
            console.log("获取文章成功！")
            loadWillUpdateArtice(data)
        })
    }
}
//修改界面的数据填充
function loadWillUpdateArtice(article) {
    $("#input-title").val(article.title);
    $("#input-author").val(article.author);
    $("#select-themes").val(article.theme_id);
    $("#select-sorts").val(article.sort_id);
    $($("#test-editor").children("textarea").get(0)).val(article.markdown);
}