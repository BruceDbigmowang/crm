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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser('+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+')">删除账号</a>'+"</td>"+"</tr>");
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
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+'">删除账号</a>'+"</td>"+"</tr>");
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
                    $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+'">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+'">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td>"+user[i].account+"</td>"+"<td>"+user[i].name+"</td>"+"<td>"+user[i].company+"</td>"+"<td>"+user[i].phone+"</td>"+"<td>"+user[i].email+"</td>"+"<td>"+user[i].role+"</td>"+"<td>"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles('+user[i].account+')">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser( '+user[i].account+'">删除账号</a>'+"</td>"+"</tr>");
            }
        }
    });
}

//在模态框中显示所有角色
function showAllRoles(account) {
    $("#RoleAccount").val(account);
    $.ajax({
        type:"post",
        data:{"account":account},
        url:"getAllRoles",
        async:false,
        success:function (data) {
            $("#roleTab").html("");
            var roles = data.roles;
            var had = data.had;
            for(var i = 0 ; i < roles.length ; i++){
                if(had.includes(roles[i].roleName)){
                    $("#roleTab").append('<tr><td><input name="rolecheck" type="checkbox" value='+roles[i].id+' checked></td><td>'+roles[i].roleName+"</td></tr>");
                }else{
                    $("#roleTab").append('<tr><td><input name="rolecheck" type="checkbox" value='+roles[i].id+'></td><td>'+roles[i].roleName+"</td></tr>");
                }
            }
        }
    })
}

function changeRoles() {
    var account = $("#RoleAccount").val();
    var arr=[];
    $.each($('input[name=rolecheck]:checked'),function(){
        arr.push($(this).val());
    });
    var data = {"account":account , "roles":arr};
    $.ajax({
        type:"post",
        data:data,
        url:"updateRoles",
        async:false,
        success:function (data) {
            if(data.includes("成功")){
                alert(data);
                getUserByPage();
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}


function getRoleCheckbox() {
    $.ajax({
        type:"post",
        url:"onlyGetAllRoles",
        async:false,
        success:function(data){
            var roles = data.roles;
            $("#userRole").html("");
            for(var i = 0 ; i < roles.length ; i++){
                $("#userRole").append('<input type="checkbox" name="userRoleCheck" value="'+roles[i].id+'">'+roles[i].roleName+"<br>");
            }
        }
    })
}

function insertNewAccount() {
    var arr=[];
    $.each($('input[name=userRoleCheck]:checked'),function(){
        arr.push($(this).val());
    });
    var account = $("#userAccount").val();
    var password = $("#userPassword").val();
    var email = $("#userEmail").val();
    var phone = $("#userPhone").val();
    var company = $("#userCompany").val();
    var data = {"account":account , "password":password , "email":email , "phone":phone , "company":company , "roles":arr};
    var errorInfo = "";
    if(account == ""){
        errorInfo = errorInfo+"账号  ";
    }
    if(password == ""){
        errorInfo = errorInfo+"密码  ";
    }
    if (email == ""){
        errorInfo = errorInfo+"邮箱  ";
    }
    if(phone == ""){
        errorInfo = errorInfo+"手机号  ";
    }
    if(arr.length == 0){
        errorInfo = errorInfo+"角色  ";
    }
    if(errorInfo == ""){
        if(!/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(email)){
            alert("邮箱输入的格式不正确");
        }else{
            $.ajax({
                type:"post",
                data:data,
                url:"addAccount",
                async:false,
                success:function(data){
                    alert(data);
                    getUserByPage();
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }
    }else{
        alert(errorInfo+"是必填项或必选项");
    }
}

function deleteUser(account) {
    $.ajax({
        type:"post",
        data:{"account":account},
        url:"deleteUser",
        async:false,
        success:function (data) {
            alert(data);
            getUserByPage();
        },
        error:function(){
            alert("程序出错");
        }
    })
}


//在线尽调相关js代码
function chooseYES() {
    $("#wechat").hide();
}
function chooseNO() {
    $("#wechat").show();
}

//选择各个行业显示不同的内容
function chooseCar() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;">'+"零部件二级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="汽车后市场供应商" style="width: 15px;">'+"汽车后市场供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="发动机">'+"发动机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="轮毂">'+"轮毂"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="曲轴">'+"曲轴"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="连杆">'+"连杆"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="涡轮">'+"涡轮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="缸体/缺盖">'+"缸体/缺盖"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="转向节">'+"转向节"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="齿轮">'+"齿轮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="门铰链">'+"门铰链"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function chooseAir() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;">'+"零部件二级供应商"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;margin-left: 5%;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="蒙皮">'+"蒙皮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="起落架">'+"起落架"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="叶轮">'+"叶轮"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="叶片">'+"叶片"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="发动机">'+"发动机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function chooseMedical() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="骨科类工具">'+"骨科类工具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="植入件">'+"植入件"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="牙科类工具">'+"牙科类工具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function chooseIndustry() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;">'+"零部件二级供应商"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;margin-left: 5%;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="推土机">'+"推土机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="挖掘机">'+"挖掘机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="装载机">'+"装载机"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="履带">'+"履带"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="底盘">'+"底盘"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function chooseMouldl() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="注塑模具">'+"注塑模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="冲压模具">'+"冲压模具"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="锻造模具">'+"锻造模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="压铸模具">'+"压铸模具"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="挤压模具">'+"挤压模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function choose3C() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="零部代加工厂家" style="width: 15px;margin-left: 5%;">'+"零部代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;" value="苹果">'+"苹果"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="华为">'+"华为"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="三星">'+"三星"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;" value="小米">'+"小米"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="OPPO">'+"OPPO"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;" value="其它">'+"其它"+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function chooseOther() {
    $("#bussiness").html("");
    $("#products").html("");
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input id="maked" name="product" type="text" style="width: 100%;border: 0px;" placeholder="请输入您公司的产品">'+"</td>");

    var file = document.getElementById("images");
    // for IE, Opera, Safari, Chrome
    if (file.outerHTML) {
        file.outerHTML = file.outerHTML;
    } else { // FF(包括3.5)
        file.value = "";
    }

    $("#pic").show();
}

function servicShow() {
    $("#hidden1").show();
    $("#hidden2").show();
}
function serviceHide() {
    $("#hidden1").hide();
    $("#hidden2").hide()
}

function ERPShow() {
    $("#erpBrand").show();
}
function ERPHide() {
    $("#erpBrand").hide();
}
function MESShow() {
    $("#mesBrand").show();
}
function MESHide() {
    $("#mesBrand").hide();
}

function chooseShow() {
    $("#brandGSG").show();
}
function chooseHide() {
    $("#brandGSG").hide();
}

function spendShow() {
    $("#repairSpend").show();
}
function spendHide() {
    $("#repairSpend").hide();
}

//点击跳转至下一页
function foreSecond() {
    var link_url="include/fore/homePages/survey/secondPage.html";
    showAtRight(link_url);
}

function foreThird() {
    var link_url="include/fore/homePages/survey/thirdPage.html";
    showAtRight(link_url);
}

function foreFourth() {
    var link_url="include/fore/homePages/survey/fourthPage.html";
    showAtRight(link_url);
}

function foreFifth() {
    var link_url="include/fore/homePages/survey/fifthPage.html";
    showAtRight(link_url);
}

function foreSixth() {
    var link_url="include/fore/homePages/survey/SixthPage.html";
    showAtRight(link_url);
}

function foreSeventh() {
    var link_url="include/fore/homePages/survey/seventhPage.html";
    showAtRight(link_url);
}

function foreEight() {
    var link_url="include/fore/homePages/survey/eighthPage.html";
    showAtRight(link_url);
}

function foreNinth() {
    var link_url="include/fore/homePages/survey/ninthPage.html";
    showAtRight(link_url);
}

function foreTenth() {
    var link_url="include/fore/homePages/survey/tenthPage.html";
    showAtRight(link_url);
}

function foreEleventh() {
    var link_url="include/fore/homePages/survey/eleventhPage.html";
    showAtRight(link_url);
}

function  foreTwelfth() {
    var link_url="include/fore/homePages/survey/twelfthPage.html";
    showAtRight(link_url);
}

//进行在线尽调，对各个页面的内容进行保存，并跳转至下一页面
//第一页
//首先检查输入是否符合要求
// 再保存数据
function sureBeforeSbmit() {
    $.ajax({
        type:"post",
        url:"getVcode",
        async:false,
        success:function(data){
            var vcode = $("#vcode").val();
            if(vcode != ""){
                if(vcode == data){
                    saveCompanyInfoPart();
                }else{
                    alert("验证码不正确");
                }
            }else{
                alert("请输入验证码");
            }
        }
    })
}

function saveCompanyInfoPart() {
    var companyName = $("#companyName").val();
    var contact = $("#contact").val();
    var contactWay = $("#contactWay").val();
    var wechatNum;
    if(wechat == 0){
        wechatNum = $("#wechatNum").val();
    }else{
        wechatNum = $("#contactWay").val();
    }

    var data = {"companyName":companyName , "contact":contact , "contactWay":contactWay , "wechatNum":wechatNum};
    var url = "saveCompanyInfo";
    if(isNaN(companyName)){
        if(isNaN(contact)){
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function (data) {
                    if(data == "OK"){
                        foreSecond();
                    }else{
                        alert(data);
                    }
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }else{
            alert("联系人填写错误，不能是数字");
        }
    }else{
        alert("公司名称填写错误，不能是数字");
    }
}

//第二页将所有数据保存并跳转至下一页
function saveSecondPart() {
    var registerMoney = $("#registerMoney").val();
    var establishTime = $("#establishTime").val();
    var bussniessNature = $("#bussinessNature option:selected").text();
    var sonCompanyNum = $("input[name='son']:checked").val();
    var employeeNum = $("input[name='employee']:checked").val();
    var data = {"registerMoney":registerMoney , "establishTime":establishTime , "bussinessNature":bussniessNature , "sonCompanyNum":sonCompanyNum , "employeeNum":employeeNum};
    var url = "updateCompanyInfoFirst";
    if(registerMoney != ""){
        if(isNaN(registerMoney)){
            alert("注册资金填写错误，只能填写数字");
        }else{
            if(establishTime != ""){
                if(isNaN(establishTime)){
                    alert("公司成立时间填写错误,只能填数字");
                }else{
                    $.ajax({
                        type:"post",
                        data:data,
                        url:url,
                        async:false,
                        success:function(data){
                            if(data == "OK"){
                                foreThird();
                            }else{
                                alert(data);
                            }
                        },
                        error:function(){
                            alert("程序出错");
                        }
                    })
                }
            }else{
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            toNext();
                        }else{
                            alert(data);
                        }
                    },
                    error:function(){
                        alert("程序出错");
                    }
                })
            }
        }
    }else{
        if(establishTime != ""){
            if(isNaN(establishTime)){
                alert("公司成立时间填写错误,只能填数字");
            }else{
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            toNext();
                        }else{
                            alert(data);
                        }
                    },
                    error:function(){
                        alert("程序出错");
                    }
                })
            }
        }else{
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function(data){
                    if(data == "OK"){
                        toNext();
                    }else{
                        alert(data);
                    }
                },
                error:function(){
                    alert("程序出错");
                }
            })
        }
    }
}



