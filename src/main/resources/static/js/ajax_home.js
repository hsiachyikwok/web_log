/**
 * 渲染分页
 * @param pageData
 */
function renderPaginition(pageData) {
    $(".pagination").empty();
    $(".pagination").append("<li></li>");
    var newLine = '<li><a id = "nav-left" href="#" onclick="prePage()">&laquo;</a></li>';
    $(".pagination li:last").after(newLine);
    var navigationNum = pageData.navigatepageNums;
    if (navigationNum.length > 0) {
        $.each(navigationNum, function (index, element) {
            var newLine = "<li id=nav-" + index + "><a href='#' onclick= navgatePage(" + element + ")>"
                + element + "</a></li>";
            $(
                ".pagination li:last")
                .after(newLine);
        });
        var lastLine = '<li><a id = "nav-right" href="#" onclick="nextPage()">&raquo;</a></li>';
        $(".pagination li:last").after(lastLine);
        $("#nav-0").addClass("active");
    }
    else {
        // TODO 没有数据
    }
}

/**
 * 切换分页
 * @param page
 */
function navgatePage(page) {
    window.console.log('*****nav to' + page + ' page*****');
    var dirSelected = window.sessionStorage.getItem("dir-selected");
    var dirName = null;
    if (dirSelected === null || dirSelected === 'dir-all') {
        dirName = null;
    } else {
        dirName = $(dirSelected).children("a")[0].innerText;
    }
    var num = page - 1;
    $("#nav-" + num).addClass("active");
    var pageData = JSON.parse(window.sessionStorage.getItem("paginition"));
    num = pageData.pageNum - 1;
    $("#nav-" + num).removeClass("active");
    var searchValue = $("#search-value").val();
    $(".warning").empty();
    var userId = getQueryString('id');
    getLogList(userId, dirName, searchValue, page, 10);
}

/**
 * 下一页
 */
function nextPage() {
    var pageData = JSON.parse(window.sessionStorage.getItem("paginition"));
    if (pageData.hasNextPage) {
        var currentPage = pageData.nextPage;
        navgatePage(currentPage);
    } else {
        window.alert("到达最后一页！")
    }
}

/**
 * 上一页
 */
function prePage() {
    var pageData = JSON.parse(window.sessionStorage.getItem("paginition"));
    if (pageData.hasPreviousPage) {
        var currentPage = pageData.prePage;
        navgatePage(currentPage);
    } else {
        window.alert("已是第一页！")
    }
}

/**
 * 渲染dirList
 * @param dirList
 */
function renderDirList(dirList) {
    if (dirList != null && dirList.length > 0) {
        $.each(dirList, function (index, element) {
            var newLine = "<li id=dir-" + index + "><a href='#' onclick= getDirLogList(" + index + ")>"
                + element + "</a></li>";
            $(
                ".nav-pills li:last")
                .after(newLine);
        });
    }
    else {
        // TODO 没有数据
    }
}

/**
 * 拉取目录列表
 * @param userId
 */
function getDirList(userId) {
    var request = {
        id: userId
    };
    var jsonStr = JSON.stringify(request);
    $.ajax({
        url: "/weblog/api/logger/dir/list",
        type: 'POST',
        data: jsonStr,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data, status, xhr) {
            window.console.log(data);
            // jquery渲染html
            renderDirList(data.body);
            window.sessionStorage.removeItem("dir-selected");
        },
        error: function (xhr, error, exception) {
            // handle the error.
            window.alert(xhr.message.errMsg)
        }
    })
}


// render log list
function renderLogList(loggerList) {
    if (loggerList != null && loggerList.length > 0) {
        $.each(loggerList, function (index, element) {
            var newRow = "<tr class = warning><td>"
                + element.id
                + "</td><td>"
                + element.dirName
                + "</td><td>"
                + element.fileName
                + "</td><td>"
                + element.createTime
                + "</td><td>"
                + element.updateTime
                + "</td><td>" +
                "<button class='btn btn-success' onclick=javascript:openLog("
                + element.id
                + ")>查看</button>"
                + "</td>";
            $(
                ".table-bordered tr:last")
                .after(newRow);
        });
    } else {
        // TODO 没有数据
    }
}

/**
 * 条件搜索
 */
function btnSearch() {
    var page = 1;
    var userId = getQueryString('id');
    // 获取输入框值
    var searchVal = $("#search-value").val();
    if (searchVal == null || searchVal === '') {
        searchVal = null;
    }
    $(".warning").empty();
    getLogList(userId, null, searchVal, page, 10);
}

/**
 * 拉取文件列表
 */
function getLogList(userId, dir, searchVal, page, rows, isFistLoad) {
    var request = {
        id: userId,
        fileName: searchVal,
        dirName: dir,
        page: page,
        rows: rows
    };
    var jsonStr = JSON.stringify(request);
    $.ajax({
        url: "/weblog/api/logger/list",
        type: 'POST',
        data: jsonStr,
        dataType: 'json',
        contentType: 'application/json',
        success: function (data, status, xhr) {
            window.console.log(data);
            // jquery渲染html
            renderLogList(data.body.list);
            // 只渲染一次
            if (isFistLoad === true) {
                renderPaginition(data.body);
            }
            window.sessionStorage.setItem("paginition", JSON.stringify(data.body));
        },
        error: function (xhr, error, exception) {
            // handle the error.
            window.alert(xhr.message.errMsg)
        }
    })
}

/**
 * 打开 日志查看页面
 * @param id
 */
function openLog(id) {
    window.open("log.html?id=" + id);
}

/**
 * 查询目录下的文件
 * @param dirName
 */
function getDirLogList(id) {
    // TODO
    var page = 1;
    var dirSelected;
    $(".warning").empty();
    var userId = getQueryString('id');
    if (id === undefined) {
        getLogList(userId, null, null, page, 10, true);
        $("#dir-all").addClass("active");
        dirSelected = window.sessionStorage.getItem("dir-selected");
        if (dirSelected !== null) {
            $(dirSelected).removeClass("active");
        }
        window.sessionStorage.setItem("dir-selected", "dir-all");
        return
    }
    var dirName = $("#dir-" + id).children('a')[0].innerText;
    getLogList(userId, dirName, null, page, 10, true);
    dirSelected = window.sessionStorage.getItem("dir-selected");
    $("#dir-all").removeClass("active");
    $(dirSelected).removeClass("active");
    window.sessionStorage.removeItem("dir-selected");
    $("#dir-" + id).addClass("active");
    window.sessionStorage.setItem("dir-selected", "#dir-" + id);
}