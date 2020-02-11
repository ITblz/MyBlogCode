$(document).ready(function(){

    //初始化 页面头 页脚
    $("#header").load("header.html");
    $("#footer").load("footer.html");

    //菜单项激活
    var timeoutId = setTimeout(function(){
        //console.log("init.js  ",$("#menu li a"))
        $("#menu la").removeClass("active")
        $.each($("#menu li a"),function (index,item) {
            var href = $(item).attr("href");
            //console.log(href,"判断",(location.href).indexOf(href))
            if((location.href).indexOf(href) != -1){
                $(item).parent().addClass("active")
            }
        })

        $('#searchText').bind('keyup', function(event) {
            if (event.keyCode == "13") {
                //回车执行查询
                clickSearch();
            }
        });

        $('#searchText2').bind('keyup', function(event) {
            if (event.keyCode == "13") {
                //回车执行查询
                var t = $('#searchText2').val();
                location.href = 'articleList.html?search='+t;
            }
        });
        clearTimeout(timeoutId)
    },100);
})

