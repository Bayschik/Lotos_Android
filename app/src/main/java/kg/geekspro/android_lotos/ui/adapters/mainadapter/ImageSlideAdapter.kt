package kg.geekspro.android_lotos.ui.adapters.mainadapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kg.geekspro.android_lotos.databinding.ItemImageBinding

class ImageSlideAdapter(
    private val imgList: ArrayList<Int>
) : RecyclerView.Adapter<ImageSlideAdapter.ImageViewHolder>() {

    class ImageViewHolder(private var binding: ItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(pos: Int) {
            binding.imgView.setImageResource(pos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageViewHolder(
            ItemImageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount() = imgList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.onBind(imgList[position])
    }
}