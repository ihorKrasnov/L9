import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class PostRepository {
    private val apiService: ApiService = ApiService.create()
    private val _posts = MutableLiveData<List<Post>>()
    val posts: LiveData<List<Post>> get() = _posts
    val loading = MutableLiveData<Boolean>()
    val error = MutableLiveData<String>()

    private val _post = MutableLiveData<Post>()
    val post: LiveData<Post> get() = _post

    suspend fun fetchPosts() {
        loading.value = true
        try {
            val response = apiService.getPosts()
            _posts.value = response
        } catch (e: Exception) {
            error.value = "Failed to load posts: ${e.localizedMessage}"
        } finally {
            loading.value = false
        }
    }

    suspend fun fetchPost(postId: Int) {
        loading.value = true
        try {
            val response = apiService.getPost(postId)
            _post.value = response
        } catch (e: Exception) {
            error.value = "Failed to load posts: ${e.localizedMessage}"
        } finally {
            loading.value = false
        }
    }


    suspend fun addPost(post: Post) {
        try {
            val response = apiService.createPost(post)
            fetchPosts()
        } catch (e: Exception) {
            error.value = "Failed to add post: ${e.localizedMessage}"
        }
    }

    suspend fun updatePost(post: Post) {
        try {
            val response = apiService.updatePost(post.id, post)
            fetchPosts()
        } catch (e: Exception) {
            error.value = "Failed to update post: ${e.localizedMessage}"
        }
    }

    suspend fun deletePost(postId: Int) {
        try {
            val response = apiService.deletePost(postId)
            fetchPosts()
        } catch (e: Exception) {
            error.value = "Failed to delete post: ${e.localizedMessage}"
        }
    }
}