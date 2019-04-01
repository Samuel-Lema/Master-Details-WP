package com.gamemotion.jsonparser

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_post_detail.*

class PostDetailActivity : AppCompatActivity() {

    /**
     * Una activity que mostrara los detalles de un post en concreto.
     * Los detalles del Post se mostraran como una lista.
     * como en la [PostListActivity].
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {

            // Crea el PostDetailFragment y lo añade a la Activity.

            val fragment = PostDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(
                        PostDetailFragment.ARG_ITEM_ID,
                        intent.getStringExtra(PostDetailFragment.ARG_ITEM_ID)
                    )
                }
            }

            supportFragmentManager.beginTransaction()
                .add(R.id.post_detail_container, fragment)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            android.R.id.home -> {

                navigateUpTo(Intent(this, PostListActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}
