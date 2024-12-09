package com.example.l9;

import PostAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.l9.PostRepository.PostViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var postViewModel: PostViewModel
    private lateinit var postAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addButton: Button = findViewById(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, AddPostActivity::class.java)
            startActivity(intent)
        }

        postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        postAdapter = PostAdapter(emptyList()) { post ->
            val intent = Intent(this, PostDetailActivity::class.java)
            intent.putExtra("POST_ID", post.id)
            startActivity(intent)
        }
        recyclerView.adapter = postAdapter

        // Спостерігаємо за змінами в даних
        postViewModel.posts.observe(this, Observer { posts ->
            Log.d("MainActivity", "Received posts: ${posts.count()}")
            postAdapter.updateData(posts)
        })

        // Спостерігаємо за станом завантаження
        postViewModel.loading.observe(this, Observer { isLoading ->
            findViewById<ProgressBar>(R.id.progressBar).visibility = if (isLoading) View.VISIBLE else View.GONE
        })

        // Спостерігаємо за помилками
        postViewModel.error.observe(this, Observer { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        })

        // Завантажуємо дані
        postViewModel.fetchPosts()
    }
}

