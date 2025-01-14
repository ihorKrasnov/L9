import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.l9.R

class PostAdapter(private var posts: List<Post>, private val onClick: (Post) -> Unit) : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts[position]
        holder.bind(post, onClick)
    }

    override fun getItemCount() = posts.size

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.title)

        fun bind(post: Post, onClick: (Post) -> Unit) {
            titleTextView.text = post.title
            itemView.setOnClickListener { onClick(post) }
        }
    }

    // Метод для оновлення даних
    fun updateData(newPosts: List<Post>) {
        posts = newPosts
        notifyDataSetChanged() // Це оновлює список
    }
}
