package com.example.l9;

import android.content.Intent
import com.example.l9.PostRepository.PostViewModel
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class PostDetailActivity : AppCompatActivity() {
    private lateinit var postViewModel: PostViewModel
    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)

        // Отримуємо ViewModel для детального перегляду поста
        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        postId = intent.getIntExtra("POST_ID", 0)
        postViewModel.fetchPost(postId)

        // Спостерігаємо за даними поста
        postViewModel.post.observe(this, Observer { post ->
            findViewById<TextView>(R.id.titleDetail).text = post.title
            findViewById<TextView>(R.id.bodyDetail).text = post.body
        })

        val updateButton: Button = findViewById(R.id.updateBtn)
        updateButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            intent.putExtra("POST_ID", postId)
            startActivity(intent)
        }

        val deleteButton: Button = findViewById(R.id.removeBtn)
        deleteButton.setOnClickListener {
            postViewModel.deletePost(postId)
            finish()
        }
    }
}
