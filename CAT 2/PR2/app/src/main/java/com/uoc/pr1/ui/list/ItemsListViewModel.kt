
package com.uoc.pr1.ui.list


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uoc.pr1.data.DataSource

class ItemsListViewModel(val dataSource: DataSource) : ViewModel() {

    var itemsLiveData = dataSource.getItemList()

    /* If the name and description are present, create new Item and add it to the datasource */
    fun insertItem(ItemName: String?, ItemDescription: String?) {
        if (ItemName == null || ItemDescription == null) {
            return
        }


    }

    fun SetSeminarsItems(seminar_id:Int){
        dataSource.selectItemsSeminar(seminar_id)

    }


}

class ItemsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ItemsListViewModel(
                dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}