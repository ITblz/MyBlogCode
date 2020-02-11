//url地址前缀
var urlpre = window.location.protocol+"//"+window.location.host+"/";
var page = 1;
var rows = 5;
var flag = false;
//等到文档加载完成之后，先发送一次请求
$(document).ready(function(){
    ajaxGetGuestBookList();

    $("#guestbookList").scroll(function(){
        var scrollTop=$(this).scrollTop();//滚动条已经滚动的距离（高度）
        var fixHeight=236;
        var thisHeight=$("#guestbookList").height();//文档对象的高度
        if((scrollTop+fixHeight)>=thisHeight && flag == false){
            console.log(flag)
            page = page + 1;
            ajaxGetGuestBookList();
        }
        });
})

function ajaxInsertGuestbook() {
    var name = $("#guestbook-name").val();
    //var email = $("#guestbook-email").val();
    var content = $("#guestbook-content").html();
    var text = $("#guestbook-content").text();
    if(content == null || text == null || $.trim(text) == ""){
        alert("输入不能为空");
        $("#guestbook-content").text("");
        return;
    }

    var list = {
        'name':name,
        'content':content,
        'date':new Date()
    }

    var url = urlpre + "guestbook/insert";
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
            if(data == 1){
                // page = 1;
                // flag = false;
                // $("#guestbookList").empty();
                // ajaxGetGuestBookList();
                alert("留言成功！等待管理员审核通过！");
                $("#guestbook-name").val("")
                $("#guestbook-content").html("");
            }
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

//获取留言 每次获得固定数目的留言
function ajaxGetGuestBookList() {
    var url = urlpre + "guestbook/list/"+page+"/"+rows;
    $.ajax({
        //请求方式
        type : "GET",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        //请求地址
        url : url,
        dataType: "json",
        success: function(data){

            if(data.length <=0){
                loadNoData();
                flag = true;
            }else {
                $.each(data, function (index, item) {
                    loadGuestbook(item);
                })
            }
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
//动态加载 留言 条目
function loadGuestbook(guestbook) {
    console.log("加载留言")
    var html = '<li class="list-group-item" >\n' +
        '                <div class="row well" style="margin: 10px 20px 10px 20px;">\n' +
        '                    <div class="media">\n' +
        '                        <div class="media-left media-middle">\n' +
        '                            <img class="img-circle media-object" src="static/public/images/myHeadPhoto.jpg" style="width: 80px;height: 80px;" alt="...">\n' +
        '                        </div>\n' +
        '                        <div class="media-body">\n' +
        '                            <h4 class="media-heading">'+guestbook.name+'</h4>\n' +
        '                            <h6 style="margin-top: 10px;">'+(new Date(guestbook.date).format("hh:mm:ss yyyy.MM.dd"))+'</h6>\n' +
        '                            <p style="margin-top: 10px;">'+guestbook.content+'</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li>';

    $("#guestbookList").append(html)
}
function loadNoData() {
    console.log("加载留言")
    var html = '<li class="list-group-item" >\n' +
        '                <div class="row well" style="margin: 10px 20px 10px 20px;">\n' +
        '                    <div class="media">\n' +
        '                        <div class="media-body text-center">\n' +
        '                            <p style="margin-top: 10px;">已经到底了~~</p>\n' +
        '                        </div>\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '            </li>';

    $("#guestbookList").append(html)
}