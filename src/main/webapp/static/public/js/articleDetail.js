//url地址前缀
var urlpre = window.location.protocol+"//"+window.location.host+"/";
$(document).ready(function () {

    var articleId = getParamValue("article")
    var views = 0;
    //console.log("articleId"+articleId)
    $.ajax({
        //请求方式
        type : "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url : urlpre+"article/detail/"+articleId,
        data:"",
        dataType: "json",
        //请求成功
        success : function(data) {

            //渲染文章内容
            $("#articleTitle").text(data.title)
            //console.log("时间戳转换为时间",new Date(data.date))
            $("#articleDate").text(new Date(data.date).format("hh:mm:ss yyyy.MM.dd"))
            views = data.views;
            $("#articleViews").text(data.views)
            $("#test-editormd").html(data.content)
            $("#articleLikeCount").text(data.like_count)

            if (data.author != ""){
                $("#articleAuthor").text(data.author);
            }
            //更新文章的阅读量
            ajaxUpdateArticleViews(views)
            //渲染文章评论
            var commentList = data.commentList;
            $('#articleCommentCount').text(commentList.length)
            //console.log(commentList)
            $.each(commentList,function (index,item) {
                //console.log(item)
                loadArticleComment(item);
            })
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });

    //加载左侧面板信息数据
    ajaxGetAllTags();
    ajaxGetArticleTotal();
    ajaxGetVisitorTotal();
    ajaxGetCommentTotal();
    ajaxGetAllSorts();

});

//加载评论 函数
function loadArticleComment(comment) {
    var html = '<div class="media well" style="color: #0C1021">\n' +
        '                    <div class="media-left">\n' +
        '                        <img class="img-circle media-object" src="static/public/images/myHeadPhoto.jpg" style="width: 80px;height: 80px;" alt="...">\n' +
        '                    </div>\n' +
        '                    <div class="media-body">\n' +
        '                        <h4 class="media-heading">'+comment.name+'</h4>\n' +
        '                        <h6 style="margin-top: 10px;">'+(new Date(comment.date).format("hh:mm:ss yyyy.MM.dd"))+'</h6>\n' +
        '                        <p style="margin-top: 10px;">'+comment.content+'</p>\n' +
        '                    </div>\n' +
        '                    <div class="media-bottom text-right" style="padding-right: 15px">\n' +
        '                        <span style="cursor: pointer;"><i onclick="clickCommentLike(event,'+comment.id+')" class="fa fa-thumbs-o-up" aria-hidden="true">'+comment.like_count+'</i></span>&nbsp;&nbsp;<em>回复</em>\n' +
        '                    </div>\n' +
        '                </div>';
    $("#showComments").append(html)
}

//发送插入评论的请求
function ajaxInsertComment() {

    var articleId = getParamValue("article");
    var name = $("#comment-name").val();
    var email = $("#comment-email").val();
    var content = $("#comment-content").html();
    var date = new Date();
    var text = $("#comment-content").text();
    if((content == ''  || $.trim(content) == "") ){
        alert("输入内容不能为空！");
        $("#comment-content").text("");
        return;
    }
    //console.log("获取",articleId)
    var list = {
        articleId:articleId,
        name:name,
        email:email,
        content:content,
        date:date,
        checked:0
    }

    //console.log(list)
    $.ajax({
        //请求方式
        type : "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url : urlpre+"comment/insert",
        data:JSON.stringify(list),
        dataType: "json",
        //请求成功
        success : function(data) {
            console.log("返回数据",data)
            if(data == 1){
                alert("评论成功，等待管理员审核通过!");
                $("#comment-name").val("");
                $("#comment-email").val("");
                $("#comment-content").html("");
            }else {
                alert("评论失败");
            }
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });
}

//点赞评论小手图标的动态改变
function clickCommentLike(e,id) {
    var res = ($(e.target).attr("class").indexOf("fa-thumbs-o-up"));
    //console.log(res)
    var sum = $(e.target).text();
    sum = Number(sum);
    if(res != -1){
        $(e.target).removeClass("fa-thumbs-o-up")
        $(e.target).addClass("fa-thumbs-up")
        sum = sum + 1;
    }else {
        $(e.target).removeClass("fa-thumbs-up")
        $(e.target).addClass("fa-thumbs-o-up")
        sum = sum -1;
    }
    $(e.target).text(sum)
    ajaxUpdateCommentLikeCount(id,sum);

}
//发送 评论是否被点赞
function ajaxUpdateCommentLikeCount(id,like_count) {
    var url = urlpre+"comment/updateLikeCount";
    var list = {
        id:id,
        like_count:like_count
    }
    ajaxUpdateData(url,list)
}
//喜欢文章图标的动态改变
function clickArticleLike(e) {
    var res = ($(e.target).attr("class").indexOf("fa-heart-o"));
    //console.log(res)
    var $articleLikeCount = $("#articleLikeCount");
    var sum = $articleLikeCount.text();
    if(res != -1){
        $(e.target).removeClass("fa-heart-o")
        $(e.target).addClass("fa-heart")
        sum = Number(sum) +1;
    }else {
        $(e.target).removeClass("fa-heart")
        $(e.target).addClass("fa-heart-o")
        sum = Number(sum)-1;
    }
    $articleLikeCount.text(sum)
    ajaxUpdateArticleLikeCount(sum);
}
//发送是否被喜欢
function ajaxUpdateArticleLikeCount(like_count) {

    var url =urlpre+"article/updateLikeCount";
    var id = getParamValue("article");
    var list = {
        id:id,
        like_count:like_count
    }
    ajaxUpdateData(url,list)
}

//更新阅读量
function ajaxUpdateArticleViews(views){
    var url = urlpre+"article/updateViews";
    var list = {
        id:getParamValue("article"),
        views:views+1
    }
    //console.log(JSON.stringify(list))
    //设置定时器 3s后改变阅读量
    var s = setTimeout(function(){
        $("#articleViews").text(views+1)
        clearTimeout(s)
    },3000)

    ajaxUpdateData(url,list)
}

//抽出的共同代码
function ajaxUpdateData(url,list) {
    $.ajax({
        //请求方式
        type : "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url : url,
        data:JSON.stringify(list),
        dataType: "json",
        success: function(data){
            data == 1 ? console.log("成功更新数据！") : console.log("失败更新数据！");
        },
        //请求失败，包含具体的错误信息
        error : function(e){
            if(e.status != 200){
                console.log(e.status);
                console.log(e.responseText);
            }

        }
    });
}