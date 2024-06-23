package kg.geekspro.android_lotos.ui.fragments.mainfragments.home

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import kg.geekspro.android_lotos.R

class ImagePagerAdapter(
    private val context: Context,
    private val images: List<Int>,
    private val titles: List<String>,
    private val descriptions: List<String>
) : PagerAdapter() {

    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(context).apply {
            setImageResource(images[position])
            scaleType = ImageView.ScaleType.FIT_CENTER
            setOnClickListener {
                // Обработчик нажатия на картинку
                onImageClick(position)
            }
        }
        container.addView(imageView)
        return imageView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    private fun onImageClick(position: Int) {
        // Реализуйте переход на новый фрагмент
        val activity = context as FragmentActivity
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        val newFragment = PromotionFragment.newInstance(images[position], titles[position], descriptions[position])
        fragmentTransaction.replace(R.id.fragment_container, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}

