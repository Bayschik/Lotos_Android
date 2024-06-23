package kg.geekspro.android_lotos.ui.fragments.mainfragments.calendar

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kg.geekspro.android_lotos.R
import kg.geekspro.android_lotos.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

class CalendarFragment : Fragment() {

    private lateinit var binding: FragmentCalendarBinding

    private lateinit var imagesAdapter: ImagesAdapter

    private val PICK_IMAGES_REQUEST_CODE = 123
    private val imagesList = mutableListOf<Uri>()
    private var selectedTime = ""



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(layoutInflater)
        return binding.root
    }

    private var date: LocalDate = LocalDate.now()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btns = arrayListOf(

            binding.btn0800,
            binding.btn1030,
            binding.btn1230,
            binding.btn1500,
            binding.btn1830,
            binding.btn1900)

        val services = arguments?.getSerializable("services")
        val m2 = arguments?.getString("m2")
        val typeOfRoom = arguments?.getString("typeOfRoom")
        val roomAmount = arguments?.getInt("roomAmount")
        val totalPrice = arguments?.getInt("totalPrice")


        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy")
        val currentDate = LocalDate.parse(getCurrentDate() , formatter)


        binding.calendar.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = LocalDate.parse("$dayOfMonth/${month+1}/$year" , formatter)

            removeUnusedDates(date=date, currentDate = currentDate)

        }
        binding.btnOrder.setOnClickListener {
            val comment = binding.etComment.text.toString()
            val time = selectedTime

            if (time.isNotBlank() && date != null) {
                val bundle = Bundle().apply {
                    putSerializable("services", services)
                    putParcelableArrayList("images", ArrayList(imagesList))
                    putString("comment", comment)
                    putString("time", time)
                    putString("date", date.toString())
                    putString("m2", m2)
                    putString("typeOfRoom", typeOfRoom)
                    putString("roomAmount", roomAmount.toString())
                }



                binding.etComment.text.clear()

                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray_), PorterDuff.Mode.SRC_IN)

                for(i in btns){
                    if(selectedTime == i.text){
                        i.setBackgroundResource(R.drawable.bg_card)
                        i.setTextColor(Color.BLACK)
                    }
                }
                selectedTime = ""


            } else {

                Toast.makeText(requireContext(), "Не все заполнено!", Toast.LENGTH_SHORT).show()
            }

            Log.d("TAG", "Services: $services")
            Log.d("TAG", "Images: ${ArrayList(imagesList)}")
            Log.d("TAG", "Comment: $comment")
            Log.d("TAG", "Time: $time")
            Log.d("TAG", "Date: $date")
            Log.d("TAG", "m2: $m2")
            Log.d("TAG", "typeOfRoom: $typeOfRoom")
            Log.d("TAG", "roomAmount: $roomAmount")
            Log.d("TAG", "totalPrice: $totalPrice")

            binding.rvImages.visibility = View.GONE
            binding.etComment.clearFocus()
            binding.btnOrder.text = "Заказать"
            binding.btnOrder.isClickable = false
        }

        binding.btnOrder.text = "Заказать за ${totalPrice}с"


        binding.ibAddImage.setOnClickListener{
            openGallery()
        }


        binding.btn0800.setOnClickListener{
            selectedTime = binding.btn0800.text.toString()
            changeButtonBackground(binding.btn0800, btns)
        }

        binding.btn1030.setOnClickListener{
            selectedTime = binding.btn1030.text.toString()
            changeButtonBackground(binding.btn1030, btns)
        }

        binding.btn1230.setOnClickListener{
            selectedTime = binding.btn1230.text.toString()
            changeButtonBackground(binding.btn1230, btns)
        }

        binding.btn1500.setOnClickListener{
            selectedTime = binding.btn1500.text.toString()
            changeButtonBackground(binding.btn1500, btns)
        }

        binding.btn1830.setOnClickListener{
            selectedTime = binding.btn1830.text.toString()
            changeButtonBackground(binding.btn1830, btns)
        }

        binding.btn1900.setOnClickListener{
            selectedTime = binding.btn1900.text.toString()
            changeButtonBackground(binding.btn1900, btns)
        }



    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGES_REQUEST_CODE)
    }

    private fun getCurrentDate(): String{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)+1
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        return "$dayOfMonth/$month/$year"
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data?.clipData != null) {
                imagesList.clear()
                val clipData = data.clipData
                for (i in 0 until clipData!!.itemCount) {

                    val imageUri: Uri = clipData.getItemAt(i).uri
                    imagesList.add(imageUri)
                }
            } else if (data?.data != null) {
                imagesList.clear()
                val imageUri: Uri = data.data!!
                imagesList.add(imageUri)
            }
            if(imagesList.size > 0){
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue), PorterDuff.Mode.SRC_IN)
            }else{
                binding.ibAddImage.setColorFilter(ContextCompat.getColor(requireContext(), R.color.gray_), PorterDuff.Mode.SRC_IN)
            }

            binding.rvImages.visibility = View.VISIBLE
            imagesAdapter = ImagesAdapter(requireContext(), imagesList)
            binding.rvImages.layoutManager = GridLayoutManager(requireContext(), 3)
            binding.rvImages.adapter = imagesAdapter

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun removeUnusedDates(date: LocalDate, currentDate: LocalDate){

        if(date.isBefore(currentDate) || date > currentDate.plusDays(7)){
            binding.btn0800.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn0800.isClickable = false
            binding.btn0800.setTextColor(Color.WHITE)
            binding.btn1030.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn1030.isClickable = false
            binding.btn1030.setTextColor(Color.WHITE)
            binding.btn1230.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn1230.isClickable = false
            binding.btn1230.setTextColor(Color.WHITE)
            binding.btn1500.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn1500.isClickable = false
            binding.btn1500.setTextColor(Color.WHITE)
            binding.btn1830.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn1830.isClickable = false
            binding.btn1830.setTextColor(Color.WHITE)
            binding.btn1900.setBackgroundResource(R.drawable.black_border_gray_bac)
            binding.btn1900.setTextColor(Color.WHITE)

            selectedTime = " "

        }else{
            binding.btn0800.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn0800.isClickable = true
            binding.btn0800.setTextColor(Color.BLACK)
            binding.btn1030.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn1030.isClickable = true
            binding.btn1030.setTextColor(Color.BLACK)
            binding.btn1230.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn1230.isClickable = true
            binding.btn1230.setTextColor(Color.BLACK)
            binding.btn1500.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn1500.isClickable = true
            binding.btn1500.setTextColor(Color.BLACK)
            binding.btn1830.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn1830.isClickable = true
            binding.btn1830.setTextColor(Color.BLACK)
            binding.btn1900.setBackgroundResource(R.drawable.black_border_white_bac)
            binding.btn1900.isClickable = true
            binding.btn1900.setTextColor(Color.BLACK)
        }
    }


    private fun changeButtonBackground(btn: Button, btns: ArrayList<AppCompatButton>){
        for (i in btns){
            if(i == btn){
                i.setBackgroundResource(R.drawable.bg_cart_blue)
                i.setTextColor(Color.WHITE)
            }else{
                i.setBackgroundResource(R.drawable.bg_card)
                i.setTextColor(Color.BLACK)
            }
        }
    }


}