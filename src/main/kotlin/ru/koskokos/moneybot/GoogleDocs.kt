package ru.koskokos.moneybot

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.AppendValuesResponse
import com.google.api.services.sheets.v4.model.ValueRange
import java.io.FileInputStream
import java.lang.RuntimeException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter


val JSON_FACTORY = JacksonFactory()
val GOOGLE_KEY = System.getenv()["GOOGLE_KEY"]
val SPREADSHEET_ID = System.getenv()["SHEET_ID"]

fun addDataToGoogleDocs(amount: BigDecimal, category: String, localDate: LocalDate) {
    val sheetsService = prepare()
    val sheetTitle = localDate.format(DateTimeFormatter.ofPattern("uuuu.MM"))

    val dateVal = localDate.format(DateTimeFormatter.ofPattern("uuuu.MM.dd"))

    val body: ValueRange = ValueRange()
        .setValues(
            listOf(listOf(dateVal, amount, category))
        )

    val appendResult: AppendValuesResponse = sheetsService.spreadsheets().values()
        .append(SPREADSHEET_ID, sheetTitle, body)
        .setValueInputOption("USER_ENTERED")
        .setInsertDataOption("INSERT_ROWS")
        .setIncludeValuesInResponse(true)
        .execute()
}

fun prepare(): Sheets {
    val httpTransport: HttpTransport = GoogleNetHttpTransport.newTrustedTransport()
    val credential: GoogleCredential = GoogleCredential
        .fromStream(GOOGLE_KEY!!.byteInputStream())
        .createScoped(SheetsScopes.all())
    return Sheets.Builder(httpTransport, JSON_FACTORY, credential).build()
}