function saveFourthPage(){
    var industry = $("input[name='bussiness']:checked").val();
    var industryNature = $("input[name='bussinessNature']:checked").val();
    var product ;
    if(industry == "其它"){
        product = $("#maked").val();
    }else{
        product = $("input[name='product']:checked").val();
    }
    var picName = "";
    var files = document.getElementById("images").files;
    for(var i=0; i< files.length; i++){
        if(i == files.length-1){
            picName = picName+ files[i].name;
        }else{
            picName = picName+ files[i].name+",";
        }
    };
    var data = {"industry":industry , "industryNature":industryNature , "product":product , "picture":picName};
    var url="updateCompanyInfoSecond";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                foreFourth();
            }else{
                alert(data);
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}
//第四页保存数据并跳转至下一页
function saveFifthPage(){
    var means = $("input[name='way']:checked").val();
    var toolManage = $("input[name='manage']:checked").val();
    var facilitatorName = $("#facilitatorName").val();
    var problem = $("#problem").val();
    var consume = $("input[name='expend']:checked").val();
    var principal = $("#principal").val();
    var mobile = $("#mobile").val();
    var email = $("#email").val();
    var ask = $("input[name='ask']:checked").val();
    var data = {"means":means , "toolManage":toolManage , "facilitatorName":facilitatorName , "problem":problem , "consume":consume , "principal":principal ,
        "mobile":mobile , "email":email , "ask":ask};
    var url = "saveKnowMorePage";
    if(toolManage == "有"){
        if(isNaN(facilitatorName)){
            if(isNaN(problem)){
                if(isNaN(mobile)){
                    alert("手机号填写错误，手机号只能是数字");
                }else{
                    if(email != ""){
                        if(email.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1){
                            $.ajax({
                                type:"post",
                                data:data,
                                url:url,
                                async:false,
                                success:function(data){
                                    if(data == "OK"){
                                        toNext();
                                    }else{
                                        alert(data);
                                    }
                                },
                                error:function () {
                                    alert("程序出错");
                                }
                            })
                        }else{
                            alert("错误的邮件格式");
                        }
                    }else{
                        $.ajax({
                            type:"post",
                            data:data,
                            url:url,
                            async:false,
                            success:function(data){
                                if(data == "OK"){
                                    foreFifth();
                                }else{
                                    alert(data);
                                }
                            },
                            error:function () {
                                alert("程序出错");
                            }
                        })
                    }
                }
            }else{
                alert("您之前碰到的主要问题不能填数字");
            }
        }else{
            alert("服务商名称不能填数字");
        }
    }else{
        if(isNaN(mobile)){
            alert("手机号填写错误，手机号只能是数字");
        }else{
            if(email != ""){
                if(email.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) != -1){
                    $.ajax({
                        type:"post",
                        data:data,
                        url:url,
                        async:false,
                        success:function(data){
                            if(data == "OK"){
                                foreFifth();
                            }else{
                                alert(data);
                            }
                        },
                        error:function () {
                            alert("程序出错");
                        }
                    })
                }else{
                    alert("错误的邮件格式");
                }
            }else{
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            foreFifth();
                        }else{
                            alert(data);
                        }
                    },
                    error:function () {
                        alert("程序出错");
                    }
                })
            }
        }
    }
}

