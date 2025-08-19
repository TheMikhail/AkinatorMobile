import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carakinatormobile.Data.Car
import com.example.carakinatormobile.Data.Question
import com.example.carakinatormobile.Repo.CarRepositoryClass
import com.example.carakinatormobile.Repo.QuestionRepositoryClass
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AkinatorViewModel : ViewModel() {
    private val carRepository = CarRepositoryClass()
    private val questionRepository = QuestionRepositoryClass()
    val car = CarRepositoryClass()

    private val _allCars = mutableListOf<Car>()
    private val _allQuestions = mutableListOf<Question>()

    private val _filteredCars = MutableStateFlow<List<Car>>(emptyList())
    val filteredCars: StateFlow<List<Car>> = _filteredCars.asStateFlow()

    private val _currentQuestion = MutableStateFlow<Question?>(null)
    val currentQuestion: StateFlow<Question?> = _currentQuestion.asStateFlow()

    private val _remainingQuestions = mutableListOf<Question>()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _allCars.addAll(carRepository.getCar())
            _allQuestions.addAll(questionRepository.getQuestion())
            resetGame()

        }
    }

    fun resetGame() {
        _filteredCars.value = _allCars.toList()
        _remainingQuestions.clear()
        _remainingQuestions.addAll(_allQuestions)
        _currentQuestion.value = getNextQuestion()
    }

    fun answerQuestion(answer: Boolean) {
        viewModelScope.launch {
            val question = _currentQuestion.value ?: return@launch
            val currentCategory = question.category
            _filteredCars.value = _filteredCars.value.filter { car ->
                question.checkCondition(answer, car)
            }
            //подумать как отфильтровать, если вопрос остался 1, то его задавать не нужно!
            if (answer) {
                _remainingQuestions.removeAll { it.category == question.category }
            } else {
                _remainingQuestions.remove(question)
            }

            _currentQuestion.value = getNextQuestion()
        }

    }

    fun getNextQuestion(): Question? {
        return if (_remainingQuestions.isNotEmpty()) {
            _remainingQuestions.random()
        } else {
            null
        }
    }
}

sealed class ViewState {
    class Question(question: Question) : ViewState()
    class Result(cars: List<Car>) : ViewState()
}