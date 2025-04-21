package com.fastrise.app.ui.services

 class ResponseModel<T> {
    var code: String?=null
    var data: T? = null
    var message: String?=null
    var status: String?=null
}