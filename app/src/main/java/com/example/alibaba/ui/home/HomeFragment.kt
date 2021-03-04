package com.example.alibaba.ui.home

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.alibaba.R
import com.example.alibaba.ui.room.Alibaba
import com.example.alibaba.ui.room.AlibabaDao
import com.example.alibaba.ui.room.AppAlibabaDatabase

class HomeFragment : Fragment() {

    private lateinit var alibabaDao: AlibabaDao
    private var alibabaProductsAdapter: AlibabaProductsAdapter? = null
    private var alibabaRecyclerView: RecyclerView? = null

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requireContext().checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    1
                )
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        alibabaRecyclerView = root.findViewById(R.id.alibabaRecyclerView)
        searchProducts()
        return root
    }

    private fun searchProducts() {
        alibabaDao = AppAlibabaDatabase.getInstance(requireContext()).alibabaDao()
        setProductsRecyclerView(alibabaDao.getAlibaba())
    }

    private fun setProductsRecyclerView(list: List<Alibaba>) {
        Log.d("josue", "list Products Room: " + list.size.toString())
        alibabaProductsAdapter = AlibabaProductsAdapter(list)
        alibabaRecyclerView!!.adapter = alibabaProductsAdapter
        alibabaRecyclerView!!.layoutManager = LinearLayoutManager(activity)
    }


}