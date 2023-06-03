package com.dicoding.abednego.mendaurid.ui.detaildaurulang

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.dicoding.abednego.mendaurid.R

class DetailDaurUlangActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_daur_ulang)

        val tvDescription: TextView = findViewById(R.id.tv_description)
        val stringBuilder = StringBuilder()

        val langkah = listOf(
            "Potong terlebih dahulu botol menjadi dua bagian, bagian tutup botol sebaiknya dipotong juga.",
            "Sekarang potong bagian ujung sendok plastik yang cekung.",
            "Gunakan potongan ujung sendok plastik untuk mencetak potongan dari botol plastik yang sudah dipotong menjadi dua bagian.",
            "Cetak sebanyak mungkin sampai sekiranya cukup untuk dibuat hiasan lampu.",
            "Mulailah merangkai potongan cetakan tersebut dengan rapi tanpa diberi lem.",
            "Jika rangkaian sudah benar dan sesuai keinginan, mulailah memberinya lem sedikit demi sedikit dan rangkai satu sama lain sampai membentuk seperti lampu hias.",
            "Agar tampilan lebih menarik dan berwarna gunakan kuas untuk mewarnai kerajinan dari sampah plastik tersebut menggunakan cat warna yang telah ditetapkan."
        )

        for ((index, step) in langkah.withIndex()) {
            stringBuilder.append("${index + 1}. $step\n")
        }

        tvDescription.text = stringBuilder.toString()
    }
}