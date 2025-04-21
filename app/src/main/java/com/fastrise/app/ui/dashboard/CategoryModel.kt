package com.fastrise.app.ui.dashboard

import java.io.Serializable

data class CategoryModel(
    val CATEGORY: String,
    val ID: Int
) : Serializable {
    override fun toString(): String {
        return CATEGORY
    }
}