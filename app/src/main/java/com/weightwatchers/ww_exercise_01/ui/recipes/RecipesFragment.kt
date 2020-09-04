package com.weightwatchers.ww_exercise_01.ui.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.weightwatchers.ww_exercise_01.R
import com.weightwatchers.ww_exercise_01.WeightWatchersApplication
import com.weightwatchers.ww_exercise_01.data.Result
import com.weightwatchers.ww_exercise_01.data.model.Recipe
import com.weightwatchers.ww_exercise_01.databinding.FragmentRecipesBinding
import com.weightwatchers.ww_exercise_01.ui.BaseFragment
import com.weightwatchers.ww_exercise_01.ui.MainActivity
import com.weightwatchers.ww_exercise_01.util.Event
import com.weightwatchers.ww_exercise_01.util.showSnackBar
import javax.inject.Inject

class RecipesFragment : BaseFragment() {
    private lateinit var recipesBinding: FragmentRecipesBinding

    @Inject
    lateinit var recipesViewModel: RecipesViewModel

    private lateinit var adapter: RecipesAdapter
    private var recipesLoadingObserver: Observer<Event<Result<*>>>
    private var recipesObserver: Observer<List<Recipe>> = createRecipesObserver()

    init {
        recipesLoadingObserver = createLoadingObserver(
                progressListener = {
                    recipesBinding.pbLoading.visibility = View.VISIBLE
                },
                successListener = {
                    recipesBinding.pbLoading.visibility = View.GONE
                    recipesBinding.rvRecipes.visibility = View.VISIBLE

                },
                errorListener = {
                    recipesBinding.pbLoading.visibility = View.GONE
                }
        )
    }

    private fun createRecipesObserver(): Observer<List<Recipe>> {
        return Observer {
            adapter.setData(it ?: listOf())
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        recipesBinding = DataBindingUtil
                .inflate(inflater, R.layout.fragment_recipes, container, false)
        setupRecyclerView()

        WeightWatchersApplication.weightWatchersAppContext.appComponent.inject(this)

        return recipesBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipesViewModel.recipesEvent.observe(viewLifecycleOwner, recipesLoadingObserver)
        recipesViewModel.recipes.observe(viewLifecycleOwner, recipesObserver)
        if (recipesViewModel.recipes.value?.isNotEmpty() != true) {
            recipesViewModel.getCategories()
        }
    }

    private fun setupRecyclerView() {
        adapter = RecipesAdapter(createCellListener(), requireActivity())
        recipesBinding.rvRecipes.adapter = adapter
        recipesBinding.rvRecipes.setEmptyView(recipesBinding.tvEmptyView)
    }

    private fun createCellListener(): RecipeListener {
        return RecipeListener { filter ->
            val message = if (filter?.isNotEmpty() != true) {
                getString(R.string.no_filter)
            } else {
                getString(R.string.filter_value, filter)
            }
            recipesBinding.flContainer.showSnackBar(message)
        }
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            (activity as MainActivity).supportActionBar?.title =
                    getString(R.string.recipes)
        }
    }
}

