package com.mohankrishna.tvshowsapp.presentation_layer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.mohankrishna.tvshowsapp.ModelClass.Result
import com.mohankrishna.tvshowsapp.R
import com.mohankrishna.tvshowsapp.databinding.ActivityDetailScreenBinding
import com.mohankrishna.tvshowsapp.presentation_layer.adapters.PaginationAdapter
import com.mohankrishna.tvshowsapp.presentation_layer.utils.InternetModeProvider
import com.mohankrishna.tvshowsapp.presentation_layer.viewModels.DetailsScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailScreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailScreenBinding
    lateinit var recycleViewListAdapter: PaginationAdapter

    val myviewModel:DetailsScreenViewModel by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_detail_screen)

        val intent = intent
        val data: Result? = intent.getSerializableExtra("selectedData") as Result?

        recycleViewListAdapter= PaginationAdapter()
        binding.similarRecyclerView.adapter=recycleViewListAdapter
        binding.similarRecyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )


        if(data?.is_favourite==null){
            binding.notFavourite.visibility= View.VISIBLE
            binding.favourite.visibility= View.GONE
        }
        else{
            binding.favourite.visibility= View.VISIBLE
            binding.notFavourite.visibility= View.GONE
        }


        binding.notFavourite.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
               binding.notFavourite.visibility= View.GONE
                binding.favourite.visibility= View.VISIBLE

                var singleData=data
                singleData?.is_favourite=true

                if (singleData != null) {
                    myviewModel.setFavouriteFlag(singleData)
                }
            }

        })
        binding.favourite.setOnClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                binding.notFavourite.visibility= View.VISIBLE
                binding.favourite.visibility= View.GONE

               var singleData=data
                singleData?.is_favourite=false

                if (singleData != null) {
                    if (singleData != null) {
                        myviewModel.setFavouriteFlag(singleData)
                    }
                }
            }
        })

        val imageUrl = "http://image.tmdb.org/t/p/w500${data?.poster_path}"
        Glide.with(this)
            .load(imageUrl)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(binding.imagePoster)


        myviewModel.setData(data?.id.toString())
        CoroutineScope(Dispatchers.IO).launch {
            myviewModel.moviesList.collect {
                recycleViewListAdapter.submitData(it)
            }
        }
    }

}