//第五页  保存并跳转至下一页
function saveSevenPage() {
    var production = $("input[name='output']:checked").val();
    var market = $("input[name='market']:checked").val();
    var competitor = $("#competitor").val();
    var project = $("input[name='plan']:checked").val();
    var data = {"production":production , "market":market , "competitor":competitor , "project":project};
    var url = "saveManufactureOne";
    if(competitor != ""){
        if(isNaN(competitor)){
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function (data) {
                    if(data == "OK"){
                        foreSixth();
                    }else{
                        alert(data);
                    }
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }else{
            alert("您公司的主要竞争对手,不能填写数字");
        }
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function (data) {
                if(data == "OK"){
                    foreSixth();
                }else{
                    alert(data);
                }
            },
            error:function () {
                alert("程序出错");
            }
        })
    }
}

//第六页保存数据并跳转至下一页
function saveEightPage(){
    var assetType_value =[];
    $('input[name="assetType"]:checked').each(function(){
        assetType_value.push($(this).val());
    });
    var assetType = "";
    for(var i = 0 ; i < assetType_value.length ; i++){
        if(i == assetType_value.length-1){
            assetType = assetType+assetType_value[i];
        }else{
            assetType = assetType+assetType_value[i]+",";
        }
    }

    var assetSource_value =[];
    $('input[name="assetSource"]:checked').each(function(){
        assetSource_value.push($(this).val());
    });
    var assetSource = "";
    for(var i = 0 ; i < assetSource_value.length ; i++){
        if(i == assetSource_value.length-1){
            assetSource = assetSource+assetSource_value[i];
        }else{
            assetSource = assetSource+assetSource_value[i]+",";
        }
    }
    var brand = $("#brand").val();
    var assetNum = $("input[name='assetNum']:checked").val();
    var life = $("input[name='life']:checked").val();
    var form = $("input[name='format']:checked").val();
    var data = {"assetType":assetType , "assetSource":assetSource , "brand":brand , "assetNum":assetNum , "life":life , "form":form };
    var url="saveManufactureTwo";
    if(brand != ""){
        if(isNaN(brand)){
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function(data){
                    if(data == "OK"){
                        foreSeventh();
                    }else{
                        alert(data);
                    }
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }else{
            alert("设备品牌填写有误,不能填写数字");
        }
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function(data){
                if(data == "OK"){
                    foreSeventh();
                }else{
                    alert(data);
                }
            },
            error:function () {
                alert("程序出错");
            }
        })
    }
}


//第七页保存数据并跳转至下一页
function saveNinthPage() {
    var buyMoney = $("input[name='buyMoney']:checked").val();

    var buySource_value =[];
    $('input[name="origin"]:checked').each(function(){
        buySource_value.push($(this).val());
    });
    var buySource = "";
    for(var i = 0 ; i < buySource_value.length ; i++){
        if(i == buySource_value.length-1){
            buySource = buySource+buySource_value[i];
        }else{
            buySource = buySource+buySource_value[i]+",";
        }
    }

    var buyBrand_value =[];
    $('input[name="buyBrand"]:checked').each(function(){
        buyBrand_value.push($(this).val());
    });
    var buyBrand = "";
    for(var i = 0 ; i < buyBrand_value.length ; i++){
        if(i == buyBrand_value.length-1){
            buyBrand = buyBrand+buyBrand_value[i];
        }else{
            buyBrand = buyBrand+buyBrand_value[i]+",";
        }
    }
    var buyModel_value =[];
    $('input[name="buyModel"]:checked').each(function(){
        buyModel_value.push($(this).val());
    });
    var buyModel = "";
    for(var i = 0 ; i < buyModel_value.length ; i++){
        if(i == buyModel_value.length-1){
            buyModel = buyModel+buyModel_value[i];
        }else{
            buyModel = buyModel+buyModel_value[i]+",";
        }
    }
    var manageModel_value =[];
    $('input[name="manageModel"]:checked').each(function(){
        manageModel_value.push($(this).val());
    });
    var manageModel = "";
    for(var i = 0 ; i < manageModel_value.length ; i++){
        if(i == manageModel_value.length-1){
            manageModel = manageModel+manageModel_value[i];
        }else{
            manageModel = manageModel+manageModel_value[i]+",";
        }
    }
    var data = {"buyMoney":buyMoney , "buySource":buySource , "buyBrand":buyBrand , "buyModel":buyModel , "manageModel":manageModel};
    var url = "saveManufactureThree";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function (data) {
            if(data == "OK"){
                foreEight();
            }else{
                alert(data);
            }
        },
        error:function () {
            alert("程序出错");
        }
    })

}

