package com.gamemotion.jsonparser

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.gamemotion.jsonparser.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_post_list.*
import kotlinx.android.synthetic.main.post_list_content.view.*
import kotlinx.android.synthetic.main.post_list.*

class PostListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        // Dandole a este boton cargara los post de la API  de WP

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Carga los Posts", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            this.setupRecyclerView(post_list)
        }

        if (post_detail_container != null) {
            twoPane = true
        }

        // Llamo a la función setupRecyclerView
        setupRecyclerView(post_list)
    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {

        // Carga los post que haya recogido desde la Clase DummyContent
        recyclerView.adapter = SimpleItemRecyclerViewAdapter(this, DummyContent.ITEMS, twoPane)
    }

    class SimpleItemRecyclerViewAdapter(
        private val parentActivity: PostListActivity,
        private val values: List<DummyContent.DummyItem>,
        private val twoPane: Boolean
    ) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as DummyContent.DummyItem
                if (twoPane) {
                    val fragment = PostDetailFragment().apply {
                        arguments = Bundle().apply {
                            putString(PostDetailFragment.ARG_ITEM_ID, item.id)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.post_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, PostDetailActivity::class.java).apply {
                        putExtra(PostDetailFragment.ARG_ITEM_ID, item.id)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.post_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]
                holder.idView.text = item.id
                holder.contentView.text = item.content

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val idView: TextView = view.id_text
            val contentView: TextView = view.content
        }
    }
}
