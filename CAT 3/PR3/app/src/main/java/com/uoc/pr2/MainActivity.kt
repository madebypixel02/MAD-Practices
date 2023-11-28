package com.uoc.pr2

import android.content.Context
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.uoc.pr2.databinding.ActivityMainBinding
import com.uoc.pr2.data.DataSource
import com.uoc.pr2.data.model.Seminary
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.ItemType
import com.uoc.pr2.ui.detail.DetailFragment
import com.uoc.pr2.ui.list.ListFragment
import com.uoc.pr2.ui.list.SeminarsFragment
import com.uoc.pr2.ui.login.LoginFragment
import com.uoc.pr2.ui.login.LoginViewModel
import com.uoc.pr2.ui.login.LoginViewModelFactory

class DetailsTransition : TransitionSet() {
    init {
        setOrdering(ORDERING_TOGETHER)
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        addTransition(ChangeImageTransform())
    }
}


class MainActivity : AppCompatActivity(), LoginFragment.OnFragmentLoginInteractionListener {




    lateinit var dataSource: DataSource

    private val sharedViewModel: LoginViewModel by viewModels<LoginViewModel>() {
        LoginViewModelFactory()
    }

    lateinit var f1: LoginFragment
    var f2:ListFragment? = null
    var seminars_fragment: SeminarsFragment? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default,this)

    //    dataSource.selectItemsSeminars(0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        f1 = LoginFragment()
        f1.loginViewModel = sharedViewModel


        supportFragmentManager.commit {
        //     setReorderingAllowed(true)
            add(R.id.placeholder1, f1)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)


        }



/*
        var f:LoginFragment = supportFragmentManager.findFragmentById(R.id.placeholder1) as LoginFragment
        f.Hello(10)

        var f2:LoginFragment = supportFragmentManager.findFragmentById(R.id.placeholder1) as LoginFragment
        f2.Hello(30)

 */
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount == 1)
        {
            f1.Reset()


        }


        super.onBackPressed()
    }


    fun openSeminar(d: Seminary)
    {
        if(f2==null) {
            f2 = ListFragment.newInstance("", "")
        }

        if(d.id==9999){
            f2!!.showAddRequestButton();
        }
        else{
            f2!!.hideAddRequestButton();
        }

        dataSource.selectItems(d.id)

        supportFragmentManager.commit {
            //setReorderingAllowed(true)
            replace(R.id.placeholder1, f2!!)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)


        }
    }

    fun openDetail(item: Item)
    {
        var f3:DetailFragment = DetailFragment.newInstance("", "")

        f3.item = item


        if(item.type==ItemType.IMAGE) {
            f3.setSharedElementEnterTransition(DetailsTransition())
            f3.setSharedElementReturnTransition(DetailsTransition())

            supportFragmentManager.commit {
                // setReorderingAllowed(true)
                addSharedElement(item.view!!, "itemImage")
                //  replace(R.id.placeholder1, f3!!)
                hide(f2!!)
                add(R.id.placeholder1, f3)
                //   setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                addToBackStack(null)


            }
        }
        else{
            supportFragmentManager.commit {
                hide(f2!!)
                add(R.id.placeholder1, f3)
                addToBackStack(null)
            }
        }

    }

    fun LoginOK(userId:Int)
    {


        dataSource.selectSeminarsUser(userId)

        Log.d("TAG","value")

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager


        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
        // pop login fragment
        // push list fragment


        if(seminars_fragment==null) {
            seminars_fragment = SeminarsFragment.Companion.newInstance()
        }


       supportFragmentManager.commit {

              replace(R.id.placeholder1, seminars_fragment!!)
              setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
              addToBackStack(null)


        }




    }

    override fun onFragmentLoginInteraction(mensaje: String) {
        Log.d("Debug",mensaje)

    }


}