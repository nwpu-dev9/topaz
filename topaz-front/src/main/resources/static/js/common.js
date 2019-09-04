function logout() {
    $.ajax({
            url: "/api/user/token",
            dataType: "json",
            type: "delete",
            data: {},
            success: function (res) {
                window.location.href = "/";
            },
            error: function (xhr) {
                res = JSON.parse(xhr.responseText);
                console.error(res.message);
            }
        }
    );
}