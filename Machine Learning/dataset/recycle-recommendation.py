import json
import pandas as pd
import os
import pathlib

# Constant list of recycle methods per waste
LIST_OF_RECYCLEABLE_WASTE = []
METHODS_PER_WASTE = 5


class RecycleableWaste:
    '''
        A recycleable waste object class which contains the following attributes:
        - jenis: Waste's type
        - url_gambar: Recycle method's picture URL
        - alat_dan_bahan: Recycle method's tools and materials
        - langkah: Recycle method's steps
    '''

    def __init__(self, jenis) -> None:
        self.total_methods = 0 # Default total methods
        self.jenis = jenis
        self.judul = []
        self.url_gambar = []
        self.alat_dan_bahan = []
        self.langkah = []


def json_converter(file_path) -> None:
    '''
        A json converter function by utilizing json dump from RecycleableWaste object:
        - file_path: path to save the json file
    '''
    with open(file_path, 'w') as f:
        json.dump([i.__dict__ for i in LIST_OF_RECYCLEABLE_WASTE],
                  f, ensure_ascii=False)


def generate_data(current_waste, list_judul, list_url, list_alat_dan_bahan, list_langkah):
    '''
        A data generating function by adding title, image url, tools and resources, 
        also recycle method steps from RecycleableWaste object:
        - current_waste: current Recycle Object Waste
        - list_judul: list of title to extend
        - list_url: list of images url to extend
        - list_alat_dan_bahan: list of tools and resources to extend
        - list_langkah: list of recycle method steps to extend
    '''
    print(
        f"Generating recycle method for {current_waste.jenis}. Please wait...")
    current_waste.total_methods = METHODS_PER_WASTE
    current_waste.judul.extend(list_judul)
    current_waste.url_gambar.extend(list_url)
    current_waste.alat_dan_bahan.extend(list_alat_dan_bahan)
    current_waste.langkah.extend(list_langkah)
    print()


