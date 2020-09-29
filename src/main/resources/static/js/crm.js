function changeHeadPic() {
    $("#headPic").attr("src" , "./image/defaultUser.jpg")
}

function getHeadPic() {
    $.ajax({
        type:"post",
        url:"getHeadPic",
        async:false,
        success:function (data) {
            if(data != "NO"){
                var picName = data;
                getPic(picName);
            }else{
                changeHeadPic();
            }
        }
    })
}

function getPic(picName) {
    $.ajax({
        type:"post",
        data:{"picName":picName},
        url:"headPicExist",
        async:false,
        success:function (data) {
            if(data == "NO"){
                changeHeadPic();
            }else{
                var path = "image/headPic/"+picName;
                $("#headPic").attr("src" , path);
                $("#tabPic").attr("src" , path);
            }
        }
    })
}

function changePhone() {
    var phone = $("#newPhone").val();
    if(phone == ""){
        $.message({
            message:"手机号不能为空",
            type:'error'
        })
    }else{
        $.ajax({
            type:"post",
            data:{"phone":phone},
            url:"onlyChangePhone",
            async:false,
            success:function (data) {
                if(data == "OK"){
                    $.message({
                        message:"修改成功",
                        type:'success'
                    })
                    getPersonalInfo();
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }

            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })
    }
}

function uploadHeadPic() {
    $.ajaxFileUpload({
        type:"post",
        fileElementId:"demo",
        url:"uploadHeadPic",
        async:false,
        dataType:'text',
        success:function(data){
            if(data == "OK"){
                $.message({
                    message:"头像上传成功",
                    type:'success'
                })
                getHeadPic();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:"设备类型文件上传出错",
                type:'error'
            })
        }
    })
}

function uploadImages(){
    $.ajaxFileUpload({
        type:"post",
        fileElementId:"images",
        url:"updatePic",
        async:false,
        dataType:'text',
        success:function(data){
            alert(data);
            if(data == "文件上传成功"){
                $.message({
                    message:'文件上传成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function changePassword() {
    var password = $("#passone").val();
    var password2 = $("#passtwo").val();
    if(password == "" || password2 ==""){
        $.message({
            message:"请输入两次新密码",
            type:'error'
        })
    }else{
        if(password != password2){
            $.message({
                message:"输入的两次密码不一致",
                type:'error'
            })
        }else{
            $.ajax({
                type:"post",
                data:{"password":password},
                url:"onlyChangePassword",
                async:false,
                success:function (data) {
                    if(data == "OK"){
                        $.message({
                            message:"修改成功",
                            type:'success'
                        })
                        getPersonalInfo();
                    }else{
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }

                },
                error:function () {
                    $.message({
                        message:"程序出错",
                        type:'error'
                    })
                }
            })
        }
    }
}

//个人中心的两个功能：1、查询个人数据  2、保存修改个人数据
function getPersonalInfo(){
    $.ajax({
        url:"getPersonInfo",
        type:"post",
        async:true,
        success: function(data){
            var user = data.user;
            $("#userAccount").val(user.account);
            $("#userName").val(user.name);
            $("#userCompany").val(user.company);
            $("#userEmail").val(user.email);
            $("#userPhone").val(user.phone);
            $("#userDept").val(user.dept);
            $("#userJob").val(user.job);
            $("#qqNum").val(user.qqNum);
            $("#wechatNum").val(user.wechatNum);
            $("#password1").val(user.password);
            if(user.sex != null){
                if(user.sex.replace(/\s*/g,"") == "男"){
                    $("#isMale").attr("checked","checked");
                    $("#isFemale").removeAttr("checked");
                }else{
                    $("#isFemale").attr("checked","checked");
                    $("#isMale").removeAttr("checked");
                }
            };
            getHeadPic();
        },
        error: function(data){
            $.message({
                message:'数据获取失败',
                type:'error'
            })
        }
    });
}

function savePersonalInfo() {
    var account = $("#userAccount").val();
    var name = $("#userName").val();
    var company = $("#userCompany").val();
    var email = $("#userEmail").val();
    var dept = $("#userDept").val();
    var job = $("#userJob").val();
    var qqNum = $("#qqNum").val();
    var wechatNum = $("#wechatNum").val();
    var sex = $("input[name='sex']:checked").val();
    var url = "savePersonInfo";
    var data = {"account":account ,"sex":sex , "name":name , "company":company  , "email":email , "dept":dept , "job":job , "qqNum":qqNum , "wechatNum":wechatNum};
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function (data) {
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
                getPersonalInfo();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }

        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
            }
            pages = data.size;

            $("#pageDIV").append("<a href=\"#\" onclick=\"getFirstUserByPage()\">首 页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getPreviousUserByPage()\">上一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getNextUserByPage()\">下一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getLastUserByPage()\">尾 页</a>");
        }
    });
}

function getNextUserByPage() {
    if(start == pages){
        $.message({
            message:'此页为最后一页',
            type:'warning'
        })
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
                    $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getPreviousUserByPage() {
    if(start == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
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
                    $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
            }
            pages = data.size;
            $("#pageDIV").append("<a href=\"#\" onclick=\"getFirstUserByPageAndCondition()\">首 页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getPreviousUserByPageAndCondition()\">上一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getNextUserByPageAndCondition()\">下一页</a>&nbsp&nbsp<a href=\"#\" onclick=\"getLastUserByPageAndCondition()\">尾 页</a>");
        }
    });

}


function getNextUserByPageAndCondition() {
    if(start == pages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
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
                    $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
                }
            }
        });
    }
}

function getPreviousUserByPageAndCondition() {
    if(start == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
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
                    $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
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
                $("#allUser").append("<tr>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].account+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].name+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].company+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].phone+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].email+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+user[i].role+"</td>"+"<td style=\"font-size:14px;text-align: left;vertical-align: inherit;\">"+'<a data-toggle="modal" data-target="#myModal" onclick="showAllRoles(this)">修改角色</a>'+"&nbsp&nbsp" +'<a onclick="deleteUser(this)">删除账号</a>'+"</td>"+"</tr>");
            }
        }
    });
}

//在模态框中显示所有角色
function showAllRoles(obj) {
    var x = $(obj).parent().parent().find("td");
    var account = x.eq(0).text();
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
                $.message({
                    message:data,
                    type:'success'
                })
                getUserByPage();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
                $("#userRole").append('<input type="checkbox" name="userRoleCheck" value="'+roles[i].id+'">'+'<p style="margin-top: -35px;margin-left: 20px;">'+roles[i].roleName+"</p>"+"<br>");
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
            $.message({
                message:'邮箱输入的格式不正确',
                type:'error'
            })
        }else{
            $.ajax({
                type:"post",
                data:data,
                url:"addAccount",
                async:false,
                success:function(data){
                    if(data == "注册成功"){
                        $.message({
                            message:'注册成功',
                            type:'success'
                        })
                        getUserByPage();
                    }else{
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }

                },
                error:function () {
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
                }
            })
        }
    }else{
        $.message({
            message:errorInfo+"是必填项或必选项",
            type:'error'
        })
    }
}

