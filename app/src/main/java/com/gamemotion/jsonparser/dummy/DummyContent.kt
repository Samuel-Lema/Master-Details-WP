package com.gamemotion.jsonparser.dummy

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import java.net.URL
import java.util.ArrayList
import java.util.HashMap

object DummyContent {

    // Lista donde se almacenaran los post
    val ITEMS: MutableList<DummyItem> = ArrayList()

    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private var COUNT = 25

    private lateinit var array: JSONArray

    init {
        var respuesta = ""

        doAsync {
            // Hago una petioción a la API de Wordpress preguntando por los Posts
            respuesta = URL("http://18.224.1.60/WordPress/?rest_route=/wp/v2/posts/").readText()

            // Accedo al hilo principal
            uiThread {

                // Guardo el Json como un array en la variable 'array'
                array = JSONArray(respuesta)
                COUNT = array.length()

                // Recorro la lista 'array' para añadir los post como Items a la pantalla.
                for (i in 0 until COUNT){

                    addItem(createDummyItem(i))
                }
            }
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    // Recojo el ID, Titulo y Descripción de cada Post (DummyItem)
    private fun createDummyItem(position: Int): DummyItem {

        return DummyItem(array.getJSONObject(position).get("id").toString(), array.getJSONObject(position).getJSONObject("title").getString("rendered"), array.getJSONObject(position).getJSONObject("excerpt").getString("rendered"))
    }

    data class DummyItem(val id: String, val content: String, val details: String) {
        override fun toString(): String = content + details
    }
}
