package com.chase.kotlincoroutines

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.chase.kotlincoroutines.adapters.PostAdapter
import com.chase.kotlincoroutines.model.Post
import com.chase.kotlincoroutines.network.RetrofitFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.toast
import retrofit2.HttpException
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = RetrofitFactory.makeRetrofitService()
        GlobalScope.launch(Dispatchers.Main) {
            val request = service.getPosts()
            try {
                val response = request.await()
                response.body()?.let { initRecyclerView(it) }
            } catch (e: HttpException) {
                toast(e.code())
            } catch (e: Throwable) {
                toast("Ooops: Something else went wrong")
            }
        }
    }

    private fun initRecyclerView(list:List<Post>) {
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = PostAdapter(list,this)
    }
}