//第八页，保存数据并跳转至下一页
function saveTenthPage() {
    var ztzb = $("#ztzb").val();
    var xdzb = $("#xdzb").val();
    var szzb = $("#szzb").val();
    var qtOne = $("#qtOne").val();
    var cdpzb = $("#cdpzb").val();
    var xdpzb = $("#xdpzb").val();
    var tdpzb = $("#tdpzb").val();
    var qtTwo = $("#qtTwo").val();
    var cbn = $("#cbn").val();
    var pcd = $("#pcd").val();
    var hjzb = $("#hjzb").val();

    var hasGSG = $("input[name='hasGSG']:checked").val();

    var gsgBrand_value =[];
    $('input[name="gsgBrand"]:checked').each(function(){
        gsgBrand_value.push($(this).val());
    });
    var gsgBrand = "";
    for(var i = 0 ; i < gsgBrand_value.length ; i++){
        if(i == gsgBrand_value.length-1){
            gsgBrand = gsgBrand+gsgBrand_value[i];
        }else{
            gsgBrand = gsgBrand+gsgBrand_value[i]+",";
        }
    }
    var data = {"ztzb":ztzb , "xdzb":xdzb , "szzb":szzb , "qtOne":qtOne , "cdpzb":cdpzb , "xdpzb":xdpzb , "tdpzb":tdpzb , "qtTwo":qtTwo , "cbn":cbn , "pcd":pcd , "hjzb":hjzb ,
        "hasGSG":hasGSG , "gsgBrand":gsgBrand };
    var url = "saveDataOne";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                foreNinth();
            }else{
                alert(data);
            }
        },
        error:function(){
            alert("程序出错");
        }
    })
}


//第9页保存数据并跳转至下一页
function saveEleventhPage() {
    var stockMoney = $("input[name='stockMoney']:checked").val();

    var stockOne = $("#stockOne").val();
    var stockTwo = $("#stockTwo").val();
    var stockThree = $("#stockThree").val();
    var stockFour = $("#stockFour").val();
    var normTool = $("#normTool").val();
    var unnormTool = $("#unnormTool").val();
    var data = {"stockMoney":stockMoney , "stockOne":stockOne , "stockTwo":stockTwo , "stockThree":stockThree , "stockFour":stockFour , "normTool":normTool , "unnormTool":unnormTool };
    var url = "saveDataTwo";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        success:function (data) {
            if(data == "OK"){
                foreTenth();
            }else{
                alert(data);
            }
        },
        error:function(){
            alert("程序出错");
        }
    })
}

//第10页，保存数据并跳转至下一页
function saveTwelfthPage() {
    var hasERP = $("input[name='erp']:checked").val();
    var erpBrand = $("#erpBrand2").val();
    var hasMES = $("input[name='mes']:checked").val();
    var mesBrand = $("#mesBrand2").val();
    var shiftManage = $("input[name='arrange']:checked").val();
    var productRest = $("input[name='rest']:checked").val();
    var productNum = $("#productNum").val();
    var data = {"hasERP":hasERP , "erpBrand":erpBrand , "hasMES":hasMES , "mesBrand":mesBrand , "shiftManage":shiftManage , "productRest":productRest , "productNum":productNum };
    var url = "saveDataThree";
    if(hasERP == "是"){
        if(isNaN(erpBrand)){
            if(hasMES == "是"){
                if(isNaN(mesBrand)){
                    $.ajax({
                        type:"post",
                        data:data,
                        url:url,
                        async:false,
                        success:function(data){
                            if(data == "OK"){
                                foreEleventh();
                            }else{
                                alert(data);
                            }
                        },
                        error:function () {
                            alert("程序出错");
                        }
                    })
                }else{
                    alert("MES品牌不能填数字");
                }
            }else{
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            foreEleventh();
                        }else{
                            alert(data);
                        }
                    },
                    error:function () {
                        alert("程序出错");
                    }
                })
            }
        }else{
            alert("ERP品牌不能填写数字");
        }
    }else{
        if(hasMES == "是"){
            if(isNaN(mesBrand)){
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            foreEleventh();
                        }else{
                            alert(data);
                        }
                    },
                    error:function () {
                        alert("程序出错");
                    }
                })
            }else{
                alert("MES品牌不能填数字");
            }
        }else{
            $.ajax({
                type:"post",
                data:data,
                url:url,
                async:false,
                success:function(data){
                    if(data == "OK"){
                        foreEleventh();
                    }else{
                        alert(data);
                    }
                },
                error:function () {
                    alert("程序出错");
                }
            })
        }
    }
}


