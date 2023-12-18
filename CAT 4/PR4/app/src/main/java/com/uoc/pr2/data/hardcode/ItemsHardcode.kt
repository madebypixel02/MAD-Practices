package com.uoc.pr2.data.model

import com.uoc.pr2.R


/* Returns initial list of Items. */
fun ItemsHardcodeSeminary1(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 1,
            name = "Obstacles",
            description = "It consists of a an exposition about the obstacles available in Agility competitions and how to teach the dog to accomplish the tests.",
            image = R.drawable.dog01,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.BASIC,
            id = 2,
            name = "Dangerous dogs",
            description = "Special chapter about how to teach dangerous dogs, a group of technics and resources to deal with these type of dogs.",
            image = R.drawable.dog02,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.IMAGE,
            id = 3,
            name = "Canicross",
            description = "The main difference between Canicross and simply running with your dog is that in Canicross, the dog is attached to the runner’s waist with a bungee leash. In this way, whenever the runner’s feet are off the ground, the dog pulls the runner forward. ",
            image = R.drawable.dog03,
            view = null,
            title = item_title
        )
    )
}

fun ItemsHardcodeSeminary2(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 4,
            name = "What is a Stroke?",
            description = "A stroke, sometimes called a brain attack, occurs when something blocks blood supply to part of the brain or when a blood vessel in the brain bursts. In either case, parts of the brain become damaged or die.",
            image = R.drawable.medicine01,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.IMAGE,
            id = 5,
            name = "Broken leg",
            description = "A broken leg is when you break one of the bones in your leg. It can happen lots of ways, like falling or getting into a car accident. Your leg has four bones (the femur, the patella, the tibia, and the fibula). If there’s an accident, any one of these bones may break (fracture) into two or more pieces.",
            image = R.drawable.medicine02,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.IMAGE,
            id = 6,
            name = "Allergies",
            description = "a damaging immune response by the body to a substance, especially a particular food, pollen, fur, or dust, to which it has become hypersensitive.",
            image = R.drawable.medicine03,
            view = null,
            title = item_title
        )
    )
}

fun ItemsHardcodeSeminary3(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 7,
            name = "Neural Network",
            description = "A neural network is a method in artificial intelligence that teaches computers to process data in a way that is inspired by the human brain. It is a type of machine learning process, called deep learning, that uses interconnected nodes or neurons in a layered structure that resembles the human brain.",
            image = R.drawable.ai01,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.IMAGE,
            id = 8,
            name = "Big Data",
            description = "Big Data is a term that describes the large volume of data, both structured and unstructured, that floods businesses every day. But it is not the amount of data that is important. What matters with Big Data is what organizations do with the data. Big Data can be analyzed to gain insights that lead to better decisions and strategic business moves.",
            image = R.drawable.ai02,
            view = null,
            title = item_title
        ),
        Item(
            type = ItemType.IMAGE,
            id = 9,
            name = "Artificial Intelligence",
            description = "Artificial intelligence is one of the disciplines that has generated the most interest in recent years, not only in fields related to technology, but also in others such as health, finance or sports. Increasingly present in our daily lives",
            image = R.drawable.ai03,
            view = null,
            title = item_title
        )
    )
}



