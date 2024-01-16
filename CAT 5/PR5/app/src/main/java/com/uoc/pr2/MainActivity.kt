package com.uoc.pr2

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.ChangeBounds
import android.transition.ChangeImageTransform
import android.transition.ChangeTransform
import android.transition.TransitionSet
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.uoc.pr2.databinding.ActivityMainBinding
import com.uoc.pr2.data.DataSource
import com.uoc.pr2.data.ListenerData
import com.uoc.pr2.data.model.Seminary
import com.uoc.pr2.data.model.Item
import com.uoc.pr2.data.model.ItemType
import com.uoc.pr2.ui.detail.DetailFragment
import com.uoc.pr2.ui.list.DiciplinesFragment
import com.uoc.pr2.ui.list.ListFragment
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

    companion object {
        public lateinit var gmainActivity:MainActivity
    }


    lateinit var dataSource: DataSource

    private val sharedViewModel: LoginViewModel by viewModels<LoginViewModel>() {
        LoginViewModelFactory()
    }

    lateinit var f1: LoginFragment
    var f2:ListFragment? = null
    var seminars_fragment: DiciplinesFragment? = null
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg + token, Toast.LENGTH_SHORT).show()
        })
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "food"
            val channelName = "food"
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(
                NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_HIGH)
            )
        }
        val intent = intent
        if (intent.extras != null) {
            val msg = intent.getStringExtra("msg")
            if (msg != null) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Notification Message")
                builder.setMessage(msg)
                builder.setPositiveButton("OK", null)
                builder.create().show()
            }
        }
        gmainActivity = this;

        dataSource = DataSource.getDataSource(DataSource.DataSourceFactory.Default,this)

    //    dataSource.selectItemsSeminar(0)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkLocationPermission()
        configLocation()



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
    @RequiresApi(Build.VERSION_CODES.N)
    private fun checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 99)
        } else {
            configLocation()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    private fun configLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    val lat = location.latitude
                    val lng = location.longitude
                    Toast.makeText(this, "$lat, $lng", Toast.LENGTH_LONG).show()
                    val notificationBuilder = NotificationCompat.Builder(this, "food")
                        .setSmallIcon(R.drawable.ailogo)
                        .setContentTitle("PR2Seminar")
                        .setContentText("Start Track")
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true)
                    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    NotificationManager.IMPORTANCE_HIGH
                    notificationManager.notify(0, notificationBuilder.build())

                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Error getting location", e)
            }
    }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            when (requestCode) {
                99 -> {
                    if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        configLocation()
                    } else {
                        Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show()
                    }
                    return
                }
            }
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


/*
        dataSource.selectItems(d.id)
        supportFragmentManager.commit {
            //setReorderingAllowed(true)
            replace(R.id.placeholder1, f2!!)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)


        }
  */
        listener.onItemsSeminar = {

            supportFragmentManager.commit {
                //setReorderingAllowed(true)
                replace(R.id.placeholder1, f2!!)
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                addToBackStack(null)


            }
        }

        dataSource.selectItemsAsync(d.id,listener)


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


    fun LoginOK2()
    {


        Log.d("TAG","value")

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager


        imm?.hideSoftInputFromWindow(binding.root.windowToken, 0)
        // pop login fragment
        // push list fragment


        if(seminars_fragment==null) {
            seminars_fragment = DiciplinesFragment.Companion.newInstance()
        }


        supportFragmentManager.commit {

            replace(R.id.placeholder1, seminars_fragment!!)
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            addToBackStack(null)


        }

    }

    var listener = ListenerData()
    fun LoginOK(userId:Int)
    {

        dataSource.selectSeminarsUser(userId)


        LoginOK2()

    }

    override fun onFragmentLoginInteraction(mensaje: String) {
        Log.d("Debug", mensaje)

    }


}