//第11页，保存数据并跳转至下一页
function saveThirteenPage() {
    var stockPerson = $("#stockPerson").val();
    var grantPerson = $("#grantPerson").val();
    var grantWay = $("input[name='grantWay']:checked").val();
    var returnWay = $("input[name='recycleWay']:checked").val();
    var payCycle = $("input[name='payCycle']:checked").val();
    var payWay = $("input[name='payWay']:checked").val();
    var data = {"stockPerson":stockPerson , "grantPerson":grantPerson ,"grantWay":grantWay , "returnWay":returnWay , "payCycle":payCycle , "payWay":payWay };
    var url = "saveDataFour";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function (data) {
            if(data == "OK"){
                foreTwelfth();
            }else{
                alert(data);
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}
//第12页保存数据并返回主页
function saveFourteenthPage() {
    var repair = $("input[name='repair']:checked").val();
    var repairSpend = $("#repairSpend2").val();
    var optimize = $("input[name='optimize']:checked").val();

    var appeal_value =[];
    $('input[name="appeal"]:checked').each(function(){
        appeal_value.push($(this).val());
    });
    var appeal = "";
    for(var i = 0 ; i < appeal_value.length ; i++){
        if(i == appeal_value.length-1){
            appeal = appeal+appeal_value[i];
        }else{
            appeal = appeal+appeal_value[i]+",";
        }
    }
    var data = {"repair":repair , "repairSpend":repairSpend , "optimize":optimize , "appeal":appeal};
    var url="saveDataFive";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                alert("数据保存成功");
                window.location.href="homePage";
            }else{
                alert(data);
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}

function createSalePlan() {
    var salePlanNumber = $("#salePlanNumber").val();
    var costEstimate = $("#costEstimate").val();
    var costType = $("#costType option:selected").text();
    var costCenter = $("#costCenter").val();
    var company = $("#company").val();
    var amount = $("#amount").val();
    var applied = $("#applied").val();
    var used = $("#used").val();
    var data = {"salePlanNumber":salePlanNumber , "costEstimate":costEstimate , "costType":costType , "costCenter":costCenter , "company":company , "amount":amount , "applied":applied , "used":used};
    var url = "createNewSalePlan";
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
//格式化时间显示 yyyy-MM-dd hh:mm:ss
function formatDateTime(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d + ' ' + h + ':' + minute + ':' + second;
}

//格式化时间显示 yyyy-MM-dd hh:mm:ss
function formatDate(inputTime) {
    var date = new Date(inputTime);
    var y = date.getFullYear();
    var m = date.getMonth() + 1;
    m = m < 10 ? ('0' + m) : m;
    var d = date.getDate();
    d = d < 10 ? ('0' + d) : d;
    var h = date.getHours();
    h = h < 10 ? ('0' + h) : h;
    var minute = date.getMinutes();
    var second = date.getSeconds();
    minute = minute < 10 ? ('0' + minute) : minute;
    second = second < 10 ? ('0' + second) : second;
    return y + '-' + m + '-' + d ;
}

var salestart = 1;
var salePages;
function findAllSalePlan() {
    salestart = 1;
    $.ajax({
        type:"post",
        data:{"start":salestart},
        url:"getAllSalePlan",
        async:false,
        success:function(data){
            $("#salePlanData").html("");
            var result = data.result;
            if(result == "暂无数据"){
                alert("暂无数据")
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].costEstimate+"</td>"+"<td>"+salePlans[i].costType+"</td>"+"<td>"+salePlans[i].costCenter+"</td>"+"<td>"+salePlans[i].amount+"</td>"+"<td>"+salePlans[i].appliedAmount+"</td>"+"<td>"+salePlans[i].usedAmount+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>")
                }
            }
        }
    })
}

function findAllSalePlanNext() {
    salestart = salestart +1;
    if(salestart > salePages){
        alert("此页为最后一页");
    }else{
        $.ajax({
            type:"post",
            data:{"start":salestart},
            url:"getAllSalePlan",
            async:false,
            success:function(data){
                $("#salePlanData").html("");
                var result = data.result;
                if(result == "暂无数据"){
                    alert("暂无数据")
                }else{
                    var salePlans = data.salePlans;
                    for(var i = 0 ; i < salePlans.length ; i++){
                        $("#salePlanData").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].costEstimate+"</td>"+"<td>"+salePlans[i].costType+"</td>"+"<td>"+salePlans[i].costCenter+"</td>"+"<td>"+salePlans[i].amount+"</td>"+"<td>"+salePlans[i].appliedAmount+"</td>"+"<td>"+salePlans[i].usedAmount+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>")
                    }
                }
            }
        })
    }
}

function findAllSalePlanPrevious() {
    salestart = salestart - 1;
    if(salestart == 0){
        alert("此页为第一页");
    }else{
        $.ajax({
            type:"post",
            data:{"start":salestart},
            url:"getAllSalePlan",
            async:false,
            success:function(data){
                $("#salePlanData").html("");
                var result = data.result;
                if(result == "暂无数据"){
                    alert("暂无数据")
                }else{
                    var salePlans = data.salePlans;
                    for(var i = 0 ; i < salePlans.length ; i++){
                        $("#salePlanData").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].costEstimate+"</td>"+"<td>"+salePlans[i].costType+"</td>"+"<td>"+salePlans[i].costCenter+"</td>"+"<td>"+salePlans[i].amount+"</td>"+"<td>"+salePlans[i].appliedAmount+"</td>"+"<td>"+salePlans[i].usedAmount+"</td>"+"<td>"+formatDateTime
                        (salePlans[i].createTime)+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>")
                    }
                }
            }
        })
    }
}

function findAllSalePlanFirst() {
    salestart = 1;
    $.ajax({
        type:"post",
        data:{"start":salestart},
        url:"getAllSalePlan",
        async:false,
        success:function(data){
            $("#salePlanData").html("");
            var result = data.result;
            if(result == "暂无数据"){
                alert("暂无数据")
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].costEstimate+"</td>"+"<td>"+salePlans[i].costType+"</td>"+"<td>"+salePlans[i].costCenter+"</td>"+"<td>"+salePlans[i].amount+"</td>"+"<td>"+salePlans[i].appliedAmount+"</td>"+"<td>"+salePlans[i].usedAmount+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>")
                }
            }
        }
    })
}

function findAllSalePlanLast() {
    salestart = salePages;
    $.ajax({
        type:"post",
        data:{"start":salestart},
        url:"getAllSalePlan",
        async:false,
        success:function(data){
            $("#salePlanData").html("");
            var result = data.result;
            if(result == "暂无数据"){
                alert("暂无数据")
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].costEstimate+"</td>"+"<td>"+salePlans[i].costType+"</td>"+"<td>"+salePlans[i].costCenter+"</td>"+"<td>"+salePlans[i].amount+"</td>"+"<td>"+salePlans[i].appliedAmount+"</td>"+"<td>"+salePlans[i].usedAmount+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>")
                }
            }
        }
    })
}

function changeSelectWay() {
    var selectWay = $("#selectWay option:selected").val();
    if(selectWay == "1"){
        $("#salePlanNumberSearch").show();
        $("#saleCreateTimeSearch").hide();
    }else if(selectWay == "2"){
        $("#salePlanNumberSearch").hide();
        $("#saleCreateTimeSearch").show();
    }
}
var writeSaleStart = 1;
function findAllSalePlanWrite() {
    writeSaleStart = 1;
    $.ajax({
        type:"post",
        data:{"start":writeSaleStart},
        url:"getAllSalePlan",
        async:false,
        success:function (data) {
            $("#salePlanWrite").html("");
            var result = data.result;
            if(result == "暂无数据"){
                alert("暂无数据")
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanWrite").append("<tr>"+"<td>"+salePlans[i].salesPlanNumber+"</td>"+"<td>"+salePlans[i].salesPlanDesc+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].totalStatus+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>");
                }
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}

function clickGetting() {
    var contactWay = $("#contactWay").val();
    if(contactWay == ""){
        alert("输入的手机号不能为空");
        return false;
    }else {
        if (isNaN(contactWay)) {
            alert("手机号码填写错误,含有非法字符,手机号只能是数字");
        } else {
            sendCode();
            var btn = $(this);
            var count = 60;
            var resend = setInterval(function(){
                count--;
                if (count > 0){
                    btn.val(count+"秒后可重新获取");
                    document.getElementById("getting").innerText=count+"秒";
                    $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});


                }else {
                    clearInterval(resend);
                    $("#getting").attr('disabled',false);
                    document.getElementById("getting").innerText="获取验证码";
                }
            }, 1000);
            btn.attr('disabled',true).css('cursor','allowed');
        }
    }
}