def main():
    print('''
 __        __   _                          
 \ \      / /__| | ___ ___  _ __ ___   ___ 
  \ \ /\ / / _ \ |/ __/ _ \| '_ ` _ \ / _ \\
   \ V  V /  __/ | (_| (_) | | | | | |  __/
    \_/\_/ \___|_|\___\___/|_| |_| |_|\___| 
''')
    print("-" * 50)

    # Generate data for kardus
    generate_data(
        LIST_OF_RECYCLEABLE_WASTE[0],
        [
            "Kursi dari Kardus",
            "Meja dari Kardus",
            "Topi Pesulap dari Kardus",
            "Rak Buku dari Kardus",
            "Meja Dudukan Laptop dari Kardus",
        ],
        [
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/12/cara-membuat-kursi-dari-kardus-kerajinan-tangan-dari-kardus-bekas-5.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/12/Kerajinan-meja-dari-kardus-1.jpg",
            "https://3.bp.blogspot.com/-b-HLEFaNcr4/W3I9qnFtcKI/AAAAAAAADAE/5IGioVG_hUExXEQBCEuyx2TqlXIT1RH1gCLcBGAs/s1600/6top.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/10/img_6164950a15ed9.jpg",
            "https://udsregep.com/wp-content/uploads/2020/06/Stand-Laptop-dari-DIY-Kardus-Bekas-yang-Unik-Menarik-dan-Bermanfaat.jpg"
        ],
        [
            ["Kardus karton tebal",
                "Lem karton atau lem yang daya rekatnya kuat (opsional)", "Pemotong kertas / Cutter", "Pensil / bulpoin", "Penggaris"],
            ["Kardus karton tebal",
                "Lem karton atau lem yang daya rekatnya kuat (opsional)", "Pemotong kertas / Cutter", "Pensil / bulpoin", "Penggaris"],
            ["Kardus Bekas", "Pensil", "Cutter", "Penggaris",
                "Obeng, untuk menandai", "Penghapus, untuk menghapus pola"],
            ["Kardus karton tebal", "Lem karton atau lem yang daya rekatnya kuat (opsional)", "Pemotong kertas / Cutter",
             "Pensil / bulpoin", "Penggaris", "Clip besi (optional)", "Stiker warna"],
            ["Kardus Bekas", "Gunting/cutter",
                "Alat tulis", "Lem", "Penggaris", "Pensil"]
        ],
        [
            ["Siapkan kardus karton yang tebal buka dengan lebar", "Siapkan kardus karton yang tebal buka dengan lebar", "Potong kardus karton yang tebal menggunakan cutter sesuai pola",
                "Selanjutnya ada dua cara untuk menyambungkanya, yang pertama bisa membuat lubang pada karton untuk bisa di rakit. Atau juga bisa menggunakan lem untuk melekatkanya, tergantung desain yang digunakan."],
            ["Siapkan kardus bekas yang tebal, jika tidak memilikinya bisa menggunakan kardus tipis dengan jumlah yang banyak.", "Gambar pola dari meja ini sesuai yang kamu inginkan.",
                "Potong sesuai pola yang kamu gambar / jika menggunakan beberapa kardus maka rekatkan dengan lem", "Rakit dengan sesuai pola yang kamu gambar tadi.", "Dan jadilah sebuah meja dari kardus bekas dan siap digunakan."],
            ["Menggambar pola lingkaran di kardus.", "Memotong lingkaran tersebut dengan Cutter dan menghapus bekas pensil agar nampak bersih.",
                "Potong kardus berbentuk persegi panjang, buat garis panjang di bagian kiri dan kanan, serta potong per 2 garis.", "Gabungkan potongan kardus sebelumnya dengan lem sehingga berbentuk topi."],
            ["Gambarlah pola sesuai bentuk yang ingin kamu buat, mulai dari bagian atas bagian bawah kemudian bagian rak-rak kecilnya.", "Mulailah memotong kardus bekas sesuai pola yang di gambar tadi dengan rapi.", "Lapisi dengan stiker warna warna atau memberi cat.",
                "Pertebal kardus pola tadi dengan menempelkan beberapa bentuk yang sama dengan menggunakan lem.", "Rakit kardus yang sudah di hias tadi sesuai pola yang kamu gambar dan potong tadi.", "Pasang setiap sudut sekat dengan klip besi (optional bila diperlukan), dan rapikan."],
            ["Ukur panjang laptop yang akan dibuatkan dudukan", "Buatlah pola pertama di kardusnya langsung atau disecarik kertas.", "Ketinggan pola biasanya berpusat di sisi paling kiri. Buatlah sebanyak 2 buah. Beri ruang untuk penggabungan pola",
                "Buat pola kedua sesuai dengan lebar laptop sebanyak 3 buah. Untuk jarak sisi kanan dan kirinya usahakan disisakan antara 1,5 cm hingga 2 cm.", "Potong kardus bekas tersebut dengan cutter atau gunting", "Susun dan gabungkan pola yang telah digunting"]
        ]
    )

    # Generate data for kaca
    generate_data(
        LIST_OF_RECYCLEABLE_WASTE[1],
        [
            "Lampion Botol Kaca Bekas",
            "Mozaik dari Pecahan Kaca",
            "Hiasan Meja dari Pecahan Kaca",
            "Vas Bunga dari Limbah Kaca",
            "Pajangan Dinding dari Pecahan Kaca"
        ],
        [
            "https://i0.wp.com/rimbasiana.com/wp-content/uploads/2021/06/lampu-2-scaled.jpg?resize=768%2C1024&ssl=1",
            "https://i0.wp.com/lezgetreal.com/wp-content/uploads/2021/01/Mozaik-Kaca-.jpg?w=564&ssl=1",
            "https://i0.wp.com/lezgetreal.com/wp-content/uploads/2021/01/Meja-dengan-Mozaik-Kaca-.jpg?w=500&ssl=1",
            "https://i0.wp.com/lezgetreal.com/wp-content/uploads/2021/01/Pot-Bunga-Indoor-Unik.jpg?w=499&ssl=1",
            "https://i0.wp.com/www.romadecade.org/wp-content/uploads/2022/04/Kerajinan-Frame-Cermin-dari-Kaca.jpg?w=399&ssl=1"
        ],
        [
            ["Botol kaca bekas sirup", "Slotip", "Lampu kawat",
                "Semen putih", "Gelas plastik", "Gunting"],
            ["Pecahan kaca", "Kayu berbentuk persegi", "Lem"],
            ["Pecahan kaca", "Lem", "Cat"],
            ["Cat warna", "Pecahan kaca/botol kaca", "Selotip/perekat lain"],
            ["Pecahan kaca", "Lem", "Triplek", "Cat warna", "Alat Pemotong"]
        ],
        [
            ["Pertama, Masukkan lampu ke dalam botol, lalu tutup pakai selotip agar semen tidak masuk botol.", "Kedua, lubangi bagian bawah gelas plastik untuk membuat cetakan sebagai dudukan nya. Lalu, pasangkan gelas plastik ke dalam botol pastikan gelas plastik seimbang dengan botolnya. Kemudian, rekatkan menggunakan slotip antar botol dan gelas plastik supaya semen tidak bocor.",
                "Ketiga, buat adonan semen dengan air secukup nya, lalu aduk rata. Setelah semen teraduk dengan rata, masukkan semen ke dalam cetakan gelas plastik dan ratakan semen.", "Keempat, diamkan semen sampai setengah mengering, lalu buat jalan untuk kabel, setelah membuat jalan untuk kabel diamkan semen hingga mengering.", "Kelima, setelah semen mengering, buka cetakan gelas plastik dan rapihkan kabel pada jalur yang sudah di buat. Dan, dudukan pun sudah jadi."],
            ["Siapkan alat dan bahan. Sebaiknya untuk menggunakan pecahan kaca yang berukuran kecil agar mudah ditata.",
                "Susun dan lem pecahan kaca pada kayu sesuai dengan pola yang diinginkan.", "Mozaik sudah dapat dipajang pada dinding."],
            ["Siapkan alat dan bahan yang dibutuhkan.", "Desain model hiasan terlebih dahulu.",
                "Padupadankan pecahan-pecahan kaca tersebut dengan lem agar membentuk pola hiasan yang kamu mau. Misalnya, hiasan bunga, dedaunan, dunia satwa dan sebagainya.", "Lakukan berulang sampai motif atau model yang kamu mau selesai."],
            ["Siapkan alat dan bahan yang dibutuhkan.", "Satukan pecahan kaca atau botol kaca yang retak menggunakan selotip. Bentuklah motif sesuai yang kamu mau.", "Berikan cat ke seluruh vas bunga dan tunggulah sampai mengering.",
                "Jika cat sudah benar-benar mengering, berikutnya kamu bisa melepas selotip yang menempel di vas bunga buatanmu.", "Vas bunga impianmu sudah siap digunakan."],
            ["Buatlah dahulu desain pajanganmu.", "Potong triplek sesuai keinginan.", "Potong juga limbah kaca yang berasal dari pecahan dengan warna atau corak beragam.",
                "Tempelkan pecahan-pecahan kaca tersebut pada triplek yang sudah kamu desain sebelumnya. Rekatkan pecahan-pecahan tersebut dengan lem yang sudah disiapkan.", "Lakukan langkah tersebut dengan sabar dan hati-hati.", "Terakhir, kamu bisa melakukan finishing, yakni dengan memberikan cat vernis atau sejenisnya."]
        ]
    )

    # Generate data for besi
    generate_data(
        LIST_OF_RECYCLEABLE_WASTE[2],
        [
            "Pot Tanaman dari Kaleng Besi",
            "Vas Bunga dari Kaleng Besi",
            "Lampu Tidur dari Kaleng Besi",
            "Celengan dari Kaleng Besi",
            "Tempat Pensil dari Kaleng Besi"
        ],
        [
            "https://i0.wp.com/tambahpinter.com/wp-content/uploads/2023/02/Pot-Tanaman-1-660x330.jpg",
            "https://i0.wp.com/tambahpinter.com/wp-content/uploads/2023/02/Vas-Bunga.jpg",
            "https://i0.wp.com/tambahpinter.com/wp-content/uploads/2023/02/Lampu-Tidur.jpg",
            "https://i0.wp.com/tambahpinter.com/wp-content/uploads/2023/02/Celengan.jpg",
            "https://i0.wp.com/tambahpinter.com/wp-content/uploads/2023/02/Tempat-Pensil.jpg"
        ],
        [
            ["Kaleng susu bekas", "Kuas", "Cat warna-warni sesuai selera", "Paku"],
            ["Kaleng susu bekas maupun jenis kaleng bekas lainnya",
                "Stoples kaca, boleh yang bekas atau baru dengan ukuran lebih kecil dari kaleng bekas", "Lem", "Tali", "Gunting"],
            ["Kaleng bekas", "Paku dan palu", "Lampu LED",
                "Cat semprot, boleh menggunakan warna apa saja yang diinginkan"],
            ["Kaleng bekas", "Cat semprot atau cat warna",
                "Kuas", "Cutter", "Koran bekas"],
            ["Kaleng bekas", "Kardus", "Kertas kado", "Berbagai jenis hiasan seperti pita dan kain flanel",
                "Lem", "Gunting", "Spidol atau pena dan penggaris (untuk membuat desain/pola)"]
        ],
        [
            ["Langkah pertama, bersihkan kaleng susu bekas dari berbagai noda dan sisa-sisa tempelan kertas.", "Buatlah lubang pada bagian bawah kaleng dengan paku. Lubang tersebut berfungsi sebagai drainase yang akan mengalirkan air siraman supaya tanah tidak terlalu basah. Tanpa lubang di bawah pot, tanah bisa jadi kelewat basah dan alhasil tanaman akan cepat layu.",
                "Setelah itu, kamu bisa mempercantik kaleng tersebut dengan menggunakan cat warna sesuai keinginan kamu. Dalam tahapan ini, kamu bisa berkreasi semenarik mungkin dan membuat desain yang unik.", "Tunggu sampai cat mengering.", "Jika cat sudah benar-benar kering, kamu bisa memulai menggunakan kerajinan dari kaleng tersebut dengan menaruh media tanam ke dalamnya."],
            ["Pertama-tama, siapkan dulu kaleng bekas, pastikan kamu sudah mencucinya hingga bersih.", "Buanglah bagian tutup kaleng.", "Berikutnya, siapkan tali dengan panjang secukupnya sesuai dengan besar dari kaleng bekas yang kamu gunakan. Tidak ada batasan untuk penggunaan jenis tali, kamu bisa menggunakan tali berbahan sabut kelapa maupun tali jenis lainnya yang cocok.",
                "Lilitkan tali yang sudah kamu siapkan secara melingkar mengelilingi sisi kaleng.", "Gunakan lem ketika melilitkan tali ke permukaan kaleng agar tali bisa menempel lebih rekat.", "Masukkan stoples yang sudah kamu siapkan ke dalam kaleng.", "Isi stoples itu dengan sedikit air gunanya untuk menjaga kesegaran bunga.", "Vas bunga siap digunakan dan kamu bisa langsung memasukkan bunga ke dalam stoples."],
            ["Bersihkan kaleng bekas yang sudah disiapkan. Pastikan kaleng benar-benar bersih supaya tidak lengket saat dipasang lampu.", "Lubangi kaleng sesuai dengan pola yang kamu inginkan. Misalnya, pola bunga, daun, pohon, atau pola yang lain.",
                "Semprotkan cat warna atau pilox pada permukaan kaleng untuk membuat tampilan kaleng semakin cantik.", "Pasang dan rangkai lampu LED ke kaleng yang sudah dihias tersebut.", "Jika sudah berhasil dirangkai, nyalakan lampu LED dan kerajinan dari kaleng berupa lampu tidur siap digunakan."],
            ["Cuci kaleng bekas hingga bersih.", "Lakukan pengecatan dengan menggunakan cat air atau cat pilox. Apabila menggunakan cat air, kamu bisa mengecatnya dengan kuas.", "Jika menggunakan cat pilox, tidak perlu tebal-tebal dan cukup merata ke seluruh permukaan kaleng. Gunakan koran bekas sebagai alas supaya cat tidak mengenai lantai.",
                "Kemudian, jemur kaleng tersebut dengan alas koran tersebut, tunggu sampai kering.", "Untuk bagian lubang celengannya, kamu bisa melubangi bagian tutup kaleng dengan cutter sepanjang 4-5 cm.", "Celengan pun siap digunakan."],
            ["Gunakan kaleng bekas yang sudah dicuci bersih terlebih dulu.", "Ukur kardus dengan ukuran 8 x 5 cm atau sesuai dengan ukuran kaleng. Nantinya akan digunakan sebagai alas permukaan kaleng.", "Gunting kardus sesuai dengan ukurang tadi.",
                "Kamu juga bisa tambahkan kertas kado dan menempelkannya di atas kardus yang sudah ditempel di kaleng.", "Tambahkan hiasan seperti pita, kain flanel yang sudah dibentuk-bentuk, maupun hiasan lainnya yang kamu sukai."]
        ]
    )

    # Generate data for kertas
    generate_data(
        LIST_OF_RECYCLEABLE_WASTE[3],
        [
            "Hiasan Dinding dari Limbah Kertas",
            "Pigura dari Limbah Kertas",
            "Vas Bunga dari Limbah Kertas",
            "Keranjang dari Kertas Koran",
            "Jam Dinding dari Kertas Koran"
        ],
        [
            "https://berita.99.co/wp-content/uploads/2021/05/hiasan-dinding-kertas.jpg",
            "https://berita.99.co/wp-content/uploads/2021/05/pigura-kertas.jpg",
            "https://berita.99.co/wp-content/uploads/2021/05/vas-bunga-kertas.jpg",
            "https://berita.99.co/wp-content/uploads/2021/05/kerajinan-tangan-kertas-keranjang.jpg",
            "https://berita.99.co/wp-content/uploads/2021/05/Jam-Dinding-Dari-Kertas-Koran.jpg"
        ],
        [
            ["Kertas Bekas", "Cat (Opsional) untuk mewarnai kertas", "Lem"],
            ["Kardus", "Gunting/Cutter", "Lem Kertas", "Kertas Majalah Bekas"],
            ["Botol plastik bekas", "Tali", "Kertas hias",
                "Kertas bekas (bisa dari koran atau majalah)", "Lem kertas", "Gunting", "Cutter", "Lem tembak"],
            ["Lidi", "Kertas bekas (bisa dari koran atau majalah)", "Lem"],
            ["Kertas koran bekas", "Jarum", "Mesin jam dinding", "Baterai", "Lem"]
        ],
        [
            ["Potong kertas membentuk kupu-kupu dengan beragam ukuran, dari yang kecil hingga besar.",
                "Susun dan lem helaian kelopak satu demi satu hingga membentuk bunga yang sedang mekar dan tempelkan di dinding dengan susunan sesuai selera."],
            ["Siapkan alat dan bahan.", "Potong 2 lembar kertas yang ukurannya 5 cm lebih lebar dari foto pilihanmu.", "Potong satu kardus di bagian tengahnya untuk muka foto, jangan lupa potong sesuai ukuran.", "Kemudian, tempel foto di antara dua kertas tebal tersebut dan buat penyangga bingkai foto dengan kertas.",
                "Potong kertas majalah dengan ukuran panjang yang bervariasi, gulung kecil-kecil, dan rekatkan dengan lem.", "Atur gulungan-gulungan kertas tersebut pada pinggiran bingkai foto dan tempel menggunakan lem."],
            ["Potong botol pada bagian tengah dengan cutter dan lintinglah kertas sebanyak-banyaknya.", "Kemudian, oles lem pada sisi luar botol bekas dan tempelkan lintingan-lintingan koran pada botol tersebut.",
                "Jika setengah bagian dari botol sudah tertempel lintingan kertas, potonglah kertas yang tertempel mengikuti bentuk panjang botol, lalu tempelkan lagi lintingan kertas hingga habis.", "Jangan lupa oleskan lem di bagian luar botol dan rekatkan tali sebagai pemanis."],
            ["Buatlah 30-50 lintingan kertas.", "Gunting selembar koran menjadi 4 bagian dan lilitkan pada sebatang lidi dari bawah ke atas.", "Rekatkan ujungnya dengan lem, lalu tarik lidi keluar.", "Ambil 8 buah lintingan dan susun membentuk arah mata angin.",
                "Lalu, ambil salah satu lintingan dan putar ke arah kiri dengan teknik anyam sampai habis hingga membentuk dasar keranjang.", "Tegakkan tiang-tiang lintingan, ambil lintingan lain, anyam secara memutar ke atas hingga membentuk sisi-sisi keranjang.", "Rapikan ujung tiang dengan menyisipkannya ke dalam sisi keranjang."],
            ["Pertama, buat lintingan-lintingan koran dengan cara seperti membuat keranjang.", "Bentuk lintingan-lintingan koran tersebut menjadi satu lingkaran besar dan 12 lingkaran kecil.",
                "Pasanglah jarum, mesin jam dinding, dan baterai ke lingkaran besar.", "Rekatkan lingkaran-lingkaran kecil ke sekeliling lingkaran besar menggunakan lem."]
        ]
    )

    # Generate data for plastik
    generate_data(
        LIST_OF_RECYCLEABLE_WASTE[4],
        [
            "Lampu Hias dari Botol Plastik",
            "Celengan Anak dari Botol Plastik",
            "Tempat Charger HP dari Botol Plastik",
            "Tempat Aksesoris dari Botol Plastik",
            "Lampion dari Botol Plastik"
        ],
        [
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/Lampu-hias.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/Celengan-anak.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/Tempat-hp.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/Kerajinan-Aksesoris.jpg",
            "https://pinhome-blog-assets-public.s3.amazonaws.com/2021/11/kerajinan-lampion.jpg"
        ],
        [
            ["Botol bekas seragam", "Cutter atau gunting",
                "Lem khusus plastik", "Sendok plastik", "Cat warna", "Kuas"],
            ["1 pcs botol plastik", "Lem atau selotip", "Gunting atau cutter",
                "Aksesoris mata boneka", "Cat warna", "Kuas"],
            ["Botol minuman 1 liter", "Paku", "Cutter atau gunting"],
            ["Botol plastik", "Cutter atau gunting", "Lem tembak",
                "Besi kecil panjang", "Tatakan dari kayu", "Lilin", "Korek api"],
            ["Botol bekas", "Cutter atau gunting", "Baterai",
                "Kawat", "Set lampu LED", "Tang", "Kardus", "Lem"]
        ],
        [
            ["Potong terlebih dahulu botol menjadi dua bagian, bagian tutup botol sebaiknya dipotong juga.", "Sekarang potong bagian ujung sendok plastik yang cekung.", "Gunakan potongan ujung sendok plastik untuk mencetak potongan dari botol plastik yang sudah dipotong menjadi dua bagian.", "Cetak sebanyak mungkin sampai sekiranya cukup untuk dibuat hiasan lampu.",
                "Mulailah merangkai potongan cetakan tersebut dengan rapi tanpa diberi lem.", "Jika rangkaian sudah benar dan sesuai keinginan, mulailah memberinya lem sedikit demi sedikit dan rangkai satu sama lain sampai membentuk seperti lampu hias.", "Agar tampilan lebih menarik dan berwarna gunakan kuas untuk mewarnai kerajinan dari sampah plastik tersebut menggunakan cat warna yang telah ditetapkan."],
            ["Langkah awal cara membuat kerajinan dari sampah plastik ini adalah memotong bagian botol terlebih dahulu.", "Potong botol menjadi dua bagian.", "Ambil potongan bawah botol, kemudian potong sedikit lagi sekitar 5 cm dari bekas potongan sebelumnya.", "Sekarang, pasangkan kedua potongan botol tersebut dengan cara memasukkan potongan botol bagian bawah ke bagian atas.", "Setelah pemasangan sempurna, rekatkan menggunakan lem atau selotip dan biarkan sampai benar-benar merekat.", "Jika sudah merekat, mulailah membuat lubang di bagian tengah botol sebagai celah untuk memasukkan uang. Pastikan lubang tersebut cukup untuk memasukkan uang kertas ataupun koin.",
                "Kamu juga bisa membuat lubang di bagian ujung atas botol agar lebih menarik.", "Setelah lubang terbentuk, kamu bisa memberinya warna atau mengecat menggunakan warna yang anak atau kamu sukai.", "Selesai mengecat, biarkan cat mengering secara sempurna. Kamu bisa menjemurnya di bawah sinar matahari jika ingin cepat kering.", "Sekarang kamu tinggal memasangkan aksesoris mata boneka di bagian depan atau ujung atas botol agar terlihat lebih menarik menggunakan lem.", "Celengan anak dari limbah botol plastik siap digunakan."],
            ["Langkah awal membuat kerajinan dari sampah plastik adalah dengan memotong terlebih dahulu botol dengan tinggi sesuai ponsel dari bawah. Tingginya bisa dinaikkan sedikit agar tidak terlalu mepet.", "Belah potongan botol bawah tadi sampai setengah saja.", "Hilangkan salah satu sisinya karena nanti digunakan untuk tempat ponsel.",
                "Sekarang, buatlah lubang di bagian atas botol sebagai lubang menempatkan paku.", "Buatlah lubang di bawah lubang sebelumnya dengan ukuran sesuai kabel USB masing-masing.", "Tempatkan paku di dekat tempat pengisian daya.", "Tempatkan botol tadi ke paku yang sudah terpasang dan tempat charger HP sudah jadi.", "Kamu juga bisa memberinya warna atau kreasi lain agar tidak terlalu sederhana."],
            ["Potong terlebih dahulu bagian bawah botol dengan ukuran yang sama sekitar 3-4 botol saja.", "Sekarang panaskan besi kecil panjang yang dijadikan sebagai penyangga menggunakan lilin.", "Jika sudah cukup panas, lubangi bagian tengah potongan bawah botol tersebut seluruhnya.",
                "Sekarang susunlah ke besi kecil panjang tersebut dengan jarak sesuai keinginan.", "Agar lebih kokoh, berilah lem tembak.", "Setelah tersusun sempurna, tempatkan di tatakan dari kayu agar dapat berdiri sempurna."],
            ["Bersihkan terlebih dahulu botol bekas tersebut.", "Potong bagian bawah dan atas botol sampai menyisakan bagian tubuhnya saja.", "Buatlah pola di kardus untuk bagian bawah botol dan potong serapi mungkin.", "Sekarang buatlah lubang di tengah-tengah kardus secara rapi untuk tempat kabel baterai.", "Tempelkan potongan kardus tadi di potongan bawah botol tadi menggunakan lem",
                "Buatlah potongan memanjang di sekitar botol dengan ukuran 1 cm dan beri jarak yang sama satu sama lain sebagai celah untuk cahaya lampu. Jangan sampai memotong bagian botolnya, sisakan jarak 3 cm dari atas ataupun bawah.", "Setelah celah dibuat, masukkan set lampu LED ke dalamnya dan keluarkan kabelnya melalui lubang kardus tadi.", "Pada bagian atas botol plastik, berilah kawat untuk digunakan sebagai gantungan.", "Sekarang, cobalah apakah lampu LED berhasil menyala ketika sudah disambungkan ke baterai."]
        ]
    )

    print(f"Generating json file. Please wait...")
    file_path = os.path.join(pathlib.Path(
        __file__).parent.resolve(), 'recycle_recommendation.json')
    json_converter(file_path)
    print(f"Data has been generated and saved into {file_path}.")
    print()


if __name__ == '__main__':
    LIST_OF_RECYCLEABLE_WASTE = [
        RecycleableWaste("kardus"),
        RecycleableWaste("kaca"),
        RecycleableWaste("besi"),
        RecycleableWaste("kertas"),
        RecycleableWaste("plastik")
    ]
    main()