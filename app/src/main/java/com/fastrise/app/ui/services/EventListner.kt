package com.fastrise.app.ui.services



interface EventListner {
    fun onSuccessResponse(reqType: Int, data: ResponseModel<*>)
    fun onFailureResponse(reqType: Int, data: ResponseModel<*>)
}