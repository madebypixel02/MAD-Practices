package com.uoc.pr2.data.hardcode

import com.uoc.pr2.R
import com.uoc.pr2.data.model.Seminary


fun SeminaryHardcode1(): List<Seminary> {
    return listOf(
        Seminary(
            id = 1,
            name = "Dogs Agility Seminary",
            duration = 60,
            "",
            image = R.drawable.doglogo,
            view = null
        ),
        Seminary(
            id = 3,
            name = "AI Seminary",
            duration = 30,
            "",
            image = R.drawable.ailogo,
            view = null
        )
        ,
        Seminary(
            id = 9999,
            name = "User Questions",
            duration = 0,
            "",
            image = R.drawable.requestlogo,
            view = null
        )
    )
}

fun SeminaryHardcode2(): List<Seminary> {
    return listOf(
        Seminary(
            id = 2,
            name = "Medicine Seminary",
            duration = 40,
            "",
            image = R.drawable.medicinelogo,
            view = null
        ),
        Seminary(
            id = 3,
            name = "AI Seminary",
            duration = 30,
            "",
            image = R.drawable.ailogo,
            view = null
        )
        ,
        Seminary(
            id = 9999,
            name = "User Questions",
            duration = 30,
            "",
            image = R.drawable.requestlogo,
            view = null
        )
    )
}