function deleteUser(obj) {
    var x = $(obj).parent().parent().find("td");
    var account = x.eq(0).text();
    if(confirm('确定要删除账号:'+account+'吗')==true){

        $.ajax({
            type:"post",
            data:{"account":account},
            url:"deleteUser",
            async:false,
            success:function (data) {
                if(data == "账号删除成功"){
                    $.message({
                        message:data,
                        type:'success'
                    })
                    getUserByPage();
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }

            },
            error:function(){
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })

    }else{

        return false;

    }

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;vertical-align:middle; margin-top:0;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"零部件二级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="汽车后市场供应商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"汽车后市场供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="发动机">'+"发动机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="轮毂">'+"轮毂"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="曲轴">'+"曲轴"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="连杆">'+"连杆"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="涡轮">'+"涡轮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="缸体/缺盖">'+"缸体/缺盖"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="转向节">'+"转向节"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="齿轮">'+"齿轮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="门铰链">'+"门铰链"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;vertical-align:middle; margin-top:0;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"零部件二级供应商"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="蒙皮">'+"蒙皮"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="起落架">'+"起落架"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="叶轮">'+"叶轮"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="叶片">'+"叶片"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="发动机">'+"发动机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="骨科类工具">'+"骨科类工具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="植入件">'+"植入件"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="牙科类工具">'+"牙科类工具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="主机厂" style="width: 15px;vertical-align:middle; margin-top:0;">'+"主机厂"
        +'<input name="bussinessNature" type="radio" value="零部件一级供应商" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"零部件一级供应商"+"<br>"
        +'<input name="bussinessNature" type="radio" value="零部件二级供应商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"零部件二级供应商"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="推土机">'+"推土机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="挖掘机">'+"挖掘机"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="装载机">'+"装载机"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="履带">'+"履带"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="底盘">'+"底盘"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="注塑模具">'+"注塑模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="冲压模具">'+"冲压模具"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="锻造模具">'+"锻造模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="压铸模具">'+"压铸模具"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="挤压模具">'+"挤压模具"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="零部代加工厂家" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"零部代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
    $("#products").append("<td  style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司主要经营的产品"+"</td>"+"<td>"+'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="苹果">'+"苹果"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="华为">'+"华为"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="三星">'+"三星"+"<br>"
        +'<input name="product" type="checkbox" style="width: 15px;vertical-align:middle; margin-top:0;" value="小米">'+"小米"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="OPPO">'+"OPPO"
        +'<input name="product" type="checkbox" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;" value="其它">'+"其它"+"</td>");

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
    $("#bussiness").append("<td style=\"font-size: 14px;\">"+"<span style=\"color: red\" y>*</span>"+"您公司是"+"</td>"+"<td>"+'<input name="bussinessNature" type="radio" value="自有品牌商" style="width: 15px;vertical-align:middle; margin-top:0;">'+"自有品牌商"
        +'<input name="bussinessNature" type="radio" value="代加工厂家" style="width: 15px;margin-left: 5%;vertical-align:middle; margin-top:0;">'+"代加工厂家"+"<br>"
        +'<input name="bussinessNature" type="radio" value="其它" style="width: 15px;vertical-align:middle; margin-top:0;">'+"其它"+"</td>");
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
                    loadSecond();
                }else{
                    $.message({
                        message:'验证码不正确',
                        type:'error'
                    })
                }
            }else{
                $.message({
                    message:'请输入验证码',
                    type:'error'
                })
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
    var salePlanID = $("#customerID").val();

    var data = {"salePlanID":salePlanID,"companyName":companyName , "contact":contact , "contactWay":contactWay , "wechatNum":wechatNum};
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
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }
                },
                error:function () {
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
                }
            })
        }else{
            $.message({
                message:'联系人填写错误，不能是数字',
                type:'error'
            })
        }
    }else{
        $.message({
            message:'公司名称填写错误，不能是数字',
            type:'error'
        })
    }
}

//第二页将所有数据保存并跳转至下一页
function saveSecondPart() {
    var registerMoney = $("#registerMoney").val();
    var establishTime = $("#establishTime").val();
    var bussniessNature = $("#bussinessNature option:selected").text();
    var sonCompanyNum = $("#son option:selected").text();
    var employeeNum = $("#employee option:selected").text();
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"registerMoney":registerMoney , "establishTime":establishTime , "bussinessNature":bussniessNature , "sonCompanyNum":sonCompanyNum , "employeeNum":employeeNum};
    var url = "updateCompanyInfoFirst";
    if(registerMoney != ""){
        if(isNaN(registerMoney)){
            $.message({
                message:'注册资金填写错误，只能填写数字',
                type:'error'
            })
        }else{
            if(establishTime != ""){
                if(isNaN(establishTime)){
                    $.message({
                        message:'公司成立时间填写错误,只能填数字',
                        type:'error'
                    })
                }else{
                    $.ajax({
                        type:"post",
                        data:data,
                        url:url,
                        async:false,
                        success:function(data){
                            if(data == "OK"){
                                loadThird();
                            }else{
                                $.message({
                                    message:data,
                                    type:'error'
                                })
                            }
                        },
                        error:function(){
                            $.message({
                                message:'程序出错',
                                type:'error'
                            })
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
                            loadThird();
                        }else{
                            $.message({
                                message:data,
                                type:'error'
                            })
                        }
                    },
                    error:function(){
                        $.message({
                            message:'程序出错',
                            type:'error'
                        })
                    }
                })
            }
        }
    }else{
        if(establishTime != ""){
            if(isNaN(establishTime)){
                $.message({
                    message:'公司成立时间填写错误,只能填数字',
                    type:'error'
                })
            }else{
                $.ajax({
                    type:"post",
                    data:data,
                    url:url,
                    async:false,
                    success:function(data){
                        if(data == "OK"){
                            loadThird();
                        }else{
                            $.message({
                                message:data,
                                type:'error'
                            })
                        }
                    },
                    error:function(){
                        $.message({
                            message:'程序出错',
                            type:'error'
                        })
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
                        loadThird();
                    }else{
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }
                },
                error:function(){
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"industry":industry , "industryNature":industryNature , "product":product , "picture":picName};
    var url="updateCompanyInfoSecond";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                loadfourth();
            }else{
                $.message({
                    message:data,
                    type:error
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"means":means , "toolManage":toolManage , "facilitatorName":facilitatorName , "problem":problem , "consume":consume , "principal":principal ,
        "mobile":mobile , "email":email , "ask":ask};
    var url = "saveKnowMorePage";
    if(toolManage == "有"){
        if(isNaN(facilitatorName)){
            if(isNaN(problem)){
                if(isNaN(mobile)){
                    $.message({
                        message:'手机号填写错误，手机号只能是数字',
                        type:'error'
                    })
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
                                        loadfifth();
                                    }else{
                                        $.message({
                                            message:data,
                                            type:'error'
                                        })
                                    }
                                },
                                error:function () {
                                    $.message({
                                        message:'程序出错',
                                        type:'error'
                                    })
                                }
                            })
                        }else{
                            $.messgae({
                                message:'错误的邮件格式',
                                type:'error'
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
                                    loadfifth();
                                }else{
                                    $.message({
                                        message:data,
                                        type:'error'
                                    })
                                }
                            },
                            error:function () {
                                $.message({
                                    message:'程序出错',
                                    type:'error'
                                })
                            }
                        })
                    }
                }
            }else{
                $.message({
                    message:'您之前碰到的主要问题不能填数字',
                    type:'error'
                })
            }
        }else{
            $.message({
                message:'服务商名称不能填数字',
                type:'error'
            })
        }
    }else{
        if(isNaN(mobile)){
            $.message({
                message:'手机号填写错误，手机号只能是数字',
                type:'error'
            })
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
                                loadfifth();
                            }else{
                                $.message({
                                    message:data,
                                    type:'error'
                                })
                            }
                        },
                        error:function () {
                            $.message({
                                message:'程序出错',
                                type:'error'
                            })
                        }
                    })
                }else{
                    $.message({
                        message:'错误的邮件格式',
                        type:'error'
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
                            loadfifth();
                        }else{
                            $.message({
                                message:data,
                                type:'error'
                            })
                        }
                    },
                    error:function () {
                        $.message({
                            message:'程序出错',
                            type:'error'
                        })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"production":production , "market":market , "competitor":competitor , "project":project};
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
                        loadsixth();
                    }else{
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }
                },
                error:function () {
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
                }
            })
        }else{
            $.message({
                message:'您公司的主要竞争对手,不能填写数字',
                type:'error'
            })
        }
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function (data) {
                if(data == "OK"){
                    loadsixth();
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"assetType":assetType , "assetSource":assetSource , "brand":brand , "assetNum":assetNum , "life":life , "form":form };
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
                        loadseventh();
                    }else{
                        $.message({
                            message:data,
                            type:'error'
                        })
                    }
                },
                error:function () {
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
                }
            })
        }else{
            $.message({
                message:'设备品牌填写有误,不能填写数字',
                type:'error'
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
                    loadseventh();
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"buyMoney":buyMoney , "buySource":buySource , "buyBrand":buyBrand , "buyModel":buyModel , "manageModel":manageModel};
    var url = "saveManufactureThree";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function (data) {
            if(data == "OK"){
                loadeighth();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"ztzb":ztzb , "xdzb":xdzb , "szzb":szzb , "qtOne":qtOne , "cdpzb":cdpzb , "xdpzb":xdpzb , "tdpzb":tdpzb , "qtTwo":qtTwo , "cbn":cbn , "pcd":pcd , "hjzb":hjzb ,
        "hasGSG":hasGSG , "gsgBrand":gsgBrand };
    var url = "saveDataOne";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                loadnineth();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"stockMoney":stockMoney , "stockOne":stockOne , "stockTwo":stockTwo , "stockThree":stockThree , "stockFour":stockFour , "normTool":normTool , "unnormTool":unnormTool };
    var url = "saveDataTwo";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        success:function (data) {
            if(data == "OK"){
                loadtenth();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"hasERP":hasERP , "erpBrand":erpBrand , "hasMES":hasMES , "mesBrand":mesBrand , "shiftManage":shiftManage , "productRest":productRest , "productNum":productNum };
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
                                loadeleventh();
                            }else{
                                $.message({
                                    message:data,
                                    type:'error'
                                })
                            }
                        },
                        error:function () {
                            $.message({
                                message:'程序出错',
                                type:'error'
                            })
                        }
                    })
                }else{
                    $.message({
                        message:'MES品牌不能填数字',
                        type:'error'
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
                            loadeleventh();
                        }else{
                            $.message({
                                message:data,
                                type:'error'
                            })
                        }
                    },
                    error:function () {
                        $.message({
                            message:'程序出错',
                            type:'error'
                        })
                    }
                })
            }
        }else{
            $.message({
                message:'ERP品牌不能填写数字',
                type:'error'
            })
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
                            loadeleventh();
                        }else{
                            $.message({
                                message:data,
                                type:'error'
                            })
                        }
                    },
                    error:function () {
                        $.message({
                            message:'程序出错',
                            type:'error'
                        })
                    }
                })
            }else{
                $.message({
                    message:'MES品牌不能填数字',
                    type:'error'
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
                        loadeleventh();
                    }else{
                        $.message({
                            message:'data',
                            type:'error'
                        })
                    }
                },
                error:function () {
                    $.message({
                        message:'程序出错',
                        type:'error'
                    })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"stockPerson":stockPerson , "grantPerson":grantPerson ,"grantWay":grantWay , "returnWay":returnWay , "payCycle":payCycle , "payWay":payWay };
    var url = "saveDataFour";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function (data) {
            if(data == "OK"){
                loadtwelfth();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
    var salePlanID = $("#customerID").val();
    var data = {"salePlanID":salePlanID,"repair":repair , "repairSpend":repairSpend , "optimize":optimize , "appeal":appeal};
    var url="saveDataFive";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                });
                $("#myModal2").modal('hide');
                loadFirstPlan();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costEstimate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costType+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costCenter+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].amount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].appliedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].usedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>");
                }
            }
        }
    })
}

