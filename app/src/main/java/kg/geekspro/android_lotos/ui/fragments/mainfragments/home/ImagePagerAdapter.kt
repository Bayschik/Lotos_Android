package kg.geekspro.android_lotos.ui.fragments.mainfragments.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.models.mainmodels.ActionsModel

class ImagePagerAdapter(
    private val context: Context,
    private val images: List<String>,
    private val onClick:(id:Int)->Unit
) : PagerAdapter() {

    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context).apply {
            Glide.with(context).load(images[position]).into(this)
            scaleType = ImageView.ScaleType.FIT_CENTER
            setOnClickListener { onClick(position) }
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}

