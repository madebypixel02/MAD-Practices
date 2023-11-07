package com.uoc.pr1.data.hardcode

import com.uoc.pr1.R
import com.uoc.pr1.data.model.Seminar


fun SeminarHardcode1(): List<Seminar> {
    return listOf(
        Seminar(
            id = 1,
            name = "Dogs Agility Seminar",
            duration = 60,
            image = R.drawable.doglogo,
            view = null
        ),
        Seminar(
            id = 3,
            name = "AI Seminar",
            duration = 30,
            image = R.drawable.ailogo,
            view = null
          )
        ,
        Seminar(
            id = 9999,
            name = "User Questions",
            duration = 0,
            image = R.drawable.requestlogo,
            view = null
        )
    )
}

fun SeminarHardcode2(): List<Seminar> {
    return listOf(
        Seminar(
            id = 2,
            name = "Medicine Seminar",
            duration = 40,
            image = R.drawable.medicinelogo,
            view = null
        ),
        Seminar(
            id = 3,
            name = "AI Seminar",
            duration = 30,
            image = R.drawable.ailogo,
            view = null
        )
        ,
        Seminar(
            id = 9999,
            name = "User Questions",
            duration = 30,
            image = R.drawable.requestlogo,
            view = null
        )
    )
}
