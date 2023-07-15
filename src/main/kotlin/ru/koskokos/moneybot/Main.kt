package ru.koskokos.moneybot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import java.lang.Exception
import java.math.BigDecimal
import java.time.LocalDate


fun main(args: Array<String>) {
    val telegramToken = System.getenv()["TG_TOKEN"]!!
    val bot = bot {
        token = telegramToken
        dispatch {
            message {
                processMessage(message.text!!, ChatId.fromId(message.chat.id), bot)
            }

            callbackQuery() {
                processMessage(callbackQuery.data, ChatId.fromId(callbackQuery.message!!.chat.id), bot)
            }

        }
    }
    bot.startPolling()
}

data class MoneyCommand(val amount: BigDecimal, val category: Category?)


fun processMessage(text: String, chatId: ChatId, bot: Bot) {
    try {
        val command = parseTextToCommand(text)
        if (command.category == null) {
            val inlineKeyboardMarkup = InlineKeyboardMarkup.create(
                Category.values().map { c ->
                    listOf(
                        InlineKeyboardButton.CallbackData(
                            text = c.names[0],
                            callbackData = "${command.amount.toPlainString()} ${c.names[0]}"
                        )
                    )
                }
            )
            bot.sendMessage(
                chatId = chatId,
                text = "Уточни категорию",
                replyMarkup = inlineKeyboardMarkup,
            )

        } else {
            addDataToGoogleDocs(command.amount, command.category.names.first(), LocalDate.now())
            bot.sendMessage(
                chatId = chatId,
                text = "Понял-принял, ${command.amount} на ${command.category}"
            )
        }
    } catch (e: Exception) {
        bot.sendMessage(
            chatId = chatId,
            text = "Все хуйня, давай по-новой ${e.message}"
        )
        e.printStackTrace()
    }
}


fun parseTextToCommand(messageText: String): MoneyCommand {
    val parts = messageText.trim().split(" ")
    val amountStr = parts[0]
    val amount = BigDecimal(amountStr)
    val category = if (parts.size > 1) {
        val categoryName = parts[1].lowercase()
        Category.values().find { c -> c.names.contains(categoryName) }
    } else null
    return MoneyCommand(amount, category)
}