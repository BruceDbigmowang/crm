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

//分页查询所有账号数据
//定义start表示当前页，初始化start = 1;
//点击下一页start+1 点击上一页 start-1
//page为总页数
//若当前页 = 总页数 即为最后一页
//当前页 = 1 即为第一页

//无条件查询所有

var start = 1;
var pages = 0;
function getUserByPage() {
    start = 1;
    var data = {"start":start};
    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPage",
        async:true,
        success:function(data){
            $("#allUser").html("");
            $("#pageDIV").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
            pages = data.size;

            $("#pageDIV").append("<a href=\"#\" onclick=\"getFirstUserByPage()\">首 页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getPreviousUserByPage()\">上一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getNextUserByPage()\">下一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getLastUserByPage()\">尾 页</a>");
        }
    });
}

function getNextUserByPage() {
    if(start == pages){
        alert("此页为最后一页");
    }else{
        start = start+1;
        var data = {"start":start};
        $.ajax({
            type:"post",
            data:data,
            url:"selectUserByPage",
            async:true,
            success:function(data){
                $("#allUser").html("");
                var user = data.user;
                for(var i = 0 ; i < user.length ; i++){
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getPreviousUserByPage() {
    if(start == 1){
        alert("此页为第一页");
    }else{
        start = start-1;
        var data = {"start":start};
        $.ajax({
            type:"post",
            data:data,
            url:"selectUserByPage",
            async:true,
            success:function(data){
                $("#allUser").html("");
                var user = data.user;
                for(var i = 0 ; i < user.length ; i++){
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getFirstUserByPage() {
    start = 1;
    var data = {"start":start};
    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPage",
        async:true,
        success:function(data){
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
        }
    });
}

function getLastUserByPage() {
    start = pages;
    var data = {"start":start};
    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPage",
        async:true,
        success:function(data){
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
        }
    });
}

// 根据条件查询
function selectByConditionFirstPage() {
    start = 1;
    var account = $("#maintainAccount").val();
    var company = $("#maintainCompany").val();
    var phone = $("#maintainPhone").val();
    var data = {"account":account , "start":start , "company":company , "phone":phone};

    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPageAndCondition",
        async:true,
        success:function(data){
            $("#pageDIV").html("");
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
            pages = data.size;
            $("#pageDIV").append("<a href=\"#\" onclick=\"getFirstUserByPageAndCondition()\">首 页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getPreviousUserByPageAndCondition()\">上一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getNextUserByPageAndCondition()\">下一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getLastUserByPageAndCondition()\">尾 页</a>");
        }
    });

}


function getNextUserByPageAndCondition() {
    if(start == pages){
        alert("此页为最后一页");
    }else{
        start = start+1;
        var account = $("#maintainAccount").val();
        var company = $("#maintainCompany").val();
        var phone = $("#maintainPhone").val();
        var data = {"account":account , "start":start , "company":company , "phone":phone};
        $.ajax({
            type:"post",
            data:data,
            url:"selectUserByPageAndCondition",
            async:true,
            success:function(data){
                $("#allUser").html("");
                var user = data.user;
                for(var i = 0 ; i < user.length ; i++){
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getPreviousUserByPageAndCondition() {
    if(start == 1){
        alert("此页为第一页");
    }else{
        start = start-1;
        var account = $("#maintainAccount").val();
        var company = $("#maintainCompany").val();
        var phone = $("#maintainPhone").val();
        var data = {"account":account , "start":start , "company":company , "phone":phone};
        $.ajax({
            type:"post",
            data:data,
            url:"selectUserByPageAndCondition",
            async:true,
            success:function(data){
                $("#allUser").html("");
                var user = data.user;
                for(var i = 0 ; i < user.length ; i++){
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getFirstUserByPageAndCondition() {
    start = 1;
    var account = $("#maintainAccount").val();
    var company = $("#maintainCompany").val();
    var phone = $("#maintainPhone").val();
    var data = {"account":account , "start":start , "company":company , "phone":phone};
    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPageAndCondition",
        async:true,
        success:function(data){
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
        }
    });
}

function getLastUserByPageAndCondition() {
    start = pages;
    var account = $("#maintainAccount").val();
    var company = $("#maintainCompany").val();
    var phone = $("#maintainPhone").val();
    var data = {"account":account , "start":start , "company":company , "phone":phone};
    $.ajax({
        type:"post",
        data:data,
        url:"selectUserByPageAndCondition",
        async:true,
        success:function(data){
            $("#allUser").html("");
            var user = data.user;
            for(var i = 0 ; i < user.length ; i++){
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"showAllRoles()\">修改角色</a>"+"</td>"+"</tr>");
            }
        }
    });
}

//在模态框中显示所有角色
function showAllRoles() {
    $.ajax({
        type:"post",
        url:"getAllRoles",
        async:false,
        success:function (data) {
            $("#roleTab").html("");
            var roles = data.roles;
            for(var i = 0 ; i < roles.length ; i++){
                $("#roleTab").append("<tr><td><input name=\"rolecheck\" type=\"checkbox\"></td><td>"+roles[i].roleName+"</td></tr>");
            }
        }
    })
}
