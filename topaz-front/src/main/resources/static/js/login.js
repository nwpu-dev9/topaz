function show_message(message, success) {
    var element = $("#alert");
    element.text(message);
    element.removeClass("alert-success alert-danger");
    if (success === true) {
        element.addClass("alert-success");
    } else {
        element.addClass("alert-danger");
    }
    element.show();
}

function clear_message() {
    var e = $("#alert");
    e.text("");
    e.removeClass("alert-success alert-danger");
    e.hide();
}

$(function () {
    $("#name, #password").bind("focus", clear_message);
    $("#name, #password").bind("keydown", function (event) {
        if (event.keyCode === 13) {
            try_login();
        }
    });
    $("#submit").bind("click", try_login);
});

$("#login-form").bind("submit", function (event) {
    event.preventDefault();
    try_login();
})

function try_login() {
    let name = $("#name").val();
    let password = $("#password").val();
    $.ajax({
        url: "/api/user/token",
        dataType: "json",
        type: "post",
        data: {
            "name": name,
            "password": password,
        },

        statusCode: {
            201: function (result) {
                window.location.href = return_url;
            },
            404: function (result) {
                show_message(result.responseJSON.message,false);
            }
        },
    });
}