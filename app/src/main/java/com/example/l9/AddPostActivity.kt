package com.example.l9

import Post
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.l9.PostRepository.PostViewModel

class AddPostActivity : AppCompatActivity() {
    private lateinit var postViewModel: PostViewModel
    private lateinit var titleEditText: EditText
    private lateinit var bodyEditText: EditText
    private lateinit var saveButton: Button
    private var postId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_post)

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        titleEditText = findViewById(R.id.titleEditText)
        bodyEditText = findViewById(R.id.bodyEditText)
        saveButton = findViewById(R.id.saveButton)

        // Отримуємо постId, якщо він переданий
        postId = intent.getIntExtra("POST_ID", 0)

        if (postId != 0) {
            // Якщо postId не 0, це означає, що ми редагуємо існуючий пост
            postViewModel.fetchPost(postId)
            postViewModel.post.observe(this, Observer { post ->
                titleEditText.setText(post.title)
                bodyEditText.setText(post.body)
            })
        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val body = bodyEditText.text.toString()

            if (title.isNotEmpty() && body.isNotEmpty()) {
                val newPost = Post(
                    id = postId,  // Використовуємо переданий id або 0 для нового поста
                    title = title,
                    body = body,
                    userId = 0
                )

                if (postId == 0) {
                    // Якщо postId == 0, додаємо новий пост
                    postViewModel.addPost(newPost)
                } else {
                    // Якщо пост існує, оновлюємо його
                    postViewModel.updatePost(newPost)
                }
                finish()  // Закриваємо активність після збереження
            } else {
                Toast.makeText(this, "Please fill in both fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
