package kg.geekspro.android_lotos.ui.fragments.mainfragments.calendar

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kg.geekspro.android_lotos.R

class ImagesAdapter(private val context: Context, private val imageList: List<Uri>) :
    RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_items, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(imageList[position])

    }

    override fun getItemCount(): Int = imageList.size


    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUri: Uri){
            val imageView: ImageView = itemView.findViewById(R.id.image_view)
            Glide.with(context)
                .load(imageUri)
                .into(imageView)
        }


    }
}