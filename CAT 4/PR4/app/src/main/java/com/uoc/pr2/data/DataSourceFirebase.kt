package com.uoc.pr2.data

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.uoc.pr2.R
import com.uoc.pr2.data.model.Seminary
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.User
import java.io.ByteArrayOutputStream
import java.io.InputStream

class DataSourceFirebase :  DataSource {

    private val seminarItemList = mutableListOf<Item>()
    private val userSeminaryList = mutableListOf<Seminary>()


    private lateinit  var context: Context
    private var _user_id = 0

    constructor(context: Context?) : super() {
        seminarsLiveData = MutableLiveData(userSeminaryList)
        ItemsLiveData = MutableLiveData(seminarItemList)
        this.context =  context!!

    }

    override fun loginAsync(username: String, password: String,listener:ListenerData){

        lateinit var result:Result<User>

        //BEGIN-CODE-UOC-2.2


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


        // END-CODE-UOC-2.2


    }

    override fun logout() {
        _user_id = 0
    }


    fun readSeminarsUserIdsAsync(listener:ListenerData){

        val result = mutableListOf<Long>()

        //BEGIN-CODE-UOC-3.1
        // Get the current user's ID
        val userId = FirebaseAuth.getInstance().currentUser?.uid

        // Query Firestore for documents where the "user_id" field is equal to the current user's ID
        FirebaseFirestore.getInstance().collection("user_seminar")
            .whereEqualTo("user_id", userId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Loop through the response documents and add the "sem_id" field value to the result list
                    for (document in task.result!!) {
                        val semId = document.data["sem_id"] as Long
                        result.add(semId)
                    }
                    // Call the listener with the result list
                    listener.onSeminarsUserIds(result)
                }
            }
        //END-CODE-UOC-3.1
    }

    //**********************************************************************
    override fun selectSeminarsUserAsync(user_id:Int, listener:ListenerData) {
        userSeminaryList.clear()


        fun Item(id: String, type: Int, title: String, description: String, imageRef: String): Item {

        }
        listener.onSeminarsUserIds = { list_ids ->
            if (!list_ids.isEmpty()){
                //BEGIN-CODE-UOC-3.2
                listener.onSeminarsUserIds = { list_ids ->
                    if (!list_ids.isEmpty()) {
                        //BEGIN-CODE-UOC-3.2
                        val firestore = FirebaseFirestore.getInstance()

                        for (id in list_ids) {
                            firestore.collection("seminar")
                                .document(id.toString())
                                .get()
                                .addOnSuccessListener { document ->
                                    if (document != null) {
                                        val sem_id = document.data?.get("sem_id") as Long
                                        val name = document.data?.get("name") as String
                                        val duration = document.data?.get("duration") as Long
                                        val image_path = context.filesDir.path + "/media/seminar/" + sem_id + ".jpg"
                                        val seminary = Seminary(
                                            id = sem_id.toInt(),
                                            name = name,
                                            duration = duration.toInt(),
                                            image_path = image_path,
                                            image = R.drawable.medicinelogo,
                                            view = null
                                        )
                                        userSeminaryList.add(seminary)
                                    } else {
                                        Log.d(TAG, "No such document")
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Log.d(TAG, "get failed with ", exception)
                                }
                        }

                        // Add the option to access the user's requests
                        val requestSeminary = Seminary(
                            id = 9999,
                            name = "User Requests",
                            duration = 0,
                            image_path = context.filesDir.path + "/media/seminar/9999.jpg",
                            image = R.drawable.requestlogo,
                            view = null
                        )
                        userSeminaryList.add(requestSeminary)

                        // Call the listener with the userSeminaryList
                        listener.onSeminarsUser(userSeminaryList)

                //END-CODE-UOC-3.2
            }
        }

        readSeminarsUserIdsAsync(listener)


    }

    // ************************************************************

    fun  selectRequestAsync(listener:ListenerData){
        // BEGIN-CODE-UOC-6.2
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("request")
            .whereEqualTo("request_user_id", _user_id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val request_id = document.id
                    val request_type = document.data?.get("request_type") as Long
                    val request_title = document.data?.get("request_title") as String
                    val request_description = document.data?.get("request_description") as String
                    val request_imageref = document.data?.get("request_imageref") as String

                    val item = Item(
                        id = request_id,
                        type = request_type.toInt(),
                        title = request_title,
                        description = request_description,
                        imageRef = request_imageref
                    )
                    seminarItemList.add(item)
                }
            }
        }
        // END-CODE-UOC-6.2
    }

    override fun selectItemsAsync(id:Int,listener:ListenerData){
        seminarItemList.clear()
        if(id==9999){
            return selectRequestAsync(listener)
        }

        //BEGIN-CODE-UOC-4.3
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("item")
            .whereEqualTo("item_sem_id", id)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val item_id = document.data?.get("item_id") as Long
                    val item_type = document.data?.get("item_type") as Long
                    val item_title = document.data?.get("item_title") as String
                    val item_description = document.data?.get("item_description") as String
                    val item_imageref = document.data?.get("item_imageref") as String

                    val item = Item(
                        id = item_id.toInt(),
                        type = item_type.toInt(),
                        title = item_title,
                        description = item_description,
                        imageRef = item_imageref
                    )
                    seminarItemList.add(item)
                }
        //END-CODE-UOC-4.3

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

        //BEGIN-CODE-UOC_7.1
        val firestore = FirebaseFirestore.getInstance()

        firestore.collection("request")
            .orderBy("request_id", Query.Direction.DESCENDING)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                var lastRequestId = 0
                for (document in documents) {
                    lastRequestId = (document.data?.get("request_id") as Long).toInt()
                }
                listener.onNewRequestId((lastRequestId + 1).toInt())
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting new request ID", exception)
                listener.onNewRequestId(-1)
            }
        //END-CODE-UOC_7.1




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
                // BEGIN-CODE-UOC-7.3
                
                // END-CODE-UOC-7.3



            }

        }

        getNewRequestId(listener)



    }


}