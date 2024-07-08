package kg.geekspro.android_lotos

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

class ReviewImagesAdapter(private val context: Context, private val imageList: List<Uri>):Adapter<ReviewImagesAdapter.ReviewImagesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImagesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_items, parent, false)
        return ReviewImagesViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    override fun onBindViewHolder(holder: ReviewImagesViewHolder, position: Int) {
        holder.bind(imageList[position])
    }

    inner class ReviewImagesViewHolder(itemView: View):ViewHolder(itemView){
        fun bind(uri: Uri) {
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            Glide.with(imageView)
                .load(uri)
                .into(imageView)
        }

    }
}