function sendCode() {
    var contactWay = $("#contactWay").val();

    $.ajax({
        type:"post",
        data:{"contactWay":contactWay},
        url:"sendVcode",
        async:false,
        success:function(data){
            alert(data);
        },
        error:function () {
            alert("获取验证码失败");
        }
    })
}

function saleWrite() {
    var number = $("#saleWriteNO").val();
    var name = $("#saleWriteName").val();
    var describe = $("#saleWriteDesc").val();
    var data = {"namber":number , "name":name , "describe":describe};
    var url = "";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                alert("销售计划创建成功");
            }else{
                alert(data);
            }
        },
        error:function(){
            alert("程序出错");
        }
    })
}


function findSalePlanByNO(number) {

    $.ajax({
        type:"post",
        data:{"number":number},
        url:"findSalePlanByNumber",
        async:false,
        success:function(data){
            $("#salePlanWrite").html("");
            var result = data.result;
            if(result == "暂无数据"){
                alert("暂无数据");
            }else{
                var salePlans = data.salesPlans;
                    $("#salePlanWrite").append("<tr>"+"<td>"+salePlans.no+"</td>"+"<td>"+salePlans.describe+"</td>"+"<td>"+salePlans.company+"</td>"+"<td>"+salePlans.status+"</td>"+"<td>"+salePlans.creater+"</td>"+"<td>"+formatDateTime(salePlans.createTime)+"</td>");

            }
        }
    })
}

function findSalePlanByCompany() {
    var number = $("#planWriteSearch").val();
    $.ajax({
        type:"post",
        data:{"number":number},
        url:"findSalePlanByCompany",
        async:false,
        success:function(data){
            $("#salePlanWrite").html("");
            var result = data.result;
            if(result == "暂无数据"){
                findSalePlanByNO(number);
            }else{
                var salePlans = data.salesPlans;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanWrite").append("<tr>"+"<td>"+salePlans[i].no+"</td>"+"<td>"+salePlans[i].describe+"</td>"+"<td>"+salePlans[i].company+"</td>"+"<td>"+salePlans[i].status+"</td>"+"<td>"+salePlans[i].creater+"</td>"+"<td>"+formatDateTime(salePlans[i].createTime)+"</td>");
                }
            }
        }
    })
}

function showAllCustomer() {
    $.ajax({
        type:"post",
        url:"findAllCustomer",
        async:true,
        success:function(data){
            $("#allCustomer").html("");
            for(var i = 0 ; i < data.length ; i++){
                $("#allCustomer").append("<tr>"+"<td>"+'<input id="checklist" type="checkbox" name="chooseOne" value="'+data[i].id+'">'+"</td>"+"<td>"+data[i].companyName+"</td>"+"<td>"+data[i].establishTime+"</td>"+"<td>"+data[i].registerMoney+"</td>"+"<td>"+data[i].contact+"</td>"+"<td>"+data[i].contactWay+"</td>"+"<td>"+'<a href="downloadCustomerByExcel?cid='+data[i].id+'">下载</a>'+"</td>"+"</tr>");
            }
        }
    })
}

function downloadAll() {
    window.location.href="downloadAllCustomerByExcel";
}

function downloadBatch() {
    var ids = $('input[name=chooseOne]');
    var arr = [];
    ids.each(function () {
        //获取当前元素的勾选状态
        if ($(this).prop("checked")) {
            arr.push($(this).val());
        }
    });
    window.location.href="downloadBatchCustomerByExcel?cids="+arr;
}

function sureCompany() {
    var salePlanNum = $("#salePlanNumber").val();
    $.ajax({
        type:"post",
        data:{"salePlanNum":salePlanNum},
        url:"findCompanyBySalePlan",
        async:false,
        success:function (data) {
            if(data == "F"){
                alert("销售计划编号输入有误");
            }else{
                $("#company").val(data);
            }
        },
        error:function(){
            alert("程序出错");
        }
    })
}

function getAllTraveler() {
    var str = $("#traveller").val();
    var traveller = str.match(/\d+/g);
    var salePlanNum = $("#salePlanNumber").val();
    var company = $("#company").val();
    var target = $("#target").val();
    var province = $("#province").text();
    var city = $("#city").text();
    var address = $("#address").val();
    var bdate = $("#bdate").val();
    var edate = $("#edate").val();
    var data = {"salePlanNum":salePlanNum , "company":company , "target":target , "province":province , "city":city ,
    "address":address , "bdate":bdate , "edate":edate , "traveller":traveller};
    var url = "saveTravelPlan";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            alert(data);
        }
    })
}

function saveVisit() {
    var salePlanNum = $("#salePlanNumber").val();
    var company = $("#company").val();
    var target = $("#target").val();
    var bdate = $("#bdate").val();
    var edate = $("#edate").val();
    var str = $("#visitor").val();
    var traveller = str.match(/\d+/g);
    var data = {"salePlanNum":salePlanNum , "company":company , "target":target , "bdate":bdate , "edate":edate , "visitor":traveller};
    var url = "createVisitSchedule";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                alert("数据保存成功");
            }else{
                alert(data);
            }
        },
        error:function(){
            alert("程序出错");
        }
    })
}

function createExpense() {
    var costNum = $("#costNum").val();
    var costType = $("#costType").val();
    var employee = $("#employee").val();
    var dept = $("#dept").val();
    var costDate = $("#costDate").val();
    var errorInfo = "";
    if(costNum == ""){
        errorInfo = errorInfo + "费用单号  ";
    }
    if(costType == 0){
        errorInfo = errorInfo +"源单类型  "
    }
    if(employee == ""){
        errorInfo = errorInfo +"负责人  ";
    }
    if(dept == 0){
        errorInfo = errorInfo+"负责部门  ";
    }
    if(costDate == ""){
        errorInfo = errorInfo +"费用日期  "
    }
    if(errorInfo != ""){
        alert(errorInfo+"未填写");
    }else{
        var data = {"costNum":costNum , "costType":costType , "employee":employee , "dept":dept , "costDate":costDate};
        var url = "createExpenseHead";
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function(data){
                if(data == "OK"){
                    alert("数据保存成功");
                }else{
                    alert(data);
                }
            },
            error:function () {
                alert("程序出错");
            }
        })
    }
}

function passValue(hId){
    $("#HID").val(hId);
}

