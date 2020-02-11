
$(document).ready(function(){
    var urlpre = window.location.protocol+"//"+window.location.host;
    //请求 生活文章life
    $.get(urlpre+"/article/lastLifeArticle",function (data) {
        //console.log("数据",data)
        $.each(data,function (index,item) {
            loadIndexLifeArtile(item);
        });
    });

    //请求 博客blog
    $.get(urlpre+"/article/mostViewBlogArticle",function(result){
        $.each(result,function (index,item) {
            showLastArticlesFun(item);
        });
    });

    //加载标签
    ajaxGetAllTags();

    //加载面板数据
    ajaxGetArticleTotal();
    ajaxGetVisitorTotal();
    ajaxGetCommentTotal();
    ajaxGetAllSorts();
})

//填充首页面 blog文章
function showLastArticlesFun(article) {
    $showLastArticle = $("#showLastArticles");
    var html = '<div class="col-xs-6 col-lg-4">\n' +
        '                        <h2 class="omit-line">'+article.title+'</h2>\n' +
        '                        <p class="omit-lines">'+article.digest+'...</p>\n' +
        '                        <p><a class="btn btn-default" href="articleDetail.html?article='+article.id+'" role="button" style="opacity: 0.7;">查看详情 &raquo;</a></p>\n' +
        '                    </div>';
    $showLastArticle.append(html);
}

//填充首页面 life文章
function loadIndexLifeArtile(lifeArticle) {
    html = '<li class="media" style="float: left;">\n' +
        '        <div class="media-left media-middle">\n' +
        '             <a href="articleDetail.html?article='+lifeArticle.id+'">\n' +
        '                <img class="media-object" src="'+getImgHref(lifeArticle.content)+'" style="max-width: 120px;max-height: 120px;">\n' +
        '             </a>\n' +
        '        </div>\n' +
        '        <div class="media-body">\n' +
        '            <a href="articleDetail.html?article='+lifeArticle.id+'">\n' +
        '            <h4 class="media-heading">'+lifeArticle.title+'</h4>\n' +
        '             </a>' +
        lifeArticle.digest.substring(0,120)+"..."+
        '          </div>\n' +
        '     </li>';

    var $show = $("ul[class='media-list']")[0];
    $($show).prepend(html)
}