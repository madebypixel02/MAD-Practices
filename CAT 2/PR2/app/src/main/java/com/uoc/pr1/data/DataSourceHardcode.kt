
package com.uoc.pr1.data

import androidx.lifecycle.MutableLiveData
import com.uoc.pr1.data.hardcode.SeminarHardcode1
import com.uoc.pr1.data.hardcode.SeminarHardcode2
import com.uoc.pr1.data.model.*
import java.io.IOException


class DataSourceHardcode:DataSource {


    private val seminarItemList = mutableListOf<Item>()

    private val userSeminarList = mutableListOf<Seminar>()

    constructor():super(){
        seminarsLiveData = MutableLiveData(userSeminarList)
        ItemsLiveData = MutableLiveData(seminarItemList)
    }

    override fun login(username: String, password: String): Result<User>?  {
        try {
            // TODO: handle loggedInUser authentication

            var user_harcoded:User
            //BEGIN-CODE-UOC-1.3
            user_harcoded = if (username == "user1@uoc.com") {
                User(1, "Jane Doe")
            } else {
                User(2, "John Doe")
            }
            //END-CODE-UOC-1.3
            return Result.Success(user_harcoded)
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    override fun logout() {
        // TODO: revoke authentication
    }

    // ****************************************************************
    // Seminars
    override open fun selectSeminarssUser(user_id:Int){
        userSeminarList.clear()
        if(user_id==1) {
            userSeminarList.addAll(SeminarHardcode1())
        }
        else{
            userSeminarList.addAll(SeminarHardcode2())
        }
    }





   // ****************************************************************
    // Items
    override fun selectItemsSeminar(seminar_id:Int){
        seminarItemList.clear()
        if(seminar_id==1) seminarItemList.addAll(ItemsHardcodeSeminar1())
        else if(seminar_id==2)  seminarItemList.addAll(ItemsHardcodeSeminar2())
        else if(seminar_id==3)  seminarItemList.addAll(ItemsHardcodeSeminar3())

    }

    override fun addItem(Item: Item) {
        val currentList = ItemsLiveData.value
        if (currentList == null) {
            ItemsLiveData.postValue(mutableListOf(Item))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, Item)
            ItemsLiveData.postValue(updatedList)
        }
    }

    /* Removes Item from liveData and posts value. */
    override fun removeItem(Item: Item) {
        val currentList = ItemsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(Item)
            ItemsLiveData.postValue(updatedList)
        }
    }

    /* Returns Item given an ID. */
    override fun getItemForId(id: Long): Item? {
        ItemsLiveData.value?.let { Items ->
            return Items.firstOrNull{ it.id == id}
        }
        return null
    }





}