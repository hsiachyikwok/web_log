function getLogDetail(id) {

    var request = {
        id:id
    };
    var jsonStr = JSON.stringify(request);
    $.ajax({
        url: "/weblog/api/logger/detail",
        type: 'POST',
        data: jsonStr,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data, status, xhr) {
            window.console.log(data);
            document.title = data.body.dirName + data.body.fileName;
        },
        error: function (xhr, error, exception) {
            // handle the error.
            window.alert(xhr.responseJSON.message);
        }
    })
}