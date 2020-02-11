$(document).ready(function () {

    var token = localStorage.getItem("token");

    //请求获得个人信息
    $.ajax({
        //请求方式
        type : "POST",
        //请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        headers:{'token':token},
        //请求地址
        url : window.location.protocol+"//"+window.location.host+"/user/online/getuser.do",
        dataType: "json",
        //请求成功
        success : function(result) {
            //console.log(result)
            if (result.data.length == 0){
                console.log("获取用户信息失败");
                logout();
                return;
            }else {
                //追加显示关于个人信息数据
                console.log("开始追加显示个人信息...")
                var user = result.data;
                $("#user_nickname").text(user.nickname)
                $("#user_nickname2").text(user.nickname)
                $("#user_nickname3").text(user.nickname)
            }

        },
        //请求失败，包含具体的错误信息
        error : function(e){
            console.log(e.status);
            console.log(e.responseText);
        }
    });


})

//点击退出
function logout() {
    localStorage.removeItem("token")
    $.cookie("token",null,{path:"/"})
    window.location.href = "../index.html";
}

//获得当前tab索引
function getCurrentTabIndex(){
    var $tabs = $('#container').children( 'li' );
    var i=0;
    $tabs.each( function() {
        var $tab = $( this );
        if($tab.hasClass('active')){
            return false;
        }else{
            i++;
        }
    } );
    return i;
}