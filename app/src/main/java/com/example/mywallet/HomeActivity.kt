package com.example.mywallet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wallet.R
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeActivity.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeActivity : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    // creating a variable for our "Firebase Database"
    lateinit var firebaseDatabase: FirebaseDatabase
    // creating a variable for our "Database Reference for Firebase"
    lateinit var databaseReference: DatabaseReference

    lateinit var adapter: HomeAdapter


    /**
     * 定義 Firebase 資料讀取後的回傳介面。
     */
    interface FirebaseCallback {
        /**
         * 當資料成功載入時被呼叫。
         * @param items 載入的資料項目列表
         */
        fun onDataLoaded(items: List<MyItem>)

        /**
         * 當資料載入過程中發生錯誤時被呼叫。
         * @param message 錯誤訊息描述
         */
        fun onError(message: String)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

        // below line is used to get the instance of our Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get reference for our database
        databaseReference = firebaseDatabase.getReference("WalletData")

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val recyclerView: RecyclerView = view.findViewById(R.id.home_list)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // 避免 E/RecyclerView: No adapter attached; skipping layout
        adapter = HomeAdapter(emptyList()) // Initialize with empty list
        recyclerView.adapter = adapter

        // 呼叫 getData() 函數並處理回應
        getData(object : FirebaseCallback {
            override fun onDataLoaded(items: List<MyItem>) {
                val adapter = HomeAdapter(items)
                recyclerView.adapter = adapter
            }

            override fun onError(message: String) {
                Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
            }
        })

        return view

    }

    /** 從資料庫取得資料 */
    private fun getData(callback: FirebaseCallback) {
        // 取得資料庫參考
        val databaseReference = FirebaseDatabase.getInstance().getReference("WalletData")

        // 添加 ValueEventListener
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val items = mutableListOf<MyItem>()

                // 遍歷每個子節點
                for (childSnapshot in snapshot.children) {
                    val key = childSnapshot.child("name").getValue(String::class.java) ?: "Unknown"
                    val price = childSnapshot.child("price").getValue(Int::class.java) ?: 0

                    // 使用 Android 圖示作為預設圖片
                    val iconResId = R.drawable.ic_baseline_android_24

                    val myItem = MyItem(iconResId, key, "價格: $price")
                    items.add(myItem)
                }

                // 回傳資料至回呼介面
                callback.onDataLoaded(items)
            }

            override fun onCancelled(error: DatabaseError) {
                // 資料讀取失敗時的處理，回傳錯誤訊息至回呼介面
                callback.onError("資料讀取失敗")
            }
        })
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeActivity().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}