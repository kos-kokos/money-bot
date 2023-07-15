package ru.koskokos.moneybot


enum class Category(val names: List<String>) {
    SUPERMARKET(listOf("продукты", "супермаркеты", "супермаркет", "еда", "пятерочка", "вкусвилл", "самокат")),
    TAXI(listOf("такси")),
    RESTAURANT(listOf("кафе", "рестик", "ресторан", "доставка еды", "пицца", "суши")),
    CAR(listOf("авто", "бенз", "бензин")),
    BABY_SITTER(listOf("няня")),
    CHILDREN(listOf("жора")),
    SPORT(listOf("спорт")),
    ENTERTAINMENT(listOf("развлечения")),
    FLAT(listOf("квартира")),
    HOBBY(listOf("хобби")),
    RENOVATION(listOf("ремонт")),
    TRAVEL(listOf("путешествие"))
}