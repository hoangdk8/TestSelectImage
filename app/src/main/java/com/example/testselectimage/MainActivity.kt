package com.example.testselectimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.testselectimage.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var pickImageLauncher: ActivityResultLauncher<Intent>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Khởi tạo ActivityResultLauncher với ActivityResultContracts.StartActivityForResult()
        pickImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Xử lý kết quả chọn ảnh tại đây
            if (result.resultCode == Activity.RESULT_OK) {
                // Lấy URI của ảnh đã chọn từ Intent trả về
                val selectedImageUri = result.data?.data
                // Hiển thị ảnh đã chọn lên ImageView
                binding.imageView.setImageURI(selectedImageUri)
            }
        }
        binding.buttonPickImage1.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =
                "image/*" //Đặt kiểu dữ liệu (MIME type) của Intent là "image/*" để chỉ định rằng chỉ tìm kiếm các tệp ảnh.
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






