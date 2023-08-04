package com.example.testselectimage

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.testselectimage.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pickImageRequestCode = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonPickImage1.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type =
                "image/*" //Đặt kiểu dữ liệu (MIME type) của Intent là "image/*" để chỉ định rằng chỉ tìm kiếm các tệp ảnh.
            // Gửi yêu cầu chọn ảnh và đặt mã yêu cầu là pickImageRequestCode
            startActivityForResult(intent, pickImageRequestCode)
        }

        binding.buttonPickImage2.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh từ Bộ sưu tập hoặc ứng dụng ảnh
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            // Gửi yêu cầu chọn ảnh và đặt mã yêu cầu là pickImageRequestCode
            startActivityForResult(intent, pickImageRequestCode)
        }

        binding.buttonPickImage3.setOnClickListener {
            // Tạo Intent yêu cầu lựa chọn ảnh
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.type = "image/*"
            // Gửi yêu cầu chọn ảnh và đặt mã yêu cầu là pickImageRequestCode
            startActivityForResult(intent, pickImageRequestCode)
        }
    }

    override fun onActivityResult(
        requestCode: Int, //requestCode: Mã yêu cầu ban đầu khi gọi startActivityForResult.
        resultCode: Int,//resultCode: Kết quả của hoạt động con.
        data: Intent?) //data: Intent chứa dữ liệu trả về từ hoạt động con
    {
        super.onActivityResult(requestCode, resultCode, data)

        // Kiểm tra nếu resultCode trả về thành công và requestCode trả về đúng với mã yêu cầu ban đầu
        if (resultCode == Activity.RESULT_OK && requestCode == pickImageRequestCode) {
            // Lấy URI của ảnh đã chọn từ Intent trả về
            val selectedImageUri = data?.data
            // Hiển thị ảnh đã chọn lên ImageView
            binding.imageView.setImageURI(selectedImageUri)
        }
    }
}

// ACTION_GET_CONTENT: Hành động này cho phép người dùng lựa chọn ảnh từ các nguồn khác nhau, bao gồm cả trong tệp và trong ứng dụng ảnh.
// ACTION_OPEN_DOCUMENT: Hành động này yêu cầu người dùng mở một tài liệu hoặc tệp được lưu trữ trên thiết bị.
// ACTION_PICK: Hành động này yêu cầu người dùng lựa chọn ảnh từ một tập hợp các mục.






