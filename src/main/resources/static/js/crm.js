//个人中心的两个功能：1、查询个人数据  2、保存修改个人数据
function getPersonalInfo(){
    $.ajax({
        url:"getPersonInfo",
        type:"post",
        async:false,
        success: function(data){
            var user = data.user;
            $("#userAccount").val(user.account);
            $("#userName").val(user.name);
            $("#userCompany").val(user.company);
            $("#userEmail").val(user.email);
            $("#userPhone").val(user.phone);
            // alert("数据获取成功");
        },
        error: function(data){
            alert("数据获取失败");
        }
    });
}

function savePersonalInfo() {
    var account = $("#userAccount").val();
    var name = $("#userName").val();
    var company = $("#userCompany").val();
    var email = $("#userEmail").val();
    var phone = $("#userPhone").val();
    var password1 = $("#password1").val();
    var password2 = $("#password2").val();
    var url = "savePersonInfo";
    if(password1 !== "" || password2 !== ""){
        if(password1 != password2){
            alert("两次输入的密码不一致");
        }else{
            var data = {"account":account , "name":name , "company":company , "phone":phone , "email":email , "password":password1};
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function (data) {
                    alert(data);
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }
    }else{
        var data = {"account":account , "name":name , "company":company , "phone":phone , "email":email , "password":""};
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function (data) {
                alert(data);
            },
            error:function () {
                alert("程序出错");
            }
        })
    }
}

//账号维护
function getUserByPage() {
    alert("success");
    $.ajax({
        type:"post",
        url:"selectUserByPage",
        async:true,
        success:function(data){
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"</tr>");
            }
        }
    });
}
