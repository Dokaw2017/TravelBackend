package com.example.data.repository.like

import com.example.Utils.Constants.DEFAULT_ACTIVITY_PAGE_SIZE
import com.example.data.models.Like

interface LikeRepository {
    suspend fun likeParent(userId:String,parentId:String,parentType:Int):Boolean

    suspend fun unlikeParent(userId:String,parentId:String,parentType:Int):Boolean

    suspend fun deleteLikesForParent(parentId: String)

    suspend fun getLikesForParent(
        parentId: String,
        page: Int = 0,
        pageSize: Int = DEFAULT_ACTIVITY_PAGE_SIZE
    ): List<Like>
}