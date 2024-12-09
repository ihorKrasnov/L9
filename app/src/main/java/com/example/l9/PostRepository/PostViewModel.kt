package com.example.l9.PostRepository

import Post
import PostRepository
import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val postRepository = PostRepository()
    val posts: LiveData<List<Post>> = postRepository.posts
    val post: LiveData<Post> = postRepository.post
    val loading: LiveData<Boolean> = postRepository.loading
    val error: LiveData<String> = postRepository.error

    // Завантаження постів
    fun fetchPosts() {
        viewModelScope.launch {
            try {
                postRepository.fetchPosts()
            }
            catch (e: Exception) {
                Log.e("API error", e.message ?:
                "Unexpected error while fetching posts!")
            }
        }
    }

    // Додавання поста
    fun addPost(post: Post) {
        viewModelScope.launch {
            try {
                postRepository.addPost(post)
            }
            catch (e: Exception) {
                Log.e("API error", e.message ?:
                "Unexpected error while adding post!")
            }
        }
    }

    // Оновлення поста
    fun updatePost(post: Post) {
        viewModelScope.launch {
            try {
                postRepository.updatePost(post)
            }
            catch (e: Exception) {
                Log.e("API error", e.message ?:
                    "Unexpected error while updating post!")
            }
        }
    }

    // Отримання поста за id
    fun fetchPost(postId: Int) {
        viewModelScope.launch {
            try {
                val post = postRepository.fetchPost(postId)
            } catch (e: Exception) {
                Log.e("API error", e.message ?:
                    "Unexpected error while getting post!")
            }
        }
    }

    //Видалення поста за id
    fun deletePost(postId: Int) {
        viewModelScope.launch {
            try {
                val res = postRepository.deletePost(postId)
            } catch (e: Exception) {
                Log.e("API error", e.message ?:
                    "Unexpected error while deleting post!")
            }
        }
    }
}
