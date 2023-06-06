package com.dicoding.abednego.mendaurid.data.item

import com.dicoding.abednego.mendaurid.data.api.response.mendaur.ListRecycle
import com.dicoding.abednego.mendaurid.data.api.response.mendaur.MetodeItem

object FakeDataRecycle {
    val listRecycle: List<ListRecycle> = listOf(
        ListRecycle(
            metode = listOf(
                MetodeItem(
                    langkah = listOf("Potong terlebih dahulu botol menjadi dua bagian, bagian tutup botol sebaiknya dipotong juga.",
                        "Sekarang potong bagian ujung sendok plastik yang cekung.",
                        "Gunakan potongan ujung sendok plastik untuk mencetak potongan dari botol plastik yang sudah dipotong menjadi dua bagian.",
                        "Cetak sebanyak mungkin sampai sekiranya cukup untuk dibuat hiasan lampu.",
                        "Mulailah merangkai potongan cetakan tersebut dengan rapi tanpa diberi lem.",
                        "Jika rangkaian sudah benar dan sesuai keinginan, mulailah memberinya lem sedikit demi sedikit dan rangkai satu sama lain sampai membentuk seperti lampu hias.",
                        "Agar tampilan lebih menarik dan berwarna gunakan kuas untuk mewarnai kerajinan dari sampah plastik tersebut menggunakan cat warna yang telah ditetapkan."),
                    urlGambar = "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/Lampu-hias.jpg",
                    judul = "Lampu Hias dari Botol Plastik",
                    alatDanBahan = listOf("Botol bekas seragam", "Cutter atau gunting", "Lem khusus plastik", "Sendok plastik", "Cat warna", "Kuas")
                ),
                MetodeItem(
                    langkah = listOf("Langkah 1", "Langkah 2", "Langkah 3"),
                    urlGambar = "https://cdn.discordapp.com/attachments/1081749242000986233/1107161021409407038/ab67706f000000024e4a46a8c5d1749877435f1f.png",
                    judul = "Metode 2",
                    alatDanBahan = listOf("Alat 2", "Bahan 2", "Bahan 3")
                )
            ),
            jenis = "plastik",
            totalMethods = 2
        )
    )
    const val error: Boolean = false
}