function showAllExpenseHead() {
    $.ajax({
        type:"post",
        url:"getAllExpenseHead",
        async:true,
        success:function(data){
            $("#expenseData").html("");
            for(var i = 0 ; i < data.length ; i++){
                var num = i+1;
                $("#expenseData").append("<tr>"+"<td>"+num+"</td>"+"<td>"+data[i].expenseNum+"</td>"+"<td>"+data[i].leafType+"</td>"+"<td>"+data[i].principal+"</td>"+"<td>"+data[i].dept+"</td>"+"<td>"+formatDateTime(data[i].expenseDate)+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal2\" onclick=\"passValue("+data[i].id+")\">编辑</a>"+"</td>"+"</tr>")
            }
        }
    })
}

function writeMore() {
    var hId = $("#HID").val();
    var spendType = $("#spendType").val();
    var happenDate = $("#happenDate").val();
    var person = $("#person").val();
    var sdept = $("#sdept").val();
    var customer = $("#customer").val();
    var samount = $("#samount").val();
    var appliedAmount = $("#appliedAmount").val();
    var bamount = $("#bAmount").val();
    var budgetNum = $("#budgetNum").val();
    var data = {"hId":hId , "spendType":spendType , "happenDate":happenDate , "person":person , "sdept":sdept , "customer":customer , "samount":samount
    ,"appliedAmount":appliedAmount , "bamount":bamount , "budget":budgetNum};
    var url = "writeExpenseDetail";
    var errorInfo = "";
    if(spendType == 0){
        errorInfo = errorInfo+"费用类型  ";
    }
    if(happenDate == ""){
        errorInfo = errorInfo+"发生日期  ";
    }
    if(person == ""){
        errorInfo = errorInfo + "职员  ";
    }
    if(sdept == ""){
        errorInfo = errorInfo + "承担部门  ";
    }
    if(customer == ""){
        errorInfo = errorInfo + "承担客户  ";
    }
    if(samount == ""){
        errorInfo = errorInfo +"金额  ";
    }
    if(appliedAmount == ""){
        errorInfo = errorInfo+"申请金额  ";
    }
    if(bamount == ""){
        errorInfo = errorInfo+"报销金额  ";
    }
    if(budgetNum == ""){
        errorInfo = errorInfo + "预算编号  ";
    }
    if(errorInfo != ""){
        alert(errorInfo +"未填写");
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function (data) {
                if(data == "OK"){
                    alert("数据保存成功");
                }else{
                    alert(data);
                }
            },
            error:function () {
                alert("程序出错");
            }
        })
    }

}


