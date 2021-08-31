package com.taskapp.happyshoppingapp.data.models



/**
 * init the value of onItem click
 *  when user click any item the interface will call the on click with An Item
 * */

interface OnItemClick {
    fun onClick(item: Any)
}