package com.thejan.flickrimagesearch.view.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.SearchRecentSuggestions
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.thejan.flickrimagesearch.R
import com.thejan.flickrimagesearch.base.Status
import com.thejan.flickrimagesearch.base.ViewModelState
import com.thejan.flickrimagesearch.helper.SuggestionProvider
import com.thejan.flickrimagesearch.model.Photo
import com.thejan.flickrimagesearch.view.adapter.PhotosAdapter
import com.thejan.flickrimagesearch.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), PhotosAdapter.ClickItem, SearchView.OnQueryTextListener,
    SearchView.OnSuggestionListener {

    private lateinit var mViewModel: MainViewModel
    private lateinit var mLayoutManager: LinearLayoutManager
    private lateinit var adapter: PhotosAdapter
    private lateinit var messageFromResponse: String
    private lateinit var responseResult: List<Photo>
    private var isLoading = false
    private var text = ""

    private lateinit var searchView: SearchView

    private var message = Observer<String> { msg ->
        messageFromResponse = msg
    }

    private var response = Observer<List<Photo>> { rp ->
        responseResult = rp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    private fun init() {
        setLayout()
        initViewModel()
        initSubscription()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                SearchRecentSuggestions(this, SuggestionProvider.AUTHORITY, SuggestionProvider.MODE)
                    .saveRecentQuery(query, null)
            }

            val query = intent.getStringExtra(SearchManager.QUERY)
            adapter.clearData()
            setData(query.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
         searchView =
            searchItem.actionView as SearchView
        searchView.queryHint = "Search your Image here"

        searchView.setOnQueryTextListener(this)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.componentName))
        searchView.setQuery("your text", false);
        searchView.isSubmitButtonEnabled = true
        return true
    }

    private fun setLayout() {
        mLayoutManager = GridLayoutManager(applicationContext, 2)
        adapter = PhotosAdapter(ArrayList())
        adapter.setClick(this)
        rvRecyclerView.setHasFixedSize(true)
        rvRecyclerView.layoutManager = mLayoutManager
        rvRecyclerView.adapter = adapter
        setPagination()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun initSubscription() {
        mViewModel.message.observe(this, message)
        mViewModel.responseResult.observe(this, response)
        mViewModel.state!!.observe(this, Observer<ViewModelState> {
            it?.let {
                update(it)
            }
        })
    }

    private fun setData(text: String) {
        mViewModel.getPhotos(1, text)
    }

    private fun setPagination() {
        rvRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    val layoutManager = rvRecyclerView.layoutManager as GridLayoutManager
                    val visibleItemCount = layoutManager.findLastCompletelyVisibleItemPosition() + 1
                    if (visibleItemCount == layoutManager.itemCount) {
                        isLoading = true
                        mViewModel.callToTheNextPage()
                    }
                }
            }
        })
    }

    private fun update(state: ViewModelState) {

        when (state.status) {
            Status.LOADING -> {
                progress.visibility = View.VISIBLE
            }

            Status.SUCCESS -> {
                progress.visibility = View.GONE
                adapter.addItems(responseResult)
            }

            Status.ERROR -> {
                progress.visibility = View.GONE
            }
        }
    }

    override fun clickItem(position: Int) {

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onSuggestionSelect(position: Int): Boolean {
        return false
    }

    override fun onSuggestionClick(position: Int): Boolean {
        return false
    }
}