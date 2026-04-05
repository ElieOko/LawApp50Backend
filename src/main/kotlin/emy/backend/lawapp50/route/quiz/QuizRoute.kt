package emy.backend.lawapp50.route.quiz

import emy.backend.lawapp50.route.GlobalRoute

object QuizScope {
    const val PUBLIC = "${GlobalRoute.PUBLIC}/quiz"
    const val PROTECTED = "${GlobalRoute.PROTECT}/quiz"
    const val PRIVATE = "${GlobalRoute.PRIVATE}/quiz"
}
