package com.uoc.pr2.data

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.Seminary
import com.uoc.pr2.data.model.User

class DataSourceException(message: String) : Exception(message) {

}



open class  DataSource {

    protected lateinit var ItemsLiveData: MutableLiveData<MutableList<Item>>
    protected lateinit var seminarsLiveData: MutableLiveData<MutableList<Seminary>>

    open fun login(username: String, password: String): Result<User>?{
        return null
    }
    open fun logout(){}

    // *********************

    open fun selectSeminarsUser(user_id:Int){

    }

    fun getSeminars():MutableLiveData<MutableList<Seminary>>? {

        return seminarsLiveData
    }

    // *******************
    open fun addItem(title:String, descripton:String,uri: Uri?){}
    open fun addItemRequest(item: Item) {}
    open fun removeItem(item: Item){}
    open fun getItemForId(id: Int): Item?{
        return null
    }

    fun getItemList():MutableLiveData<MutableList<Item>>?{
        return ItemsLiveData
    }


    open fun selectItems(id:Int){

    }

    public fun seminarRecommendation(duration:Int, skill: String, experience:Boolean):Int
    {
        // 0 = Dog agility
        // 1 = Medicine
        // 2 = AI

        if(duration<20){
            return 0
        }
        else if(duration>20){
            if(skill.equals("IT")){
                return 2
            }
            else{

                return 0
            }
        }
        else{
            if(duration>50){
                if(!experience)
                    return 1
                else
                    return 2
            }
            else{
                return 0
            }

        }
    }


    companion object DataSourceFactory{

        enum class DataSourceType {
            Hardcode, LocalStorage, RemoteStorage
        }

        //BEGIN-CODE-UOC-4
        val Default = DataSourceType.LocalStorage
        //END-CODE-UOC-4

        private var INSTANCE: DataSource? = null

        fun getDataSource(type:DataSourceType,context: Context?=null): DataSource {
            return synchronized(DataSource::class) {

                if(type==DataSourceType.Hardcode) {
                    val newInstance = INSTANCE ?: DataSourceHardcode()
                    INSTANCE = newInstance
                    newInstance
                }
                else if(type==DataSourceType.LocalStorage) {
                    val newInstance = INSTANCE ?: DataSourceLocal(context)
                    INSTANCE = newInstance
                    newInstance
                }
                else{
                    val newInstance = INSTANCE ?: DataSourceHardcode()
                    INSTANCE = newInstance
                    newInstance
                }
            }
        }
    }

}