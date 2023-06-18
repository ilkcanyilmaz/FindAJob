package com.ilkcanyilmaz.findajob.data

import java.lang.Exception

sealed class Result<out R> {
    data class Success<out R>(val result: R):Result<R>()
    data class Failure(val exception: Exception):Result<Nothing>()
}