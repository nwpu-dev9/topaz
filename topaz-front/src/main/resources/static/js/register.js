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

function on_validation(element, valid) {
    element.removeClass("is-invalid is-valid");
    if (valid) {
        element.addClass("is-valid");
    } else {
        element.addClass("is-invalid");
    }
}

function validate_phone() {
    var element = $("#phone");
    var phone = element.val();
    on_validation(element, phone.match(/^1\d{10}$/));
}

function validate_name() {
    var element = $("#name");
    var name = element.val();
    on_validation(element, name.length >= 6 && name.length <= 30);
}

function validate_password() {
    var element = $("#password");
    var password = element.val();
    on_validation(element, password.length >= 8);
}

function validate_password_repeat() {
    var element = $("#password-repeat");
    var password = element.val();
    on_validation(element, password === $("#password").val());
}

function check_all() {
    validate_name();
    validate_phone();
    validate_password();
    validate_password_repeat();
    var valid = true;
    $("#name,#phone,#password,#password-repeat").each(function () {
        if (!$(this).hasClass("is-valid")) {
            valid = false;
        }
    });
    return valid;
}

$(function () {
    $("#phone").bind("input", validate_phone);
    $("#name").bind("input", validate_name);
    $("#password").bind("input", validate_password);
    $("#password-repeat").bind("input", validate_password_repeat);
    $("#submit").bind("click", check_all);
});

var time = 3;

function try_register() {
    if (!check_all())
        return;
    $.ajax({
            url: "/api/user",
            dataType: "json",
            type: "post",
            data: {
                "name": $("#name").val(),
                "phoneNumber": $("#phone").val(),
                "password": $("#password").val()
            },
            success: function (res) {
                show_message(res.message, true);
                setTimeout(function () {
                    window.location.href = "/login";
                }, 2000);
            },
            error: function (xhr) {
                res = JSON.parse(xhr.responseText);
                show_message(res.message, false);
            }
        }
    );
}