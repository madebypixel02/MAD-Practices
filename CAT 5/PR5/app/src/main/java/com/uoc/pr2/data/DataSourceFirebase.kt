package com.uoc.pr2.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.uoc.pr2.data.model.Seminary
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.User
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DataSourceFirebase :  DataSource {

    private val seminarItemList = mutableListOf<Item>()
    private val userSeminarList = mutableListOf<Seminary>()


    private lateinit  var context: Context
    private var _user_id = 0

    constructor(context: Context?) : super() {
        seminarsLiveData = MutableLiveData(userSeminarList)
        ItemsLiveData = MutableLiveData(seminarItemList)
        this.context =  context!!

    }

    override fun loginAsync(username: String, password: String,listener:ListenerData){

        lateinit var result:Result<User>



        val db = FirebaseFirestore.getInstance()
        db.collection("user")
            .whereEqualTo("user_username", username)
            .whereEqualTo("user_pwd", password)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if(!querySnapshot.isEmpty()){
                    val doc_user = querySnapshot.documents.get(0)
                    val user_id = doc_user.data?.get("user_id") as Long
                    var user_display_name = doc_user.data?.get("user_display_name") as String
                    var user:User = User(user_id.toInt(),user_display_name)
                    result =  Result.Success(user)
                    _user_id = user_id.toInt()
                    listener.onLogin(result);
                }
                else{

                    _user_id = 0;
                    listener.onLogin(Result.Error(DataSourceException("User not exists")));
                }
            }
            .addOnFailureListener { exception ->
                _user_id = 0;
                listener.onLogin(Result.Error(DataSourceException("User not exists")));
                Log.w("Firestore", "Error getting documents $exception")
            }



    }

    override fun logout() {
        _user_id = 0
    }


    fun readSeminarsUserIdsAsync(listener:ListenerData){

        val result = mutableListOf<Long>()


    }

    //**********************************************************************
    override fun selectSeminarsUserAsync(user_id:Int, listener:ListenerData) {
        userSeminarList.clear()


        listener.onSeminarsUserIds = { list_ids ->
            if (!list_ids.isEmpty()){



            }
        }

        readSeminarsUserIdsAsync(listener)


    }

    // ************************************************************

    fun  selectRequestAsync(listener:ListenerData){

    }

    override fun selectItemsAsync(id:Int,listener:ListenerData){
        seminarItemList.clear()
        if(id==9999){
            return selectRequestAsync(listener)
        }



    }

    // ****************************************************

    fun ReloadViewModel(item:Item)
    {
        val currentList = ItemsLiveData.value
        if (currentList == null) {
            ItemsLiveData.postValue(mutableListOf(item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, item)
            ItemsLiveData.postValue(updatedList)
        }

    }

    fun getNewRequestId(listener: ListenerData)
    {
        val db = FirebaseFirestore.getInstance()
        db.collection("request")
            .orderBy("request_id", Query.Direction.DESCENDING).limit(1)
            .get()
            .addOnSuccessListener { querySnapshot ->
                val request = querySnapshot.documents.get(0)
                var request_id = request.data?.get("request_id") as Long
                request_id++
                listener.onNewRequestId(request_id.toInt())

            }
            .addOnFailureListener { exception ->
                listener.onNewRequestId(-1)
            }



    }

    override fun addItemAsync(title:String, description:String, uri: Uri?,listener:ListenerData) {

        listener.onNewRequestId = { new_id ->

            lateinit var inputStream:InputStream

            try {
                inputStream = this.context.getContentResolver().openInputStream(uri!!)!!

            }
            catch(e: Exception)
            {
                Log.d("error",e.message!!)

            }

            val bitmap = BitmapFactory.decodeStream(inputStream)

            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference

            val newImageRef = storageRef.child(new_id.toString() + ".jpg")


            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
            val data = baos.toByteArray()

            val uploadTask = newImageRef.putBytes(data)
            uploadTask.addOnFailureListener {
                // Handle unsuccessful uploads
            }.addOnSuccessListener { taskSnapshot ->

                val durl = newImageRef.toString()


            }

        }

        getNewRequestId(listener)



    }


}