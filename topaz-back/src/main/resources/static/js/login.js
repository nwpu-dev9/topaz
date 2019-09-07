
function login() {

    var account = document.getElementById("input_name");
    account = account.value;

    if (account == null || account=="") {
        alert("名字不能为空");
        return;
    }
    var psword = document.getElementById("input_password");
    psword = psword.value;

    if (psword == null || psword==""){
        alert("密码不能为空");
        return;
    }

    console.log("/api/user/token/" + "?name="+account+"&password="+psword);
    $.ajax({
        url: "/api/user/token/" + "?name="+account+"&password="+psword,
        type: "GET",
        success: function(msg){
            if (msg){
                alert(msg);
                var userName = '<%=session.getAttribute("userName")%>';
                window.location.href = "/admin/manage/?name=" + userName;
            }
            else{
                alert("你的登录是正确的，但是服务器出现了一些问题导致无法登录");
            }
        },
        error: function(msg){
            alert("用户或密码错误");
        }
    });

}
