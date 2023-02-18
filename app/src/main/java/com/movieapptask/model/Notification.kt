package com.movieapptask.model

data class Notification(
    var token: String = "",
    var data: Data
) {
    data class Data(
        var title: String = "",
        var message: String = ""
    )
}
