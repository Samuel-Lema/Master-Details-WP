package com.gamemotion.jsonparser

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gamemotion.jsonparser.dummy.DummyContent
import kotlinx.android.synthetic.main.activity_post_detail.*
import kotlinx.android.synthetic.main.post_detail.view.*

class PostDetailFragment : Fragment() {

        private var item: DummyContent.DummyItem? = null

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            arguments?.let {
                if (it.containsKey(ARG_ITEM_ID)) {

                    item = DummyContent.ITEM_MAP[it.getString(ARG_ITEM_ID)]
                    activity?.toolbar_layout?.title = item?.content
                }
            }
        }

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.post_detail, container, false)

            // Show the dummy content as text in a TextView.
            item?.let {
                rootView.post_detail.text = it.details
            }

            return rootView
        }

        companion object {

            const val ARG_ITEM_ID = "id"
        }
}
