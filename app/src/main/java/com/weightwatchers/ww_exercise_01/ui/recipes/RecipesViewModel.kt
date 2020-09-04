package com.weightwatchers.ww_exercise_01.ui.recipes

import androidx.lifecycle.*
import com.weightwatchers.ww_exercise_01.data.ErrorCode
import com.weightwatchers.ww_exercise_01.data.Result
import com.weightwatchers.ww_exercise_01.data.Result.*
import com.weightwatchers.ww_exercise_01.data.WeightWatchersRepository
import com.weightwatchers.ww_exercise_01.data.api.NoNetworkConnectionException
import com.weightwatchers.ww_exercise_01.data.model.Recipe
import com.weightwatchers.ww_exercise_01.util.Event
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.ArrayList
import javax.inject.Inject
import kotlin.system.measureTimeMillis

class RecipesViewModel @Inject constructor(private val repository: WeightWatchersRepository) :
        ViewModel() {

    private var _recipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    val recipes: LiveData<List<Recipe>>
        get() = _recipes
    private var _recipesEvent: MutableLiveData<Event<Result<*>>> =
            MutableLiveData(Event(None))
    val recipesEvent: LiveData<Event<Result<*>>>
        get() = _recipesEvent

    fun getCategories() {
        viewModelScope.launch {
            _recipesEvent.value = Event(Loading)

            var result: Result<*>
            val time = measureTimeMillis {
                result = try {
                    repository.getRecipes()
                } catch (e: NoNetworkConnectionException) {
                    e.printStackTrace()
                    Error(code = ErrorCode.NO_DATA_CONNECTION.code)
                }
                if (result is Success) {
                    var values: List<Recipe> = ArrayList()
                    val data = (result as Success).data as List<*>
                    for (item in data.filterIsInstance<Recipe>()) {
                        values = values.plus(item)
                    }
                    _recipes.value = values
                }

            }

            Timber.d("Get recipes duration $time")
            _recipesEvent.value = Event(result)
        }
    }
}
