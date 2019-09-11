/**
 * 登录
 */
function login(username, password) {
    var request = {
        username: username,
        password: password
    };
    var jsonStr = JSON.stringify(request);
    $.ajax({
        url: "/weblog/user/login",
        type: 'POST',
        data: jsonStr,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data, status, xhr) {
            window.console.log(data);
            window.sessionStorage.setItem("userInfo",JSON.stringify(data.body));
            var userId = data.body.userId;
            window.location.href = "home.html?id=" + userId;
        },
        error: function (xhr, error, exception) {
            // handle the error.
            window.alert(xhr.responseJSON.message)
        }
    })
}

/**
 * 登出
 */
function logout() {
    $.ajax({
        url: "/weblog/user/logout",
        type: 'GET',
        // data: null,
        // dataType: 'json',
        // contentType: 'application/json',
        success: function (data, status, xhr) {
            window.console.log(data);
            window.location.href = "login.html";
            window.sessionStorage.clear();
        },
        error: function (xhr, error, exception) {
            // handle the error.
            // window.alert(xhr.responseJSON.message);
            window.location.href = "login.html";
            window.sessionStorage.clear();
        }
    })
}

/**
 * 修改密码
 */
function updatePwd(newPassword, oldPassword) {

}