function findAllSalePlanNext() {
    salestart = salestart +1;
    if(salestart > salePages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
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
                }else{
                    var salePlans = data.salePlans;
                    for(var i = 0 ; i < salePlans.length ; i++){
                        $("#salePlanData").append("<tr>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costEstimate+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costType+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costCenter+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].amount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].appliedAmount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].usedAmount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>");                    }
                }
            }
        })
    }
}

function findAllSalePlanPrevious() {

    if(salestart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        salestart = salestart - 1;
        $.ajax({
            type:"post",
            data:{"start":salestart},
            url:"getAllSalePlan",
            async:false,
            success:function(data){
                $("#salePlanData").html("");
                var result = data.result;
                if(result == "暂无数据"){
                }else{
                    var salePlans = data.salePlans;
                    for(var i = 0 ; i < salePlans.length ; i++){
                        $("#salePlanData").append("<tr>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costEstimate+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costType+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costCenter+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].amount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].appliedAmount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].usedAmount+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                            +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>");
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
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costEstimate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costType+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costCenter+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].amount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].appliedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].usedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>");                }
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
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costEstimate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costType+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].costCenter+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].amount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].appliedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].usedAmount+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>");                }
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
            }else{
                var salePlans = data.salePlans;
                salePages = data.size;
                for(var i = 0 ; i < salePlans.length ; i++){
                    $("#salePlanWrite").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanNumber+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].salesPlanDesc+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].company+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].totalStatus+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+salePlans[i].creater+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(salePlans[i].createTime)+"</td>");
                }
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function clickGetting() {
    var contactWay = $("#contactWay").val();
    if(contactWay == ""){
        $.message({
            message:'输入的手机号不能为空',
            type:'error'
        })
        return false;
    }else {
        if (isNaN(contactWay)) {
            $.message({
                message:'手机号码填写错误(含有非法字符,手机号只能是数字)',
                type:'error'
            })
        } else {
            sendCode();
            var btn = $(this);
            var count = 60;
            var resend = setInterval(function(){
                count--;
                if (count > 0){
                    btn.val(count+"秒后可重新获取");
                    document.getElementById("getting").innerHTML=count+"秒";
                    $.cookie("captcha", count, {path: '/', expires: (1/86400)*count});
                    $('#getting').attr('disabled',"true");

                }else {
                    clearInterval(resend);
                    $("#getting").attr('disabled',false);
                    document.getElementById("getting").innerText="获取验证码";
                    $('#getting').removeAttr("disabled");
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
            if(data == "获取验证码成功"){
                $.message({
                    message:'获取验证码成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'获取验证码失败',
                type:'error'
            })
        }
    })
}

function saleWrite() {
    var number = $("#saleWriteNO").val();
    var name = $("#saleWriteName").val();
    var describe = $("#saleWriteDesc").val();
    var data = {"number":number , "name":name , "describe":describe};
    var url = "createSalePlanFirst";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                $.message({
                    message:'销售计划创建成功',
                    type:'success'
                })
                findAllSalePlan();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
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

function sureCompany() {
    var salePlanNum = $("#salePlanNumber").val();
    $.ajax({
        type:"post",
        data:{"salePlanNum":salePlanNum},
        url:"findCompanyBySalePlan",
        async:false,
        success:function (data) {
            if(data == "F"){
                $.message({
                    message:'销售计划不存在',
                    type:'error'
                })
                $("#company").val("");
            }else{
                $("#company").val(data);
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function getAllTraveler() {
    var salePlanNum = $("#salePlanNumber").val();
    var company = $("#company").val();
    var target = $("#target").val();
    var province = $("#province option:selected").val();
    var city = $("#city option:selected").val();
    var address = $("#address").val();
    var bdate = $("#bdate").val();
    var creater = $("#creater").val();
    var data = {"salePlanNum":salePlanNum , "company":company , "target":target , "province":province , "city":city ,
    "address":address , "bdate":bdate , "creater":creater};
    var url = "saveTravelPlan";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
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
        $.message({
            message:errorInfo+"未填写",
            type:'error'
        })
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
                    $.message({
                        message:'数据保存成功',
                        type:'success'
                    })
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
        $.message({
            message:errorInfo +"未填写",
            type:'error'
        })
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:url,
            async:false,
            success:function (data) {
                if(data == "OK"){
                    $.message({
                        message:'数据保存成功',
                        type:'success'
                    })
                    judgeIdentity();
                }else{
                    $.message({
                        message:data,
                        type:'error'
                    })
                }
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
            $.message({
                message:'程序出错',
                type:'error'
            })
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
            }else{
                $("#expenseData").html("");
                for(var i = 0 ; i < data.length ; i++){
                    var num = i+1;
                    $("#expenseData").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+num+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].expenseNum+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].leafType+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].dept+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(data[i].expenseDate)+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal3\" onclick=\"getTotalInfo("+data[i].id+")\">审批</a>"+"</td>"+"</tr>")
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
            }else{
                $("#expenseData").html("");
                for(var i = 0 ; i < data.length ; i++){
                    var num = i+1;
                    $("#expenseData").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+num+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].expenseNum+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].leafType+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].dept+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDateTime(data[i].expenseDate)+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal2\" onclick=\"passValue("+data[i].id+")\">编辑</a>"+"</td>"+"</tr>")
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
            $.message({
                message:data,
                type:'success'
            })
            getAllApprove();
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
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
            $.message({
                message:data,
                type:'success'
            })
            getAllApprove();
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
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
        $.message({
            message:errorInfo + "未填写",
            type:'error'
        })
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:"saveIncomeHead",
            async:false,
            success:function (data) {
                $.message({
                    message:data,
                    type:'success'
                })
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
                loadSimpleApprove();
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
                $("#incomeData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].incomeNum+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].employee+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].dept+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].customer+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDate(incomes[i].incomeDate)+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal1\" onclick=\"getIncomeId("+incomes[i].id+")\">编辑</a>"+"</td>"+"</tr>");
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
                $("#incomeData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].incomeNum+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].employee+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].dept+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+incomes[i].customer+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDate(incomes[i].incomeDate)+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal2\" onclick=\"findIncomeTotalInfo("+incomes[i].id+")\">审批</a>"+"</td>"+"</tr>");
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
        $.message({
            message:errorInfo + "未填写或未选择",
            type:'error'
        })
    }else{
        $.ajax({
            type:"post",
            data:data,
            url:"saveIncomeDetail",
            async:false,
            success:function (data) {
                $.message({
                    message:data,
                    type:'success'
                })
                loadSimpleIncome();
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
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
            $.message({
                message:data,
                type:'success'
            })
            loadSimpleApprove();
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
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
            $.message({
                message:data,
                type:'success'
            })
            loadSimpleApprove();
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function saveCustomer() {
    var customerName = $("#customerName").val();
    var customerType = $("#customerType").val();
    var area = $("#area option:selected").val();
    var decisionMaker = $("#decisionMaker").val();
    var decisionPhone = $("#decisionPhone").val();
    var principal = $("#principal").val();
    var phone = $("#phone").val();

    var superior = $("#superior").val();
    var email = $("#email").val();
    var wechat = $("#wechat").val();
    var industry = $("#industry").val();
    var companyAddress = $("#companyAddress").val();
    var saleAmount = $("#saleAmount").val();
    var useAmount = $("#useAmount").val();

    var data = {"customerName":customerName , "customerType":customerType , "area":area , "decisionMaker":decisionMaker , "decisionPhone":decisionPhone , "principal":principal , "phone":phone ,
        "superior":superior , "email":email , "wechat":wechat ,"industry":industry , "companyAddress":companyAddress , "saleAmount":saleAmount ,  "useAmount":useAmount };
    $.ajax({
        type:"post",
        data:data,
        url:"saveCustomer",
        async:false,
        success:function(data){
            if(data == "客户创建成功"){
                $.message({
                    message:'客户创建成功',
                    type:'success'
                })
                $("#customerName").val("");
                $("#industry").val("");
                $("#companyName").val("");
                $("#companyAddress").val("");
                $("#customerType").val("");
                $("#saleAmount").val("");
                $("#contact").val("");
                $("#useAmount").val("");
                $("#email").val("");
                $("#superior").val("");
                $("#phone").val("");
                $("#principal").val("");
                $("#wechat").val("");
                $("#saleMan").val("");
                findAllCustomer();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

var customerStart = 1;
var customerPages;

function findAllCustomer() {
    customerStart = 1;
    $.ajax({
        type:"post",
        data:{"start":customerStart},
        url:"selectCustomerByPage",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            customerPages = data.pages;
            var customers = data.customers;
            for(var i = 0 ; i < customers.length ; i++){
                $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

            }
            $("#customerPage").show();
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function findFirstCustomer() {

    if(customerStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })

    }else{
        customerStart = customerStart+1;
        $.ajax({
            type:"post",
            data:{"start":customerStart},
            url:"selectCustomerByPage",
            async:false,
            success:function (data) {
                $("#dataforCustomer").html("");
                // customerPages = data.pages;
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
                $("#customerPage").show();
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })
    }
}

function findNextCustomer() {

    if(customerStart == customerPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })

    }else{
        customerStart = customerStart+1;
        $.ajax({
            type:"post",
            data:{"start":customerStart},
            url:"selectCustomerByPage",
            async:false,
            success:function (data) {
                $("#dataforCustomer").html("");
                // customerPages = data.pages;
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
                $("#customerPage").show();
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })
    }
}

function findPreviousCustomer() {

    if(customerStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        customerStart = customerStart-1;
        $.ajax({
            type:"post",
            data:{"start":customerStart},
            url:"selectCustomerByPage",
            async:false,
            success:function (data) {
                $("#dataforCustomer").html("");
                // customerPages = data.pages;
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
                $("#customerPage").show();
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })
    }
}

function findLastCustomer() {
    if(customerStart == customerPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        customerStart = customerPages;
        $.ajax({
            type:"post",
            data:{"start":customerStart},
            url:"selectCustomerByPage",
            async:false,
            success:function (data) {
                $("#dataforCustomer").html("");
                // customerPages = data.pages;
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
                $("#customerPage").show();
            },
            error:function () {
                $.message({
                    message:'程序出错',
                    type:'error'
                })
            }
        })
    }
}


function getTodayCustomer() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    if(month < 10){
        month = "0"+month;
    }
    var day = now.getDate();

    var next = day+1;
    if(day < 10){
        day = "0"+day;
    }
    if(next < 10){
        next = "0" + next;
    }
    var start = year+"-"+month+"-"+day;
    var end =  year+"-"+month+"-"+next;
    var data = {"start":start , "end":start};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}


function getYesterdayCustomer() {
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    if(month < 10){
        month = "0"+month;
    }
    var day = now.getDate();
    if(day < 10){
        day = "0" + day;
    }
    var last = day-1;
    if(last < 10){
        last = "0" + last;
    }
    var start = year+"-"+month+"-"+last;
    var end =  year+"-"+month+"-"+day;
    var data = {"start":start , "end":start};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}

function getNextDate(day) {
    var dd = new Date();
    dd.setDate(dd.getDate() + day);
    var y = dd.getFullYear();
    var m = dd.getMonth() + 1 < 10 ? "0" + (dd.getMonth() + 1) : dd.getMonth() + 1;
    var d = dd.getDate() < 10 ? "0" + dd.getDate() : dd.getDate();
    return y + "-" + m + "-" + d;
};

function getThisWeekCustomer() {
    var now = new Date();
    var week = now.getDay();
    var first = getNextDate(-week+1)
    var last = getNextDate(7-week+1);
    var start = first;
    var end =  last;
    var data = {"start":start , "end":end};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}

function getLastWeekCustomer() {
    var now = new Date();
    var week = now.getDay();
    var first = getNextDate(-week-6)
    var last = getNextDate(7-week-6);
    var start = first
    var end =  last
    var data = {"start":start , "end":end};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}

function getThisMonthCustomer() {
    var beginMonth;
    var endMonth;
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    if(month == 12){
        if(month < 10){
            month = "0" + month;
        }
        beginMonth = year+"-"+month;
        var nextYear = year+1;
        endMonth = nextYear+"-"+"01";
    }else{
        var newMonth = month+1;
        if(month < 10){
            month = "0" + month;
        }
        beginMonth = year+"-"+month;
        if(newMonth < 10){
            newMonth = "0"+newMonth;
        }
        endMonth = year+"-"+newMonth;
    }
    var start = beginMonth+"-"+"01";
    var end =  endMonth+"-"+"01";
    var data = {"start":start , "end":end};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}

function getLastMonthCustomer() {
    var beginMonth;
    var endMonth;
    var now = new Date();
    var year = now.getFullYear();
    var month = now.getMonth()+1;
    if(month == 1){

        var lastYear = year-1;
        beginMonth = lastYear+"-"+"12";
        if(month < 10){
            month = "0"+month;
        }
        endMonth = year+"-"+month;

    }else{
        var newMonth = month-1;
        if(newMonth < 10){
            newMonth = "0"+newMonth;
        }
        if(month < 10){
            month = "0"+month;
        }
        beginMonth = year+"-"+newMonth;
        endMonth = year+"-"+month;
    }
    var start = beginMonth+"-"+"01";
    var end =  endMonth+"-"+"01";
    var data = {"start":start , "end":end};
    $.ajax({
        type:"post",
        data:data,
        url:"getTodayCustomer",
        async:false,
        success:function (data) {
            $("#dataforCustomer").html("");
            $("#customerPage").hide();
            var result = data.result;
            if(result == "OK"){
                var customers = data.customers;
                for(var i = 0 ; i < customers.length ; i++){
                    $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    // $("#dataforCustomer").append("<tr>"+"<td>"+customers[i].name+"</td>"+"<td>"+customers[i].contact+"</td>"+"<td>"+customers[i].phone+"</td>"+"<td>"+customers[i].customerType+"</td>"+"<td>"+customers[i].industry+"</td>"+"<td>"+customers[i].principal+"</td>"+"<td>"+formatDate(customers[i].createDate)+"</td>"+"</tr>");

                }
            }else{
                $.message({
                    message:result,
                    type:'error'
                })
            }
        }
    })

}

function findCutomerByNameOrPhone() {
    var  name = $("#customerSearch").val();
    if(name == ""){
        $.message({
            message:'请填写搜索客户的名称或联系方式',
            type:'error'
        })
    }else{
        if(isNaN(name)){//判断输入的内容是客户名称还是联系方式
            //客户名称
            $.ajax({
                type:"post",
                data:{"name":name},
                url:"findCustomerByName",
                async:false,
                success:function (data) {
                    $("#dataforCustomer").html("");
                    $("#customerPage").hide();
                    var customers = data.customers;
                    for(var i = 0 ; i < customers.length ; i++){
                        $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    }
                }
            })
        }else{
            //联系方式
            $.ajax({
                type:"post",
                data:{"phone":name},
                url:"findCustomerByPhone",
                async:false,
                success:function (data) {
                    $("#dataforCustomer").html("");
                    $("#customerPage").hide();
                    var customers = data.customers;
                    for(var i = 0 ; i < customers.length ; i++){
                        $("#dataforCustomer").append("<tr>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].id+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].name+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].customerType+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].decisionMaker+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].principal+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].industry+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].followName+"</td>"+"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+customers[i].createDate+"</td>"+"</tr>");
                    }
                }
            })
        }
    }
}

function createNewSaleCost() {
    var costType = $("#costType").val();
    var happenDate = $("#happenDate").val();
    var describe = $("#describe").val();
    var contract = $("#contract").val();
    var costAmount = $("#costAmount").val();
    var principal = $("#costPrincipal").val();
    var data = {"costType":costType , "happenDate":happenDate , "describe":describe , "contract":contract , "costAmount":costAmount , "principal":principal};
    $.ajax({
        type:"post",
        data:data,
        url:"saveSaleCost",
        async:false,
        success:function (data) {
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

function findByParams() {
    var type = $("#costType").val();
    var principal = $("#costPrincipal").val();
    var data={"type":type , "principal":principal};
    $.ajax({
        type:"post",
        data:data,
        url:"findByTypeAndPrincipal",
        async:false,
        success:function (data) {
            $("#allSaleCost").html("");
            var costs = data.costs;
            for(var i = 0 ; i < costs.length ; i++){
                $("#allSaleCost").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].costType+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].costDescribe+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].costAmount+"元"+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].happenDate+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].contract+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+costs[i].principal+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+formatDate(costs[i].createDate)+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a>操作</a>"+"</td>"
                    +"</tr>")
            }
        }
    })
}

function saveOpportunity() {
    var name = $("#name").val();
    var fax = $("#fax").val();
    var companyName = $("#companyName").val();
    var email = $("#email").val();
    var source = $("#source").val();
    var principal = $("#principal").val();
    var body = $("#body").val();
    var dept = $("#dept").val();
    var contract = $("#contract").val();
    var maker = $("#maker").val();
    var phone = $("#phone").val();
    var makeDate = $("#makeDate").val();
    var mobile = $("#mobile").val();
    var leafType = $("#leafType").val();
    var province = $("#province option:selected").text();
    var city = $("#city option:selected").text();
    var area = $("#area option:selected").text();
    var address = $("#address").val();
    var companyAddress = province + city + area + address;
    var leafNum = $("#leafNum").val();
    var data = {"name":name , "fax":fax , "companyName":companyName , "email":email , "source":source , "principal":principal , "body":body , "dept":dept ,
    "contract":contract , "maker":maker , "phone":phone , "makeDate":makeDate , "mobile":mobile , "leafType":leafType , "address":companyAddress , "leafNum":leafNum};
    $.ajax({
        type:"post",
        data:data,
        url:"createOpportunity",
        async:false,
        success:function (data) {
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function () {
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

var oppoSatrt = 1;
var oppoPages;

function getOppoByPageLoad() {
    oppoSatrt = 1;
    $.ajax({
        type:"post",
        data:{"start":oppoSatrt},
        url:"getOpportunityByPage",
        async:false,
        success:function(data){
            $("#opportunityData").html("");
            oppoPages = data.pages;
            var oppos = data.oppos;
            for(var i = 0 ; i < oppos.length ; i++){
                $("#opportunityData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                    +"</tr>");
            }
            $("#pageDIV").show();
        }
    })
}

function getOppoByPageFirst() {
    if(oppoSatrt == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        oppoSatrt = 1;
        $.ajax({
            type:"post",
            data:{"start":oppoSatrt},
            url:"getOpportunityByPage",
            async:false,
            success:function(data){
                $("#opportunityData").html("");
                oppoPages = data.pages;
                var oppos = data.oppos;
                for(var i = 0 ; i < oppos.length ; i++){
                    $("#opportunityData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                        +"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function getOppoByPagePrevious() {
    if(oppoSatrt == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        oppoSatrt = oppoSatrt - 1;
        $.ajax({
            type:"post",
            data:{"start":oppoSatrt},
            url:"getOpportunityByPage",
            async:false,
            success:function(data){
                $("#opportunityData").html("");
                oppoPages = data.pages;
                var oppos = data.oppos;
                for(var i = 0 ; i < oppos.length ; i++){
                    $("#opportunityData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                        +"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function getOppoByPageNext() {
    if(oppoSatrt == oppoPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        oppoSatrt = oppoSatrt + 1;
        $.ajax({
            type:"post",
            data:{"start":oppoSatrt},
            url:"getOpportunityByPage",
            async:false,
            success:function(data){
                $("#opportunityData").html("");
                oppoPages = data.pages;
                var oppos = data.oppos;
                for(var i = 0 ; i < oppos.length ; i++){
                    $("#opportunityData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                        +"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function getOppoByPageLast() {
    if(oppoSatrt == oppoPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        oppoSatrt = oppoPages;
        $.ajax({
            type:"post",
            data:{"start":oppoSatrt},
            url:"getOpportunityByPage",
            async:false,
            success:function(data){
                $("#opportunityData").html("");
                oppoPages = data.pages;
                var oppos = data.oppos;
                for(var i = 0 ; i < oppos.length ; i++){
                    $("#opportunityData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                        +"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}


function getOppoByAddress() {
    var province = $("#province option:selected").text();
    var city = $("#city option:selected").text();
    var area = $("#area option:selected").text();
    var address = province+city+area;
    $.ajax({
        type:"post",
        data:{"address":address},
        url:"findOppoByAddress",
        async:false,
        success:function(data){
            $("#opportunityData").html("");
            var oppos = data.oppos;
            for(var i = 0 ; i < oppos.length ; i++){
                $("#opportunityData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                    +"</tr>");
            }
            $("#pageDIV").hide();
        }
    })
}

function getOppoByResource(obj) {
    var resource = $(obj).text();
    $.ajax({
        type:"post",
        data:{"resource":resource},
        url:"findOppoByResource",
        async:false,
        success:function(data){
            $("#opportunityData").html("");
            var oppos = data.oppos;
            for(var i = 0 ; i < oppos.length ; i++){
                $("#opportunityData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].name+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].companyName+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].resource+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].contract+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].phone+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].mobile+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].principal+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+oppos[i].makeDate+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+"<a data-toggle=\"modal\" data-target=\"#myModal\">编辑</a>"+"</td>"
                    +"</tr>");
            }
            $("#pageDIV").hide();
        }
    })
}

//竞争对手新增
function createCompetitor() {
    var competitor = $("#competitorName").val();
    var owner = $("#owner").val();
    var province = $("#province option:selected").text();
    var city = $("#city option:selected").text();
    var address = $("#address").val();
    var website = $("#website").val();
    var manace = $("#manace").val();

    var product = $("#mainProduct").val();
    var customer = $("#mainCustomer").val();
    var advantage = $("#advantage").val();
    var data={"competitorName":competitor , "owner":owner , "province":province , "city":city  , "address":address , "advantage":advantage , "manace":manace , "website":website , "product":product , "customer":customer};
    $.ajax({
        type:"post",
        data:data,
        url:"saveCompetitor",
        async:false,
        success:function (data) {
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
                loadCompetitor();
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        }
    })
}

function getCompetitorByName() {
    var name = $("#competitorSearch").val();
    $.ajax({
        type:"post",
        data:{"name" : name},
        url:"getCompetitorByName",
        async:false,
        success:function (data) {
            $("#competitorData").html("");
            for(var i = 0 ; i < data.length ; i++){
                $("#competitorData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].competitorName+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].owner+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].province+data[i].city+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].address+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].website+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].manace+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+data[i].updatePerson+"</td>"+"</tr>");
            }
            if(name == ""){
                $("#pageDIV").show();
            }else{
                $("#pageDIV").hide();
            }
        }
    })
}

var competitorStart = 1;
var competitorPages;

function loadCompetitor() {
    competitorStart = 1;
    $.ajax({
        type:"post",
        data:{"start" : competitorStart},
        url:"findAllCompetitor",
        async:false,
        success:function (data) {
            $("#competitorData").html("");
            competitorPages = data.pages;
            var competitors = data.competitors;
            for(var i = 0 ; i < competitors.length ; i++){
                $("#competitorData").append("<tr>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].competitorName+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].owner+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].province+competitors[i].city+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].address+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].website+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].manace+"</td>"
                    +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].updatePerson+"</td>"+"</tr>");
            }
            $("#pageDIV").show();
        }
    })
}

function findCompetitorByPageFirst() {
    if(competitorStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        competitorStart = 1;
        $.ajax({
            type:"post",
            data:{"start":competitorStart},
            url:"findAllCompetitor",
            async:false,
            success:function (data) {
                $("#competitorData").html("");
                var competitors = data.competitors;
                for(var i = 0 ; i < competitors.length ; i++){
                    $("#competitorData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].competitorName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].owner+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].province+competitors[i].city+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].address+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].website+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].manace+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].updatePerson+"</td>"+"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function findCompetitorByPagePrevious() {
    if(competitorStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        competitorStart = competitorStart - 1;
        $.ajax({
            type:"post",
            data:{"start":competitorStart},
            url:"findAllCompetitor",
            async:false,
            success:function (data) {
                $("#competitorData").html("");
                var competitors = data.competitors;
                for(var i = 0 ; i < competitors.length ; i++){
                    $("#competitorData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].competitorName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].owner+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].province+competitors[i].city+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].address+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].website+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].manace+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].updatePerson+"</td>"+"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function findCompetitorByPageNext() {
    if(competitorStart == competitorPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        competitorStart = competitorStart + 1;
        $.ajax({
            type:"post",
            data:{"start":competitorStart},
            url:"findAllCompetitor",
            async:false,
            success:function (data) {
                $("#competitorData").html("");
                var competitors = data.competitors;
                for(var i = 0 ; i < competitors.length ; i++){
                    $("#competitorData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].competitorName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].owner+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].province+competitors[i].city+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].address+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].website+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].manace+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].updatePerson+"</td>"+"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function findCompetitorByPageLast() {
    if(competitorStart == competitorPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        competitorStart = competitorPages;
        $.ajax({
            type:"post",
            data:{"start":competitorStart},
            url:"findAllCompetitor",
            async:false,
            success:function (data) {
                $("#competitorData").html("");
                var competitors = data.competitors;
                for(var i = 0 ; i < competitors.length ; i++){
                    $("#competitorData").append("<tr>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].competitorName+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].owner+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].province+competitors[i].city+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].address+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].website+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].manace+"</td>"
                        +"<td style=\"font-size: 14px;text-align: left;vertical-align: inherit;\">"+competitors[i].updatePerson+"</td>"+"</tr>");
                }
                $("#pageDIV").show();
            }
        })
    }
}

function saveVisit() {
    var salePlanNo = $("#salePlanNo").val();
    var company = $("#company").val();
    var record = $("#record").val();
    var bdate = $("#bdate").val();
    var creater = $("#creater").val();
    var data = {"salePlanNo":salePlanNo , "company":company , "record":record , "bdate":bdate , "creater":creater};
    var url = "createVisitSchedule";
    $.ajax({
        type:"post",
        data:data,
        url:url,
        async:false,
        success:function(data){
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        },
        error:function(){
            $.message({
                message:'程序出错',
                type:'error'
            })
        }
    })
}

var visitScheduleStart = 1;
var visitSchedulePages;
function loadVisitSchedule() {
    visitScheduleStart = 1;
    $.ajax({
        type:"post",
        data:{"start":visitScheduleStart},
        url:"findVisitScheduleByPage",
        async:true,
        success:function (data) {
            var pages = data.pages;
            visitSchedulePages = pages;
            $("#visitRecord").html("");
            $("#visitPageDIV").show();
            var visitSchedules = data.visitSchedules;
            for(var i = 0 ; i <visitSchedules.length ; i++){
                $("#visitRecord").append("<tr>"
                    +"<td>"+visitSchedules[i].number+"</td>"
                    +"<td>"+visitSchedules[i].vname+"</td>"
                    +"<td>"+visitSchedules[i].bdate+"</td>"
                    +"<td>"+visitSchedules[i].destination+"</td>"
                    +"<td>"+visitSchedules[i].creater+"</td>"
                    +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                    +"</tr>");
            }
        }
    })
}

function getVisitScheduleFirst() {
    if(visitScheduleStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        visitScheduleStart = 1;
        $.ajax({
            type:"post",
            data:{"start":visitScheduleStart},
            url:"findVisitScheduleByPage",
            async:true,
            success:function (data) {
                var pages = data.pages;
                visitSchedulePages = pages;
                $("#visitRecord").html("");
                $("#visitPageDIV").show();
                var visitSchedules = data.visitSchedules;
                for(var i = 0 ; i <visitSchedules.length ; i++){
                    $("#visitRecord").append("<tr>"
                        +"<td>"+visitSchedules[i].number+"</td>"
                        +"<td>"+visitSchedules[i].vname+"</td>"
                        +"<td>"+visitSchedules[i].bdate+"</td>"
                        +"<td>"+visitSchedules[i].destination+"</td>"
                        +"<td>"+visitSchedules[i].creater+"</td>"
                        +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getVisitSchedulePrevious() {
    if(visitScheduleStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        visitScheduleStart = visitScheduleStart -1;
        $.ajax({
            type:"post",
            data:{"start":visitScheduleStart},
            url:"findVisitScheduleByPage",
            async:true,
            success:function (data) {
                var pages = data.pages;
                visitSchedulePages = pages;
                $("#visitRecord").html("");
                $("#visitPageDIV").show();
                var visitSchedules = data.visitSchedules;
                for(var i = 0 ; i <visitSchedules.length ; i++){
                    $("#visitRecord").append("<tr>"
                        +"<td>"+visitSchedules[i].number+"</td>"
                        +"<td>"+visitSchedules[i].vname+"</td>"
                        +"<td>"+visitSchedules[i].bdate+"</td>"
                        +"<td>"+visitSchedules[i].destination+"</td>"
                        +"<td>"+visitSchedules[i].creater+"</td>"
                        +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getVisitScheduleNext() {
    if(visitScheduleStart == visitSchedulePages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        visitScheduleStart = visitScheduleStart + 1;
        $.ajax({
            type:"post",
            data:{"start":visitScheduleStart},
            url:"findVisitScheduleByPage",
            async:true,
            success:function (data) {
                var pages = data.pages;
                visitSchedulePages = pages;
                $("#visitRecord").html("");
                $("#visitPageDIV").show();
                var visitSchedules = data.visitSchedules;
                for(var i = 0 ; i <visitSchedules.length ; i++){
                    $("#visitRecord").append("<tr>"
                        +"<td>"+visitSchedules[i].number+"</td>"
                        +"<td>"+visitSchedules[i].vname+"</td>"
                        +"<td>"+visitSchedules[i].bdate+"</td>"
                        +"<td>"+visitSchedules[i].destination+"</td>"
                        +"<td>"+visitSchedules[i].creater+"</td>"
                        +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getVisitScheduleLast() {
    if(visitScheduleStart == visitSchedulePages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        visitScheduleStart = visitSchedulePages;
        $.ajax({
            type:"post",
            data:{"start":visitScheduleStart},
            url:"findVisitScheduleByPage",
            async:true,
            success:function (data) {
                var pages = data.pages;
                visitSchedulePages = pages;
                $("#visitRecord").html("");
                $("#visitPageDIV").show();
                var visitSchedules = data.visitSchedules;
                for(var i = 0 ; i <visitSchedules.length ; i++){
                    $("#visitRecord").append("<tr>"
                        +"<td>"+visitSchedules[i].number+"</td>"
                        +"<td>"+visitSchedules[i].vname+"</td>"
                        +"<td>"+visitSchedules[i].bdate+"</td>"
                        +"<td>"+visitSchedules[i].destination+"</td>"
                        +"<td>"+visitSchedules[i].creater+"</td>"
                        +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function searchVisit() {
    var searchId = $("#searchId").val();
    $.ajax({
        type:"post",
        data:{"search" : searchId},
        url:"findVisitByParam",
        async:false,
        success:function(data){
            $("#visitRecord").html("");
            $("#visitPageDIV").hide();
            var result = data.result;
            var visitSchedules = data.visitSchedules;
            if(result == "OK"){

                for(var i = 0 ; i <visitSchedules.length ; i++){
                    $("#visitRecord").append("<tr>"
                        +"<td>"+visitSchedules[i].number+"</td>"
                        +"<td>"+visitSchedules[i].vname+"</td>"
                        +"<td>"+visitSchedules[i].bdate+"</td>"
                        +"<td>"+visitSchedules[i].destination+"</td>"
                        +"<td>"+visitSchedules[i].creater+"</td>"
                        +"<td>"+formatDateTime(visitSchedules[i].createDate)+"</td>"
                        +"</tr>");
                }
            }
        }
    })

}

var travelPlanStart = 1;
var travelPlanPages;

function loadTravelPlan() {
    travelPlanStart = 1;
    $.ajax({
        type:"post",
        data:{"start":travelPlanStart},
        url:"findTravelByPage",
        async:true,
        success:function (data) {
            travelPlanPages = data.pages;
            $("#travelList").html("");
            var travels = data.travels;
            for(var i = 0 ; i < travels.length ; i++){
                $("#travelList").append("<tr>"
                    +"<td>"+travels[i].salePlanNumber+"</td>"
                    +"<td>"+travels[i].customer+"</td>"
                    +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                    +"<td>"+travels[i].target+"</td>"
                    +"<td>"+travels[i].bdate+"</td>"
                    +"<td>"+travels[i].creater+"</td>"
                    +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                    +"</tr>");
            }
            $("#travelPageDIV").show();
        }
    })
}

function getTravelFirst() {
    if(travelPlanStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        travelPlanStart = 1;
        $.ajax({
            type:"post",
            data:{"start":travelPlanStart},
            url:"findTravelByPage",
            async:true,
            success:function (data) {
                travelPlanPages = data.pages;
                $("#travelList").html("");
                var travels = data.travels;
                for(var i = 0 ; i < travels.length ; i++){
                    $("#travelList").append("<tr>"
                        +"<td>"+travels[i].salePlanNumber+"</td>"
                        +"<td>"+travels[i].customer+"</td>"
                        +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                        +"<td>"+travels[i].target+"</td>"
                        +"<td>"+travels[i].bdate+"</td>"
                        +"<td>"+travels[i].creater+"</td>"
                        +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                        +"</tr>");
                }
                $("#travelPageDIV").show();
            }
        })
    }
}

function getTravelPrevious() {
    if(travelPlanStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        travelPlanStart = travelPlanStart - 1;
        $.ajax({
            type:"post",
            data:{"start":travelPlanStart},
            url:"findTravelByPage",
            async:true,
            success:function (data) {
                travelPlanPages = data.pages;
                $("#travelList").html("");
                var travels = data.travels;
                for(var i = 0 ; i < travels.length ; i++){
                    $("#travelList").append("<tr>"
                        +"<td>"+travels[i].salePlanNumber+"</td>"
                        +"<td>"+travels[i].customer+"</td>"
                        +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                        +"<td>"+travels[i].target+"</td>"
                        +"<td>"+travels[i].bdate+"</td>"
                        +"<td>"+travels[i].creater+"</td>"
                        +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                        +"</tr>");
                }
                $("#travelPageDIV").show();
            }
        })
    }
}

function getTravelNext() {
    if(travelPlanStart == travelPlanPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        travelPlanStart = travelPlanStart + 1;
        $.ajax({
            type:"post",
            data:{"start":travelPlanStart},
            url:"findTravelByPage",
            async:true,
            success:function (data) {
                travelPlanPages = data.pages;
                $("#travelList").html("");
                var travels = data.travels;
                for(var i = 0 ; i < travels.length ; i++){
                    $("#travelList").append("<tr>"
                        +"<td>"+travels[i].salePlanNumber+"</td>"
                        +"<td>"+travels[i].customer+"</td>"
                        +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                        +"<td>"+travels[i].target+"</td>"
                        +"<td>"+travels[i].bdate+"</td>"
                        +"<td>"+travels[i].creater+"</td>"
                        +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                        +"</tr>");
                }
                $("#travelPageDIV").show();
            }
        })
    }
}

function getTravelLast() {
    if(travelPlanStart == travelPlanPages){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        travelPlanStart = travelPlanPages;
        $.ajax({
            type:"post",
            data:{"start":travelPlanStart},
            url:"findTravelByPage",
            async:true,
            success:function (data) {
                travelPlanPages = data.pages;
                $("#travelList").html("");
                var travels = data.travels;
                for(var i = 0 ; i < travels.length ; i++){
                    $("#travelList").append("<tr>"
                        +"<td>"+travels[i].salePlanNumber+"</td>"
                        +"<td>"+travels[i].customer+"</td>"
                        +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                        +"<td>"+travels[i].target+"</td>"
                        +"<td>"+travels[i].bdate+"</td>"
                        +"<td>"+travels[i].creater+"</td>"
                        +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                        +"</tr>");
                }
                $("#travelPageDIV").show();
            }
        })
    }
}

function searchTravel() {
    var travelSearch = $("#travelSearch").val();
    $.ajax({
        type:"post",
        data:{"param" : travelSearch},
        url:"searchTravelPlan",
        async:false,
        success:function (data) {
            $("#travelPageDIV").hide();
            $("#travelList").html("");
            var result = data.result;
            if(result == "OK"){
                var travels = data.travels;
                for(var i = 0 ; i < travels.length ; i++){
                    $("#travelList").append("<tr>"
                        +"<td>"+travels[i].salePlanNumber+"</td>"
                        +"<td>"+travels[i].customer+"</td>"
                        +"<td>"+travels[i].province+travels[i].city+travels[i].address+"</td>"
                        +"<td>"+travels[i].target+"</td>"
                        +"<td>"+travels[i].bdate+"</td>"
                        +"<td>"+travels[i].creater+"</td>"
                        +"<td>"+formatDateTime(travels[i].createDate)+"</td>"
                        +"</tr>");
                }

            }
        }
    })
}

//点击 显示/隐藏 填写更多字段
function showMore() {
    $("#operation2").show();
    $("#moreInfo1").show();
    $("#moreInfo2").show();
    $("#moreInfo3").show();
    $("#operation1").hide();
}
function hideMore() {
    $("#operation2").hide();
    $("#moreInfo1").hide();
    $("#moreInfo2").hide();
    $("#moreInfo3").hide();
    $("#operation1").show();
}

function customerShowMore() {
    $("#operation2").show();
    $("#moreInfo1").show();
    $("#moreInfo2").show();
    $("#moreInfo3").show();
    $("#moreInfo4").show();
    $("#moreInfo5").show();
    $("#moreInfo6").show();
    $("#moreInfo7").show();
    $("#operation1").hide();
}

function customerHideMore() {
    $("#operation2").hide();
    $("#moreInfo1").hide();
    $("#moreInfo2").hide();
    $("#moreInfo3").hide();
    $("#moreInfo4").hide();
    $("#moreInfo5").hide();
    $("#moreInfo6").hide();
    $("#moreInfo7").hide();
    $("#operation1").show();
}

function generateNum() {
    $.ajax({
        type:"post",
        url:"generateSalePlanNum",
        async:false,
        success:function (data) {
            $("#saleWriteNO").val(data);
        }
    })
}


function createNewSaleSpend() {
    var costType = $("#costType").val();
    var describe = $("#describe").val();
    var amount = $("#amount").val();
    var happenDate = $("#happenDate").val();
    var contract = $("#contract").val();
    var principal = $("#principal").val();
    var data = {"costType":costType , "describe":describe , "money":amount , "happenDate":happenDate , "contract":contract , "principal":principal};
    $.ajax({
        type:"post",
        data:data,
        url:"saveSaleSpend",
        async:false,
        success:function (data) {
            if(data == "OK"){
                $.message({
                    message:'数据保存成功',
                    type:'success'
                })
            }else{
                $.message({
                    message:data,
                    type:'error'
                })
            }
        }
    })
}

var saleSpendStart = 1;
var saleSpendPage ;
function loadSaleSpend() {
    saleSpendStart = 1;
    $.ajax({
        type:"post",
        data:{"start":saleSpendStart},
        url:"findSaleSpendByPage",
        async:false,
        success:function (data) {
            $("#spendData").html("");
            $("#saleSpendPageDIV").show();
            saleSpendPage = data.pages;
            var spends = data.spends;
            for(var i = 0 ; i < spends.length ; i++){
                $("#spendData").append("<tr>"
                    +"<td>"+spends[i].costType+"</td>"
                    +"<td>"+spends[i].describe+"</td>"
                    +"<td>"+spends[i].amount+"元"+"</td>"
                    +"<td>"+spends[i].happenDate+"</td>"
                    +"<td>"+spends[i].contract+"</td>"
                    +"<td>"+spends[i].principal+"</td>"
                    +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                    +"<td>"+"<a>编辑</a>"+"</td>"
                    +"</tr>");
            }
        }
    })
}

function getSaleSpendFirst() {
    if(saleSpendStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        saleSpendStart = 1;
        $.ajax({
            type:"post",
            data:{"start":saleSpendStart},
            url:"findSaleSpendByPage",
            async:false,
            success:function (data) {
                $("#spendData").html("");
                $("#saleSpendPageDIV").show();
                // saleSpendPage = data.pages;
                var spends = data.spends;
                for(var i = 0 ; i < spends.length ; i++){
                    $("#spendData").append("<tr>"
                        +"<td>"+spends[i].costType+"</td>"
                        +"<td>"+spends[i].describe+"</td>"
                        +"<td>"+spends[i].amount+"元"+"</td>"
                        +"<td>"+spends[i].happenDate+"</td>"
                        +"<td>"+spends[i].contract+"</td>"
                        +"<td>"+spends[i].principal+"</td>"
                        +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                        +"<td>"+"<a>编辑</a>"+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getSaleSpendPrevious() {
    if(saleSpendStart == 1){
        $.message({
            message:'此页已是第一页',
            type:'warning'
        })
    }else{
        saleSpendStart = saleSpendStart - 1;
        $.ajax({
            type:"post",
            data:{"start":saleSpendStart},
            url:"findSaleSpendByPage",
            async:false,
            success:function (data) {
                $("#spendData").html("");
                $("#saleSpendPageDIV").show();
                // saleSpendPage = data.pages;
                var spends = data.spends;
                for(var i = 0 ; i < spends.length ; i++){
                    $("#spendData").append("<tr>"
                        +"<td>"+spends[i].costType+"</td>"
                        +"<td>"+spends[i].describe+"</td>"
                        +"<td>"+spends[i].amount+"元"+"</td>"
                        +"<td>"+spends[i].happenDate+"</td>"
                        +"<td>"+spends[i].contract+"</td>"
                        +"<td>"+spends[i].principal+"</td>"
                        +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                        +"<td>"+"<a>编辑</a>"+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getSaleSpendNext() {
    if(saleSpendStart == saleSpendPage){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        saleSpendStart = saleSpendStart + 1;
        $.ajax({
            type:"post",
            data:{"start":saleSpendStart},
            url:"findSaleSpendByPage",
            async:false,
            success:function (data) {
                $("#spendData").html("");
                $("#saleSpendPageDIV").show();
                // saleSpendPage = data.pages;
                var spends = data.spends;
                for(var i = 0 ; i < spends.length ; i++){
                    $("#spendData").append("<tr>"
                        +"<td>"+spends[i].costType+"</td>"
                        +"<td>"+spends[i].describe+"</td>"
                        +"<td>"+spends[i].amount+"元"+"</td>"
                        +"<td>"+spends[i].happenDate+"</td>"
                        +"<td>"+spends[i].contract+"</td>"
                        +"<td>"+spends[i].principal+"</td>"
                        +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                        +"<td>"+"<a>编辑</a>"+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function getSaleSpendLast() {
    if(saleSpendStart == saleSpendPage){
        $.message({
            message:'此页已是最后一页',
            type:'warning'
        })
    }else{
        saleSpendStart = saleSpendPage;
        $.ajax({
            type:"post",
            data:{"start":saleSpendStart},
            url:"findSaleSpendByPage",
            async:false,
            success:function (data) {
                $("#spendData").html("");
                $("#saleSpendPageDIV").show();
                // saleSpendPage = data.pages;
                var spends = data.spends;
                for(var i = 0 ; i < spends.length ; i++){
                    $("#spendData").append("<tr>"
                        +"<td>"+spends[i].costType+"</td>"
                        +"<td>"+spends[i].describe+"</td>"
                        +"<td>"+spends[i].amount+"元"+"</td>"
                        +"<td>"+spends[i].happenDate+"</td>"
                        +"<td>"+spends[i].contract+"</td>"
                        +"<td>"+spends[i].principal+"</td>"
                        +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                        +"<td>"+"<a>编辑</a>"+"</td>"
                        +"</tr>");
                }
            }
        })
    }
}

function findSpendByTypeAndPrincipal() {
    var type = $("#spendType option:selected").val();
    var name = $("#principalName").val();
    var data = {"type":type , "name":name};
    $.ajax({
        type:"post",
        data:data,
        url:"findSaleSpendByParams",
        async:false,
        success:function (data) {
            $("#spendData").html("");
            $("#saleSpendPageDIV").hide();
            var result = data.result;
            if(result == "OK"){
                var spends = data.spends;
                for(var i = 0 ; i < spends.length ; i++){
                    $("#spendData").append("<tr>"
                        +"<td>"+spends[i].costType+"</td>"
                        +"<td>"+spends[i].describe+"</td>"
                        +"<td>"+spends[i].amount+"元"+"</td>"
                        +"<td>"+spends[i].happenDate+"</td>"
                        +"<td>"+spends[i].contract+"</td>"
                        +"<td>"+spends[i].principal+"</td>"
                        +"<td>"+formatDateTime(spends[i].createDate)+"</td>"
                        +"<td>"+"<a>编辑</a>"+"</td>"
                        +"</tr>");
                }
            }
        }
    })
}
