package com.example.sundarua.model

data class QuizModel(
    val id : String = "",
    val title : String = "",
    val subtitle : String = "",
    val time : String = "",
    val questionList : List<QuestionModel> = emptyList()
)

data class QuestionModel(
    val question: String = "",
    val questionImageUrl: String = "",
    val isImageQuestion: Boolean = false,
    val options: List<String> = emptyList(),
    val correct: String = ""
)