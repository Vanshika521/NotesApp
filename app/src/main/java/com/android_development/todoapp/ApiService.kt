package com.android_development.todoapp

import retrofit2.http.GET

interface ApiService {
    @GET("/posts")
    suspend fun getNotes(): List<NoteDto>
}
