package com.uoc.pr1.data

import androidx.lifecycle.MutableLiveData
import com.uoc.pr1.data.model.Seminar
import com.uoc.pr1.data.model.Item
import com.uoc.pr1.data.model.User

open class  DataSource {

    protected lateinit var ItemsLiveData: MutableLiveData<MutableList<Item>>
    protected lateinit var seminarsLiveData: MutableLiveData<MutableList<Seminar>>

    open fun login(username: String, password: String): Result<User>?{
        return null
    }
    open fun logout(){}

    // *********************

    open fun selectSeminarssUser(user_id:Int){

    }

    fun getSeminars():MutableLiveData<MutableList<Seminar>>? {

        return seminarsLiveData
    }

    // *******************
    open fun addItem(Item: Item){}
    open fun removeItem(Item: Item){}
    open fun getItemForId(id: Long): Item?{
        return null
    }

    fun getItemList():MutableLiveData<MutableList<Item>>?{
        return ItemsLiveData
    }


    open fun selectItemsSeminar(seminar_id:Int){

    }

    companion object DataSourceFactory{

        enum class DataSourceType {
            Hardcode, Room, Remote
        }

        val Default = DataSourceType.Hardcode

        private var INSTANCE: DataSource? = null

        fun getDataSource(type:DataSourceType): DataSource {
            return synchronized(DataSource::class) {

                if(type==DataSourceType.Hardcode) {
                    val newInstance = INSTANCE ?: DataSourceHardcode()
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