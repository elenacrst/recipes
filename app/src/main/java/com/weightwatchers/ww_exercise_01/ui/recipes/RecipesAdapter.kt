package com.weightwatchers.ww_exercise_01.ui.recipes

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.weightwatchers.ww_exercise_01.BuildConfig
import com.weightwatchers.ww_exercise_01.data.model.Recipe
import com.weightwatchers.ww_exercise_01.databinding.ItemRecipeBinding
import com.weightwatchers.ww_exercise_01.util.loadImage

class RecipesAdapter(private val clickListener: RecipeListener, private val activity: Activity) :
        RecyclerView.Adapter<RecipeViewHolder>() {

    private var data = ArrayList<Recipe>()

    fun setData(data: List<Recipe>?) {
        this.data = ArrayList()
        if (data != null) {
            this.data.addAll(data)
        }
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRecipeBinding.inflate(layoutInflater, parent, false)

        return RecipeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        val currentItem = data[position]
        holder.bind(clickListener, currentItem, activity)
    }
}

class RecipeListener(val clickListener: (filter: String?) -> Unit) {
    fun onClick(filter: String?) = clickListener(filter)
}

class RecipeViewHolder(private val binding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {

    fun bind(clickListener: RecipeListener, item: Recipe, activity: Activity) {
        binding.clickListener = clickListener
        binding.item = item
        binding.ivRecipe.loadImage(BuildConfig.SERVER_URL + item.image, activity)
    }
}

