package com.example.beers_of_the_world.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.beers_of_the_world.*
import com.example.beers_of_the_world.databinding.FragmentMainBinding
import com.example.beers_of_the_world.databinding.ItemBeerBinding
import com.example.beers_of_the_world.ui.fragments.MainFragment
import com.squareup.picasso.Picasso

class BeerAdapter(private val listener: MainFragment, val abeer: List<BeerResponse>) :
    RecyclerView.Adapter<BeerAdapter.BeerHolder>() {

    private lateinit var binding: FragmentMainBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeerHolder {
        val itemBeerBinding =
            ItemBeerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BeerHolder(itemBeerBinding)
    }

    //we link the data to the list.
    override fun onBindViewHolder(beerholder: BeerHolder, position: Int) {
        val beerItem = abeer[position]

        beerholder.render(abeer[position])


    }


    override fun getItemCount(): Int {
        return abeer.size
    }


    inner class BeerHolder(itemBeerBinding: ItemBeerBinding) :
        RecyclerView.ViewHolder(itemBeerBinding.root), View.OnClickListener {


        val tvid: TextView = itemBeerBinding.tvid
        val tvname: TextView = itemBeerBinding.tvname
        val tvtagline: TextView = itemBeerBinding.tvtagline
        val ivbeer: ImageView = itemBeerBinding.ivbeer

        fun render(beer: BeerResponse) {
            tvid.text = beer.id.toString()
            tvname.text = beer.name
            tvtagline.text = beer.tagline

            Picasso.get().load(beer.image_url).into(ivbeer)
        }

        //Action after clicking in the name of a beer
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position:Int = adapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}


