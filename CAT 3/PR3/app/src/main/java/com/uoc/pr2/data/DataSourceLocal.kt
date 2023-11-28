package com.uoc.pr2.data

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.uoc.data.localstorage.DbHelper
import com.uoc.pr2.R
import com.uoc.pr2.data.model.*
import java.io.FileOutputStream
import java.io.InputStream


class DataSourceLocal:  DataSource {

    private lateinit var dbHelper: DbHelper
    private lateinit  var db: SQLiteDatabase
    private lateinit  var db_read: SQLiteDatabase

    private val seminarItemList = mutableListOf<Item>()

    private val userSeminarList = mutableListOf<Seminary>()

    private lateinit  var context: Context

    private var _user_id = 0

    constructor(context: Context?) : super() {
        seminarsLiveData = MutableLiveData(userSeminarList)
        ItemsLiveData = MutableLiveData(seminarItemList)

        dbHelper = DbHelper(context!!)
        db = dbHelper.writableDatabase
        db_read = dbHelper.readableDatabase

        this.context =  context
    }

    override fun login(username: String, password: String): Result<User>? {

        lateinit var result:Result<User>


        //BEGIN-CODE-UOC-3.1

         //var user_harcoded:User  = User(1, "Jane Doe")
         //result =  Result.Success(user_harcoded)

        val str_sql = "SELECT * FROM user WHERE user_username = '$username' and user_pwd = '$password'"



        // END-CODE-UOC-3.1

        return result

    }

    override fun logout() {
        _user_id = 0
    }

    // ****************************************************************
    // Seminars
    @SuppressLint("Range")
    override open fun selectSeminarsUser(user_id: Int) {
        userSeminarList.clear()
        // Load user's seminars
        //BEGIN-CODE-UOC-3.2
        val str_sql = "SELECT * FROM seminar,user_seminar WHERE  usersem_user_id= $user_id and usersem_seminar_id = sem_id"
        val cursor = db.rawQuery(str_sql, null)

        while (cursor.moveToNext()) {
            userSeminarList.add(Seminary(cursor.getInt(cursor.getColumnIndex("sem_id")),
                cursor.getString(cursor.getColumnIndex("sem_name")),
                cursor.getInt(cursor.getColumnIndex("sem_duration")),
                context.filesDir.path + "/media/seminar/" +
                        cursor.getInt(cursor.getColumnIndex("sem_id")) + ".jpg"))
        cursor.close()


        //END-CODE-UOC-3.2

        // Add for every user
        userSeminarList.add(Seminary(
            id = 9999,
            name = "User Requests",
            duration = 0,
            context.filesDir.path + "/media/seminar/9999.jpg",
            image = R.drawable.requestlogo,
            view = null
        ))
    }

    // ****************************************************************
    // Items
    override fun selectItems(id:Int){

        seminarItemList.clear()

        if(id!=9999){
            // Load seminar items
            //BEGIN-CODE-UOC-3.3

            val str_sql = "SELECT * FROM item WHERE item_sem_id= $id"
            val cursor = db.rawQuery(str_sql, null)

            while (cursor.moveToNext()) {
                seminarItemList.add(Item(ItemType.BASIC,
                    cursor.getInt(cursor.getColumnIndex("item_id")),
                    cursor.getString(cursor.getColumnIndex("item_title")),
                    cursor.getString(cursor.getColumnIndex("item_description"))))
            }
            cursor.close()



            // END-CODE-UOC-3.3
        }
        else {
            // Load user request items
            //BEGIN-CODE-UOC-3.4
            val str_sql = "SELECT * FROM request WHERE request_user_id= $_user_id order by request_id DESC"
            val cursor = db.rawQuery(str_sql, null)
            var UserRequestList = mutableListOf<UserRequest>()
            while (cursor.moveToNext()) {
                UserRequestList.add(UserRequest(cursor.getLong(cursor.getColumnIndex("request_id")),
                    cursor.getString(cursor.getColumnIndex("request_description"))))
            }
            cursor.close()
            // END-CODE-UOC-3.4
        }

        ItemsLiveData.postValue(seminarItemList)
    }


    fun getNewId():Int
    {

        val str_sql = "SELECT max(request_id)+1 as 'new_request_id' FROM request"

        val cursor2 = db_read.rawQuery(
            str_sql
            , null)

        var item_id = -1

        with(cursor2) {
            while (moveToNext()) {

                item_id = getInt(getColumnIndexOrThrow("new_request_id"))

            }

        }
        cursor2.close()

        return item_id
    }

    override fun addItem(title:String, description:String, uri:Uri?) {
        var _type: ItemType = ItemType.BASIC
        if(uri!=null){
            _type = ItemType.IMAGE
        }

        val new_id:Int = getNewId()

        var title_sql = title.replace("'","''");
        var description_sql = description.replace("'","''");

        var str_sql = "INSERT INTO 'main'.'request' ('request_id', 'request_type', 'request_title', 'request_description', 'request_user_id') VALUES ($new_id,${_type.v1} , '$title_sql', '$description_sql', $_user_id)"


        //BEGIN-CODE-UOC-6.6.1

        //END-CODE-UOC-6.6.1



        var image_path:String = ""

        //BEGIN-CODE-UOC-6.6.2


        //END-CODE-UOC-6.6.2

        val item:Item = Item(_type,new_id,title,description,image_path)


        //BEGIN-CODE-UOC-6.6.3

        val currentList = ItemsLiveData.value
        if (currentList == null) {
            ItemsLiveData.postValue(mutableListOf(item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, item)
            ItemsLiveData.postValue(updatedList)
        }

        //END-CODE-UOC-6.6.3
    }


    override fun getItemForId(id: Int): Item? {
        ItemsLiveData.value?.let { Items ->
            return Items.firstOrNull{ it.id == id}
        }
        return null
    }


}