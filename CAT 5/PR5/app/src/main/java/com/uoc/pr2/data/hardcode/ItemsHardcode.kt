package com.uoc.pr2.data.model

import android.content.res.Resources
import com.uoc.pr2.R




/* Returns initial list of Items. */
fun ItemsHardcodeSeminary1(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 1,
            name = "Obstacles",
            image = R.drawable.dog01,
            description = "It consists of a an exposition about the obstacles available in Agility competitions and how to teach the dog to accomplish the tests.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            type = ItemType.BASIC,
            id = 2,
            name = "Dangerous dogs",
            image = R.drawable.dog02,
            description = "Special chapter about how to teach dangerous dogs, a group of technics and resources to deal with these type of dogs.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            id = 3,
            type = ItemType.IMAGE,
            name = "Canicross",
            image = R.drawable.dog03,
            description = "The main difference between Canicross and simply running with your dog is that in Canicross, the dog is attached to the runner’s waist with a bungee leash. In this way, whenever the runner’s feet are off the ground, the dog pulls the runner forward. ",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        )
    )
}

fun ItemsHardcodeSeminary2(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 4,
            name = "What is a Stroke?",
            image = R.drawable.medicine01,
            description = "A stroke, sometimes called a brain attack, occurs when something blocks blood supply to part of the brain or when a blood vessel in the brain bursts. In either case, parts of the brain become damaged or die.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            type = ItemType.IMAGE,
            id = 5,
            name = "Broken leg",
            image = R.drawable.medicine02,
            description = "A broken leg is when you break one of the bones in your leg. It can happen lots of ways, like falling or getting into a car accident. Your leg has four bones (the femur, the patella, the tibia, and the fibula). If there’s an accident, any one of these bones may break (fracture) into two or more pieces.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            type = ItemType.IMAGE,
            id = 6,
            name = "Allergies",
            image = R.drawable.medicine03,
            description = "a damaging immune response by the body to a substance, especially a particular food, pollen, fur, or dust, to which it has become hypersensitive.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        )
    )
}

fun ItemsHardcodeSeminary3(): List<Item> {
    return listOf(
        Item(
            type = ItemType.IMAGE,
            id = 7,
            name = "Neural Network",
            image = R.drawable.ai01,
            description = "A neural network is a method in artificial intelligence that teaches computers to process data in a way that is inspired by the human brain. It is a type of machine learning process, called deep learning, that uses interconnected nodes or neurons in a layered structure that resembles the human brain.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            type = ItemType.IMAGE,
            id = 8,
            name = "Big Data",
            image = R.drawable.ai02,
            description = "Big Data is a term that describes the large volume of data, both structured and unstructured, that floods businesses every day. But it is not the amount of data that is important. What matters with Big Data is what organizations do with the data. Big Data can be analyzed to gain insights that lead to better decisions and strategic business moves.",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        ),
        Item(
            type = ItemType.IMAGE,
            id = 9,
            name = "Artificial Intelligence",
            image = R.drawable.ai03,
            description = "Artificial intelligence is one of the disciplines that has generated the most interest in recent years, not only in fields related to technology, but also in others such as health, finance or sports. Increasingly present in our daily lives",
            view = null,
            item_lat=0.0,
            item_lon=0.0
        )
    )
}






