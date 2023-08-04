package com.example.testselectimage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.testselectimage.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    private lateinit var pickMultipleImagesLauncher: ActivityResultLauncher<Intent>
    private val selectedImagesList: MutableList<Uri> = mutableListOf()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerView.adapter = ImageAdapter(this, selectedImagesList)
        selectImage()
        selectMutipleImages()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun selectMutipleImages() {
        //chọn nhiều ảnh
        pickMultipleImagesLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Kiểm tra xem người dùng đã chọn một ảnh hay nhiều ảnh
                if (result.data?.clipData != null) {// Nếu chọn nhiều ảnh
                    val count = result.data?.clipData?.itemCount ?: 0 // Đếm số lượng ảnh đã chọn
                    for (i in 0 until count) {
                        val imageUri = result.data?.clipData?.getItemAt(i)?.uri // lấy uri của ảnh
                        imageUri?.let{//kiểm tra xem imageUri có null hay không
                            selectedImagesList.add(it)
                        }
                    }
                } else {
                    // Nếu chọn một ảnh
                    val imageUri = result.data?.data
                    imageUri?.let {
                        selectedImagesList.add(it)
                    }
                }
                binding.recyclerView.adapter?.notifyDataSetChanged()
            }
        }

        binding.buttonPickMultipleImages.setOnClickListener {
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)  // Cho phép chọn nhiều ảnh cùng lúc
            pickMultipleImagesLauncher.launch(intent)
        }
    }

    private fun selectImage() {
        //Chọn 1 ảnh
        // Khởi tạo ActivityResultLauncher với ActivityResultContracts.StartActivityForResult()
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    selectedImagesList.add(it)
                    binding.recyclerView.adapter?.notifyDataSetChanged()
                }
            }
        }
        binding.buttonPickImage1.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*" //Đặt kiểu dữ liệu (MIME type) của Intent là "image/*" để chỉ định rằng chỉ tìm kiếm các tệp ảnh.
            // Bắt đầu hoạt động chọn ảnh bằng ActivityResultLauncher
            pickImageLauncher.launch(intent)

        }

        binding.buttonPickImage2.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh từ Bộ sưu tập hoặc ứng dụng ảnh
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // Bắt đầu hoạt động chọn ảnh bằng ActivityResultLauncher
            pickImageLauncher.launch(intent)
        }

        binding.buttonPickImage3.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            // Bắt đầu hoạt động chọn ảnh bằng ActivityResultLauncher
            pickImageLauncher.launch(intent)
        }
    }
}

// ACTION_GET_CONTENT: Hành động này cho phép người dùng lựa chọn ảnh từ các nguồn khác nhau, bao gồm cả trong tệp và trong ứng dụng ảnh.
// ACTION_OPEN_DOCUMENT: Hành động này yêu cầu người dùng mở một tài liệu hoặc tệp được lưu trữ trên thiết bị.
// ACTION_PICK: Hành động này yêu cầu người dùng lựa chọn ảnh từ một tập hợp các mục.






