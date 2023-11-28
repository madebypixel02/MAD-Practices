package com.uoc.data.localstorage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.uoc.pr2.R
import java.io.File
import java.io.FileOutputStream


class DbHelper(context: Context) : SQLiteOpenHelper(context, "food", null, 1) {

    val _context = context

    override fun onCreate(db: SQLiteDatabase) {

        try {

            //BEGIN-CODE-UOC-1.1
            db.execSQL(SQL_CREATE_user)
            db.execSQL(SQL_CREATE_seminar)
            db.execSQL(SQL_CREATE_item)
            db.execSQL(SQL_CREATE_request)
            db.execSQL(SQL_CREATE_user_seminar)
            //END-CODE-UOC-1.1

            //BEGIN-CODE-UOC-1.2
            val command_list = listOf(

                "INSERT INTO 'main'.'user' ('user_id', 'user_username', 'user_pwd', 'user_display_name') VALUES ('1', 'user1@uoc.com', '123456', 'Jane Doe')",
                "INSERT INTO 'main'.'user' ('user_id', 'user_username', 'user_pwd', 'user_display_name') VALUES ('2', 'user2@uoc.com', '123456', 'John Doe')",
                "INSERT INTO 'main'.'seminar' ('sem_id', 'sem_name', 'sem_duration') VALUES ('1', 'Dogs Agility Seminary','60')",
                "INSERT INTO 'main'.'seminar' ('sem_id', 'sem_name', 'sem_duration') VALUES ('2', 'Medicine Seminary','40')",
                "INSERT INTO 'main'.'seminar' ('sem_id', 'sem_name', 'sem_duration') VALUES ('3', 'AI Seminary','30')",

                "INSERT INTO 'main'.'user_seminar' ('usersem_user_id', 'usersem_seminar_id') VALUES ('1', '1')",
                "INSERT INTO 'main'.'user_seminar' ('usersem_user_id', 'usersem_seminar_id') VALUES ('1', '3')",
                "INSERT INTO 'main'.'user_seminar' ('usersem_user_id', 'usersem_seminar_id') VALUES ('2', '2')",
                "INSERT INTO 'main'.'user_seminar' ('usersem_user_id', 'usersem_seminar_id') VALUES ('2', '3')",

                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('1', '2', 'Obstacles', 'It consists of a an exposition about the obstacles available in Agility competitions and how to teach the dog to accomplish the tests.', '1', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('2', '2', 'Dangerous dogs', 'Special chapter about how to teach dangerous dogs, a group of technics and resources to deal with these type of dogs.', '1', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('3', '2', 'Canicross', 'The main difference between Canicross and simply running with your dog is that in Canicross, the dog is attached to the runner’s waist with a bungee leash. In this way, whenever the runner’s feet are off the ground, the dog pulls the runner forward.', '1', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('4', '2', 'What is a Stroke?', 'A stroke, sometimes called a brain attack, occurs when something blocks blood supply to part of the brain or when a blood vessel in the brain bursts. In either case, parts of the brain become damaged or die.', '2', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('5', '2', 'Broken leg', 'A broken leg is when you break one of the bones in your leg. It can happen lots of ways, like falling or getting into a car accident. Your leg has four bones (the femur, the patella, the tibia, and the fibula). If there’s an accident, any one of these bones may break (fracture) into two or more pieces.', '2', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('6', '2', 'Allergies', 'a damaging immune response by the body to a substance, especially a particular food, pollen, fur, or dust, to which it has become hypersensitive.', '2', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('7', '2', 'Neural Network', 'A neural network is a method in artificial intelligence that teaches computers to process data in a way that is inspired by the human brain. It is a type of machine learning process, called deep learning, that uses interconnected nodes or neurons in a layered structure that resembles the human brain.', '3', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('8', '2', 'Big Data', 'Big Data is a term that describes the large volume of data, both structured and unstructured, that floods businesses every day. But it is not the amount of data that is important. What matters with Big Data is what organizations do with the data. Big Data can be analyzed to gain insights that lead to better decisions and strategic business moves.', '3', '', '')",
                "INSERT INTO 'main'.'item' ('item_id', 'item_type', 'item_title', 'item_description', 'item_sem_id', 'item_lat', 'item_lon') VALUES ('9', '2', 'Artificial Intelligence', 'Artificial intelligence is one of the disciplines that has generated the most interest in recent years, not only in fields related to technology, but also in others such as health, finance or sports. Increasingly present in our daily lives', '3', '', '')",

                "INSERT INTO 'main'.'request' ('request_id', 'request_type', 'request_title', 'request_description', 'request_user_id') VALUES ('1', '1', 'Location', 'Where is the seminar going to be placed?', '1')",
                "INSERT INTO 'main'.'request' ('request_id', 'request_type', 'request_title', 'request_description', 'request_user_id') VALUES ('2', '1', 'Schedule', 'At what time is the seminar starting?', '2')"
            )


            //END-CODE-UOC-1.2
            for (command in command_list) {
                db.execSQL(command)
            }
            //BEGIN-CODE-UOC-2

            // Create the /media directory in the app's internal storage
            var directory_path:String = _context.filesDir.path + "/media/"
            var f = File(directory_path,"")
            f.mkdir()
            // Create the /media/seminar directory in the app's internal storage
            directory_path = _context.filesDir.path + "/media/seminar/"
            f = File(directory_path,"")
            f.mkdir()
            // Move three logos to the /media/seminar directory we just created
            MoveResources(directory_path+"1.jpg",R.drawable.doglogo)
            MoveResources(directory_path+"2.jpg",R.drawable.medicinelogo)
            MoveResources(directory_path+"3.jpg",R.drawable.ailogo)
            MoveResources(directory_path+"9999.jpg", R.drawable.requestlogo)
            // Create the /media/item directory in the app's internal storage
            directory_path = _context.filesDir.path + "/media/item/"
            f = File(directory_path,"")
            f.mkdir()
            // Move three dog images to the /media/item directory we just created
            MoveResources(directory_path+"1.jpg",R.drawable.dog01)
            MoveResources(directory_path+"2.jpg",R.drawable.dog02)
            MoveResources(directory_path+"3.jpg",R.drawable.dog03)
            // Move three medicine images to the /media/item directory we just created
            MoveResources(directory_path+"4.jpg",R.drawable.medicine01)
            MoveResources(directory_path+"5.jpg",R.drawable.medicine02)
            MoveResources(directory_path+"6.jpg",R.drawable.medicine03)
            // Move three AI images to the /media/item directory we just created
            MoveResources(directory_path+"7.jpg",R.drawable.ai01)
            MoveResources(directory_path+"8.jpg",R.drawable.ai02)
            MoveResources(directory_path+"9.jpg",R.drawable.ai03)
            // Create the /media/request directory in the app's internal storage
            directory_path = _context.filesDir.path + "/media/request/"
            f = File(directory_path,"")
            f.mkdir()

            //END-CODE-UOC-2


        }
        catch(err:Exception){
            Log.d("error",err.message!!)
        }
    }

    fun MoveResources(filename:String,resource_id:Int){

        val bmp = BitmapFactory.decodeResource(_context.getResources(), resource_id)
        val out = FileOutputStream(filename)
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, out)

    }




    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    private val SQL_CREATE_user = """
CREATE TABLE "user" (
	"user_id"	INTEGER,
	"user_username"	TEXT,
	"user_pwd"	TEXT,
	"user_display_name"	TEXT,
	PRIMARY KEY("user_id")
);
                """

    private val SQL_CREATE_seminar = """
CREATE TABLE "seminar" (
	"sem_id"	INTEGER,
	"sem_name"	TEXT,
    "sem_duration" INTEGER,
	PRIMARY KEY("sem_id")
);
        """

    private val SQL_CREATE_item = """
 CREATE TABLE "item" (
	"item_id"	INTEGER,
	"item_type"	INTEGER,
	"item_title"	TEXT,
	"item_description"	TEXT,
	"item_sem_id"	INTEGER,
	"item_lat"	REAL,
	"item_lon"	REAL,
	PRIMARY KEY("item_id")
);
 """
    private val SQL_CREATE_request = """
     CREATE TABLE "request" (
	"request_id"	INTEGER,
	"request_type"	INTEGER,
	"request_title"	TEXT,
	"request_description"	TEXT,
	"request_user_id"	INTEGER,
	PRIMARY KEY("request_id")
);
        """

    private val SQL_CREATE_user_seminar = """
   CREATE TABLE "user_seminar" (
	"usersem_user_id"	INTEGER,
	"usersem_seminar_id"	INTEGER,
	PRIMARY KEY("usersem_user_id","usersem_seminar_id")
)
        """


}