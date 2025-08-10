package com.madrid.data.dataSource.mapper

import com.madrid.data.dataSource.remote.dto.list.CreateListResponse
import com.madrid.data.dataSource.remote.dto.list.ListOperationResponse
import com.madrid.domain.entity.ListOperationStatus

fun ListOperationResponse.toListOperationStatus(): ListOperationStatus {
    return ListOperationStatus(
        success = this.success ?: false,
        message = this.statusMessage
    )
}

fun CreateListResponse.toCreateListStatus(): ListOperationStatus {
    return ListOperationStatus(
        success = this.success,
        message = this.status_message
    )
}