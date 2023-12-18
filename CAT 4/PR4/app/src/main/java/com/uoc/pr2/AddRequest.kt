package com.uoc.pr2

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.uoc.pr2.databinding.ActivityAddRequestBinding
import java.io.InputStream

class AddRequest : AppCompatActivity() {
    private lateinit var binding: ActivityAddRequestBinding

    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)




        var getResult  = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            val value = it.data?.getData()
            uri = value!!
            val inputStream: InputStream? = this.getContentResolver().openInputStream(value!!)
            val bitmap = BitmapFactory.decodeStream(inputStream)
            binding.image.setImageBitmap(bitmap)
        }



        binding.btnSelectImage.setOnClickListener {


            //BEGIN-CODE-UOC-6.3
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            getResult.launch(intent)
            //END-CODE-UOC-6.3

        }

        binding.btnNew.setOnClickListener {

            //BEGIN-CODE-UOC-6.4
            val l:AddRequestResult = AddRequestResult(binding.editTitle.text.toString(), binding.editDescription.text.toString(),uri)
            val intent = Intent()
            intent.putExtra(PARAM_ADDREQUESTRESULT_CLASS, l)
            this.setResult(Activity.RESULT_OK,intent)
            finish()
            //END-CODE-UOC-6.4

        }

    }
}