//费用页面加载后  判断登录者账号信息  看其是何身份
//若是提流程的  可显示所有与其相关的流程
//若是审批的  则显示所有需要审批的流程
//用于费用页面
function judgeIdentity() {
    $.ajax({
        type:"post",
        url:"sureIdentity",
        async:false,
        success:function (data) {
            if(data == 1){
                getExpenseList();
            }else if(data == 2){
                getAllApprove();
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}

function getAllApprove() {
    $.ajax({
        type:"post",
        url:"getAllNeedAppprove",
        async:false,
        success:function (data) {
            if(data =="暂无数据"){
                $("#expenseData").html("");
                // alert("暂无数据");
            }else{
                $("#expenseData").html("");
                for(var i = 0 ; i < data.length ; i++){
                    var num = i+1;
                    $("#expenseData").append("<tr>"+"<td>"+num+"</td>"+"<td>"+data[i].expenseNum+"</td>"+"<td>"+data[i].leafType+"</td>"+"<td>"+data[i].principal+"</td>"+"<td>"+data[i].dept+"</td>"+"<td>"+formatDateTime(data[i].expenseDate)+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal3\" onclick=\"getTotalInfo("+data[i].id+")\">审批</a>"+"</td>"+"</tr>")
                }
            }
        }
    })
}

function getExpenseList() {
    var status = "I";
    $.ajax({
        type:"post",
        data:{"status":status},
        url:"getAllApproving",
        async:false,
        success:function (data) {
            if(data =="暂无数据"){
                $("#expenseData").html("");
                // alert("暂无数据");
            }else{
                $("#expenseData").html("");
                for(var i = 0 ; i < data.length ; i++){
                    var num = i+1;
                    $("#expenseData").append("<tr>"+"<td>"+num+"</td>"+"<td>"+data[i].expenseNum+"</td>"+"<td>"+data[i].leafType+"</td>"+"<td>"+data[i].principal+"</td>"+"<td>"+data[i].dept+"</td>"+"<td>"+formatDateTime(data[i].expenseDate)+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal2\" onclick=\"passValue("+data[i].id+")\">编辑</a>"+"</td>"+"</tr>")
                }
            }
        }
    })
}



function getTotalInfo(hId) {
    $.ajax({
        type:"post",
        data:{"hId":hId},
        url:"getExpenseTotal",
        async:false,
        success:function (data) {
            $("#headId").val(hId);
            var head = data.head;
            var detail = data.detail;
            $("#spendNum").val(head.expenseNum);
            $("#newSpendType").val(detail.expenseType);
            $("#newLeafType").val(head.leafType);
            $("#newBudgetNum").val(detail.budgetNum);
            $("#principal").val(head.principal);
            $("#newDept").val(head.dept);
            $("#newEmployee").val(detail.employee);
            $("#acceptDept").val(detail.dept);
            $("#acceptCustomer").val(detail.customer);
            $("#amountOne").val(detail.amount);
            $("#amountTwo").val(detail.applyAmount);
            $("#amountThree").val(detail.reimburseAmount);
            $("#spendDate").val(formatDateTime(head.expenseDate));
            $("#occurDate").val(formatDateTime(detail.happenDate));
        }
    })
}

function expensePass() {
    var hId = $("#headId").val();
    var status = "P";
    $.ajax({
        type:"post",
        data:{"hId":hId ,"status":status},
        url:"expenseCheck",
        async:false,
        success:function (data) {
            alert(data);
            getAllApprove();
        },
        error:function(){
            alert("程序出错");
        }
    })
}

function expenseRefuse() {
    var hId = $("#headId").val();
    var status = "R";
    $.ajax({
        type:"post",
        data:{"hId":hId ,"status":status},
        url:"expenseCheck",
        async:false,
        success:function (data) {
            alert(data);
            getAllApprove();
        },
        error:function(){
            alert("程序出错");
        }
    })
}


function writeIncomeHead() {
    var incomeNum = $("#incomeNum").val();
    var principal = $("#principal").val();
    var dept = $("#dept").val();
    var customer = $("#customer").val();
    var serverId = $("#serverId").val();
    var incomeDate = $("#incomeDate").val();
    var leafType = $("#leafType").val();
    var leafNum = $("#leafNum").val();

    var data={"incomeNum":incomeNum , "principal":principal , "dept":dept , "customer":customer , "serverId":serverId , "incomeDate":incomeDate ,
    "leafType":leafType , "leafNum":leafNum};

    var errorInfo = "";
    if(incomeNum == ""){
        errorInfo = errorInfo+"收入单号  ";
    }
    if(principal == ""){
        errorInfo = errorInfo+"负责人  ";
    }
    if(dept == ""){
        errorInfo = errorInfo+"负责部门  ";
    }
    if(customer == ""){
        errorInfo = errorInfo+"客户  ";
    }
    if(serverId == ""){
        errorInfo = errorInfo+"服务请求单号  ";
    }
    if(incomeDate == ""){
        errorInfo = errorInfo+"收入日期  ";
    }
    if(leafType == ""){
        errorInfo = errorInfo+"源单类型  ";
    }
    if(leafNum == ""){
        errorInfo = errorInfo+"源单编号  ";
    }
    if(errorInfo != ""){
        alert(errorInfo + "未填写");
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:"saveIncomeHead",
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
//用于收入页面
function judgeIdentityOnIncome() {
    $.ajax({
        type:"post",
        url:"sureIdentity",
        async:false,
        success:function (data) {
            if(data == 1){
                loadSimpleIncome();
            }else if(data == 2){
                // getAllApprove();
                loadSimpleApprove();
            }
        },
        error:function () {
            alert("程序出错");
        }
    })
}

function loadSimpleIncome() {
    var status = "W";
    $.ajax({
        type:"post",
        data:{"status":status},
        url:"getAllCanWrite",
        async:false,
        success:function (data) {
            $("#incomeData").html("");
            var incomes = data.incomes;
            for(var i = 0 ; i < incomes.length ; i++){
                $("#incomeData").append("<tr>"+"<td>"+incomes[i].incomeNum+"</td>"+"<td>"+incomes[i].employee+"</td>"+"<td>"+incomes[i].dept+"</td>"+"<td>"+incomes[i].customer+"</td>"+"<td>"+formatDate(incomes[i].incomeDate)+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal1\" onclick=\"getIncomeId("+incomes[i].id+")\">编辑</a>"+"</td>"+"</tr>");
            }
        }
    })
}

function loadSimpleApprove() {
    var status = "O";
    $.ajax({
        type:"post",
        data:{"status":status},
        url:"getAllCanWrite",
        async:false,
        success:function (data) {
            $("#incomeData").html("");
            var incomes = data.incomes;
            for(var i = 0 ; i < incomes.length ; i++){
                $("#incomeData").append("<tr>"+"<td>"+incomes[i].incomeNum+"</td>"+"<td>"+incomes[i].employee+"</td>"+"<td>"+incomes[i].dept+"</td>"+"<td>"+incomes[i].customer+"</td>"+"<td>"+formatDate(incomes[i].incomeDate)+"</td>"+"<td>"+"<a data-toggle=\"modal\" data-target=\"#myModal2\" onclick=\"findIncomeTotalInfo("+incomes[i].id+")\">审批</a>"+"</td>"+"</tr>");
            }
        }
    })
}

function getIncomeId(iId) {
    $("#incomeHid").val(iId);
}

function writeIncomeDetail() {
    var iId = $("#incomeHid").val();
    var incomeCode = $("#incomeType option:selected").val();
    var incomeName = $("#incomeType option:selected").text();
    var employee = $("#incomeEmployee").val();
    var dept = $("#incomeDept").val();
    var happenDate = $("#happenDate").val();
    var amount = $("#incomeAmount").val();
    var associate = $("#associateAmount").val();
    var note = $("#note").val();

    var data={"iId":iId , "incomeCode":incomeCode , "incomeName":incomeName , "employee":employee , "dept":dept , "happenDate":happenDate , "amount":amount , "associate":associate , "note":note};

    var errorInfo = "";
    if(incomeCode == "0"){
        errorInfo += "收入类型  ";
    }
    if(employee ==""){
        errorInfo += "员工  ";
    }
    if(dept == ""){
        errorInfo += "归属部门  ";
    }
    if(happenDate ==""){
        errorInfo += "发生日期  ";
    }
    if(amount == ""){
        errorInfo += "金额  ";
    }
    if(associate == ""){
        errorInfo += "关联金额  ";
    }
    if(errorInfo != ""){
        alert(errorInfo + "未填写或未选择");
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:"saveIncomeDetail",
            async:false,
            success:function (data) {
                alert(data);
                loadSimpleIncome();
            },
            error:function () {
                alert("程序出错");
            }
        })
    }

}

function findIncomeTotalInfo(hId) {
    $.ajax({
        type:"post",
        data:{"hId":hId},
        url:"findTotalIncome",
        async:false,
        success:function (data) {
            var head = data.head;
            var detail = data.detail;
            $("#headeId").val(head.id);
            $("#newIncomeNum").val(head.incomeNum);
            $("#newPrincipal").val(head.employee);
            $("#newAcceptDept").val(head.dept);
            $("#newEmployee").val(detail.employee);
            $("#hadDept").val(detail.dept);
            $("#newCustomer").val(head.customer);
            $("#requestNum").val(head.requestNum);
            $("#newLeafType").val(head.leafType);
            $("#newLeafNum").val(head.leafNum);
            $("#newIncomeType").val(detail.incomeName);
            $("#newAmount").val(detail.amount);
            $("#associate").val(detail.associateAmount);
            $("#newNote").val(detail.note);
            $("#newIncomeDate").val(formatDate(head.incomeDate));
            $("#newHappenDate").val(formatDate(detail.happenDate));

        }
    })
}

function passIncome() {
    var status = "P";
    var hId = $("#headeId").val();
    $.ajax({
        type:"post",
        data:{"hId":hId , "status":status},
        url:"approveIncome",
        async:false,
        success:function (data) {
            alert(data);
            loadSimpleApprove();
        },
        error:function () {
            alert("程序出错");
        }
    })
}

function refuseIncome() {
    var status = "R";
    var hId = $("#headeId").val();
    $.ajax({
        type:"post",
        data:{"hId":hId , "status":status},
        url:"approveIncome",
        async:false,
        success:function (data) {
            alert(data);
            loadSimpleApprove();
        },
        error:function () {
            alert("程序出错");
        }
    })
}
