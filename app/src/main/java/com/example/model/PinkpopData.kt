package com.example.model

data class Performance(
    val artist: String,
    val startTime: String, // e.g., "18:00"
    val endTime: String,   // e.g., "02:00"
    val url: String?
) {
    // Unique identifier for setting alarms and comparisons
    val id: String get() = "${artist}_${startTime}"
}

data class StageInfo(
    val name: String,
    val performances: List<Performance>
)

data class FestivalDay(
    val name: String, // "Donderdag", "Vrijdag", etc.
    val label: String, // "donderdag 18 juni", etc.
    val dateString: String, // "18-06-2026"
    val stages: List<StageInfo>
)

object PinkpopData {
    val days: List<FestivalDay> = listOf(
        FestivalDay(
            name = "Vrijdag",
            label = "vrijdag 19 juni",
            dateString = "19-06-2026",
            stages = listOf(
                StageInfo(
                    name = "SOUTH STAGE",
                    performances = listOf(
                        Performance("TWENTY ONE PILOTS", "22:25", "00:00", "https://www.pinkpop.nl/line-up/twenty-one-pilots/"),
                        Performance("TEDDY SWIMS", "20:15", "21:15", "https://www.pinkpop.nl/line-up/teddy-swims/"),
                        Performance("ROXY DEKKER", "18:15", "19:05", "https://www.pinkpop.nl/line-up/roxy-dekker/"),
                        Performance("THE VACCINES", "16:10", "17:10", "https://www.pinkpop.nl/line-up/the-vaccines/"),
                        Performance("NATASHA BEDINGFIELD", "14:00", "15:00", "https://www.pinkpop.nl/line-up/natasha-bedingfield/")
                    )
                ),
                StageInfo(
                    name = "NORTH STAGE",
                    performances = listOf(
                        Performance("ZARA LARSSON", "21:20", "22:20", "https://www.pinkpop.nl/line-up/zara-larsson/"),
                        Performance("ELECTRIC CALLBOY", "19:10", "20:10", "https://www.pinkpop.nl/line-up/electric-callboy/"),
                        Performance("KINGFISHR", "17:15", "18:15", "https://www.pinkpop.nl/line-up/kingfishr/"),
                        Performance("THE PRETTY RECKLESS", "15:05", "16:05", "https://www.pinkpop.nl/line-up/the-pretty-reckless/")
                    )
                ),
                StageInfo(
                    name = "TENT STAGE",
                    performances = listOf(
                        Performance("THE PLOT IN YOU", "20:35", "21:35", "https://www.pinkpop.nl/line-up/the-plot-in-you/"),
                        Performance("THE BEACHES", "18:50", "19:50", "https://www.pinkpop.nl/line-up/the-beaches/"),
                        Performance("SKI AGGU", "17:20", "18:05", "https://www.pinkpop.nl/line-up/ski-aggu/"),
                        Performance("BALU BRIGADA", "15:30", "16:30", "https://www.pinkpop.nl/line-up/balu-brigada/"),
                        Performance("SLEEP THEORY", "13:45", "14:45", "https://www.pinkpop.nl/line-up/sleep-theory/"),
                        Performance("DE HERFSHANE BAND", "12:15", "13:00", "https://www.pinkpop.nl/line-up/de-herfshane-band/")
                    )
                ),
                StageInfo(
                    name = "STAGE 4",
                    performances = listOf(
                        Performance("VOILÀ", "21:35", "22:20", "https://www.pinkpop.nl/line-up/voila/"),
                        Performance("YONG YELLO", "19:50", "20:35", "https://www.pinkpop.nl/line-up/yong-yello/"),
                        Performance("ECCA VANDAL", "18:05", "18:50", "https://www.pinkpop.nl/line-up/ecca-vandal/"),
                        Performance("BUFFALO TRAFFIC JAM", "16:30", "17:15", "https://www.pinkpop.nl/line-up/buffalo-traffic-jam/"),
                        Performance("ISABEL VAN GELDER", "14:45", "15:30", "https://www.pinkpop.nl/line-up/isabel-van-gelder/"),
                        Performance("LINKA MOJA", "13:00", "13:45", "https://www.pinkpop.nl/line-up/linka-moja/")
                    )
                ),
                StageInfo(
                    name = "SUN STAGE X DESPERADOS",
                    performances = listOf(
                        Performance("CHUCKIE", "22:30", "23:50", "https://www.pinkpop.nl/randprogramma/chuckie-2/"),
                        Performance("GET LUCKY", "21:00", "22:30", null),
                        Performance("YUNG FELIX", "20:00", "21:00", "https://www.pinkpop.nl/randprogramma/yung-felix-2/"),
                        Performance("DEF", "19:30", "20:00", "https://www.pinkpop.nl/randprogramma/def-2/"),
                        Performance("EL DON", "18:30", "19:30", null),
                        Performance("NEMS", "17:30", "18:30", "https://www.pinkpop.nl/randprogramma/nems-2/"),
                        Performance("EL DON", "16:00", "17:30", null),
                        Performance("ADF SAMSKI", "15:30", "16:00", "https://www.pinkpop.nl/randprogramma/adf-samski-2/")
                    )
                ),
                StageInfo(
                    name = "DE STILLE JAN",
                    performances = listOf(
                        Performance("SILENT DISCO VRIJDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/silent-disco-vrijdag-2/")
                    )
                ),
                StageInfo(
                    name = "SUN SCREAM KARAOKEBAR",
                    performances = listOf(
                        Performance("SUN SCREAM KARAOKEBAR VRIJDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/sun-scream-karaokebar-vrijdag/")
                    )
                ),
                StageInfo(
                    name = "FEESTTENT (FESTIVAL CAMPING)",
                    performances = listOf(
                        Performance("FEESTTENT VRIJDAG", "00:00", "03:30", "https://www.pinkpop.nl/randprogramma/feesttent-vrijdag/")
                    )
                ),
                StageInfo(
                    name = "FEESTPLEIN (GROEPS CAMPING)",
                    performances = listOf(
                        Performance("FEESTPLEIN VRIJDAG", "21:00", "01:00", "https://www.pinkpop.nl/randprogramma/feestplein-vrijdag/")
                    )
                ),
                StageInfo(
                    name = "WIJNRESTAURANT OP HET MEGALAND",
                    performances = listOf(
                        Performance("WIJNRESTAURANT OP HET MEGALAND", "11:00", "20:30", "https://www.pinkpop.nl/wijnrestaurant-op-het-megaland/")
                    )
                )
            )
        ),
        FestivalDay(
            name = "Zaterdag",
            label = "zaterdag 20 juni",
            dateString = "20-06-2026",
            stages = listOf(
                StageInfo(
                    name = "SOUTH STAGE",
                    performances = listOf(
                        Performance("THE CURE", "22:00", "00:00", "https://www.pinkpop.nl/line-up/the-cure/"),
                        Performance("EDITORS", "19:50", "20:50", "https://www.pinkpop.nl/line-up/editors/"),
                        Performance("FRANZ FERDINAND", "17:40", "18:40", "https://www.pinkpop.nl/line-up/franz-ferdinand/"),
                        Performance("SUZAN & FREEK", "15:30", "16:30", "https://www.pinkpop.nl/line-up/suzan-freek/"),
                        Performance("TRIGGERFINGER", "13:20", "14:20", "https://www.pinkpop.nl/line-up/triggerfinger/")
                    )
                ),
                StageInfo(
                    name = "NORTH STAGE",
                    performances = listOf(
                        Performance("HALSEY", "20:55", "21:55", "https://www.pinkpop.nl/line-up/halsey/"),
                        Performance("IDLES", "18:45", "19:45", "https://www.pinkpop.nl/line-up/idles/"),
                        Performance("SOFI TUKKER", "16:35", "17:35", "https://www.pinkpop.nl/line-up/sofi-tukker/"),
                        Performance("LAUREN SPENCER SMITH", "14:25", "15:25", "https://www.pinkpop.nl/line-up/lauren-spencer-smith/"),
                        Performance("GIANT ROOKS", "12:15", "13:15", "https://www.pinkpop.nl/line-up/giant-rooks/")
                    )
                ),
                StageInfo(
                    name = "TENT STAGE",
                    performances = listOf(
                        Performance("SOULWAX", "20:30", "21:30", "https://www.pinkpop.nl/line-up/soulwax/"),
                        Performance("ALESSI ROSE", "18:45", "19:45", "https://www.pinkpop.nl/line-up/alessi-rose/"),
                        Performance("THE HAUNTED YOUTH", "17:00", "18:00", "https://www.pinkpop.nl/line-up/the-haunted-youth/"),
                        Performance("JEHNNY BETH", "15:15", "16:15", "https://www.pinkpop.nl/line-up/jehnny-beth/"),
                        Performance("HAEVN", "13:30", "14:30", "https://www.pinkpop.nl/line-up/haevn/"),
                        Performance("HANG YOUTH", "12:00", "12:45", "https://www.pinkpop.nl/line-up/hang-youth/")
                    )
                ),
                StageInfo(
                    name = "STAGE 4",
                    performances = listOf(
                        Performance("THIJS BOONTJES", "19:45", "20:30", "https://www.pinkpop.nl/line-up/thijs-boontjes/"),
                        Performance("CHEZILE", "18:00", "18:45", "https://www.pinkpop.nl/line-up/chezile/"),
                        Performance("LEAP", "16:15", "17:00", "https://www.pinkpop.nl/line-up/leap/"),
                        Performance("WODAN BOYS", "14:30", "15:15", "https://www.pinkpop.nl/line-up/wodan-boys/"),
                        Performance("JERUB", "12:45", "13:30", "https://www.pinkpop.nl/line-up/jerub/")
                    )
                ),
                StageInfo(
                    name = "SUN STAGE X DESPERADOS",
                    performances = listOf(
                        Performance("LISA KORVER", "22:30", "23:50", "https://www.pinkpop.nl/randprogramma/lisa-korver-2/"),
                        Performance("JULIËN MOUREAU", "20:30", "22:30", null),
                        Performance("FIESTA MACUMBA SOUNDSYSTEM", "19:30", "20:30", "https://www.pinkpop.nl/randprogramma/fiesta-macumba-soundsystem-2/"),
                        Performance("SDNX", "18:30", "19:30", null),
                        Performance("BOKOESAM", "18:00", "18:30", "https://www.pinkpop.nl/randprogramma/bokoesam-2/"),
                        Performance("SDNX", "16:30", "18:00", null),
                        Performance("JESSE HOEFNAGELS", "15:45", "16:15", "https://www.pinkpop.nl/randprogramma/jesse-hoefnagels-2/"),
                        Performance("AKRA", "14:00", "15:30", null),
                        Performance("FLAIRE", "13:30", "14:00", null),
                        Performance("AKRA", "12:15", "13:30", null),
                        Performance("MORNING PILATES BY UMA", "11:30", "12:00", null)
                    )
                ),
                StageInfo(
                    name = "DE STILLE JAN",
                    performances = listOf(
                        Performance("SILENT DISCO ZATERDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/silent-disco-zaterdag/")
                    )
                ),
                StageInfo(
                    name = "SUN SCREAM KARAOKEBAR",
                    performances = listOf(
                        Performance("SUN SCREAM KARAOKEBAR ZATERDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/sun-scream-karaokebar-zaterdag/")
                    )
                ),
                StageInfo(
                    name = "FEESTTENT (FESTIVAL CAMPING)",
                    performances = listOf(
                        Performance("FEESTTENT ZATERDAG", "00:00", "03:30", "https://www.pinkpop.nl/randprogramma/feesttent-zaterdag/")
                    )
                ),
                StageInfo(
                    name = "FEESTPLEIN (GROEPS CAMPING)",
                    performances = listOf(
                        Performance("FEESTPLEIN ZATERDAG", "21:00", "01:00", "https://www.pinkpop.nl/randprogramma/feestplein-zaterdag/")
                    )
                ),
                StageInfo(
                    name = "WIJNRESTAURANT OP HET MEGALAND",
                    performances = listOf(
                        Performance("WIJNRESTAURANT OP HET MEGALAND", "11:00", "20:30", "https://www.pinkpop.nl/wijnrestaurant-op-het-megaland/")
                    )
                ),
                StageInfo(
                    name = "BACKSTAGE SUN SQUARE",
                    performances = listOf(
                        Performance("NEDERLAND VS. ZWEDEN", "19:00", "20:45", "https://www.pinkpop.nl/randprogramma/nederland-vs-zweden/")
                    )
                )
            )
        ),
        FestivalDay(
            name = "Zondag",
            label = "zondag 21 juni",
            dateString = "21-06-2026",
            stages = listOf(
                StageInfo(
                    name = "SOUTH STAGE",
                    performances = listOf(
                        Performance("FOO FIGHTERS", "22:00", "00:00", "https://www.pinkpop.nl/line-up/foo-fighters/"),
                        Performance("YUNGBLUD", "19:35", "20:35", "https://www.pinkpop.nl/line-up/yungblud/"),
                        Performance("ROYEL OTIS", "17:25", "18:25", "https://www.pinkpop.nl/line-up/royel-otis/"),
                        Performance("WHITE LIES", "15:15", "16:15", "https://www.pinkpop.nl/line-up/white-lies/"),
                        Performance("BENTE", "13:05", "14:05", "https://www.pinkpop.nl/line-up/bente/")
                    )
                ),
                StageInfo(
                    name = "NORTH STAGE",
                    performances = listOf(
                        Performance("DI-RECT", "20:40", "21:55", "https://www.pinkpop.nl/line-up/di-rect/"),
                        Performance("WET LEG", "18:30", "19:30", "https://www.pinkpop.nl/line-up/wet-leg/"),
                        Performance("JADE", "16:20", "17:20", "https://www.pinkpop.nl/line-up/jade/"),
                        Performance("HOOGMIS VAN HET ZUIDEN", "14:10", "15:10", "https://www.pinkpop.nl/line-up/hoogmis-van-het-zuiden/"),
                        Performance("MY BABY", "12:00", "13:00", "https://www.pinkpop.nl/line-up/my-baby/")
                    )
                ),
                StageInfo(
                    name = "TENT STAGE",
                    performances = listOf(
                        Performance("FAT DOG", "20:30", "21:30", "https://www.pinkpop.nl/line-up/fat-dog/"),
                        Performance("MAX MCNOWN", "18:45", "19:45", "https://www.pinkpop.nl/line-up/max-mcnown/"),
                        Performance("TOM MORELLO", "17:00", "18:00", "https://www.pinkpop.nl/line-up/tom-morello/"),
                        Performance("GOOD NEIGHBOURS", "15:15", "16:15", "https://www.pinkpop.nl/line-up/good-neighbours/"),
                        Performance("DOGSTAR", "13:30", "14:30", "https://www.pinkpop.nl/line-up/dogstar/"),
                        Performance("DAÐI FREYR", "12:00", "12:45", "https://www.pinkpop.nl/line-up/dadi-freyr/")
                    )
                ),
                StageInfo(
                    name = "STAGE 4",
                    performances = listOf(
                        Performance("SOFIA CAMARA", "21:30", "22:15", "https://www.pinkpop.nl/line-up/sofia-camara/"),
                        Performance("BEN ELLIS", "19:45", "20:30", "https://www.pinkpop.nl/line-up/ben-ellis/"),
                        Performance("NAFT", "18:00", "18:45", "https://www.pinkpop.nl/line-up/naft/"),
                        Performance("DIE SPITZ", "16:15", "17:00", "https://www.pinkpop.nl/line-up/die-spitz/"),
                        Performance("HOTWAX", "14:30", "15:15", "https://www.pinkpop.nl/line-up/hotwax/"),
                        Performance("LEILA LAMB", "12:45", "13:30", "https://www.pinkpop.nl/line-up/leila-lamb/")
                    )
                ),
                StageInfo(
                    name = "SUN STAGE X DESPERADOS",
                    performances = listOf(
                        Performance("NOTHING BUT FUNK + GROOVE SAFARI", "22:30", "23:50", null),
                        Performance("BRENT NEW", "21:15", "22:30", null),
                        Performance("MR. POLSKA", "20:45", "21:15", "https://www.pinkpop.nl/randprogramma/mr-polska-2/"),
                        Performance("MILANY", "19:30", "20:45", null),
                        Performance("B???? <3 SIM FANE <3 BOSCHA NOVA", "18:30", "19:30", "https://www.pinkpop.nl/randprogramma/b/"),
                        Performance("FABY", "17:30", "18:30", "https://www.pinkpop.nl/randprogramma/faby-2/"),
                        Performance("NOCRONI NIGHT SHIFTER", "16:15", "17:30", null),
                        Performance("IDA", "15:45", "16:15", "https://www.pinkpop.nl/randprogramma/ida-2/"),
                        Performance("MILANY", "14:45", "15:45", null),
                        Performance("JET VAN DER STEEN", "14:15", "14:45", "https://www.pinkpop.nl/randprogramma/jet-van-der-steen-2/"),
                        Performance("BADDIE FM COFFEE RAVE", "12:15", "14:15", null),
                        Performance("SUNDAY RESET", "11:30", "12:00", null)
                    )
                ),
                StageInfo(
                    name = "DE STILLE JAN",
                    performances = listOf(
                        Performance("SILENT DISCO ZONDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/silent-disco-zondag/")
                    )
                ),
                StageInfo(
                    name = "SUN SCREAM KARAOKEBAR",
                    performances = listOf(
                        Performance("SUN SCREAM KARAOKEBAR ZONDAG", "13:00", "23:50", "https://www.pinkpop.nl/randprogramma/sun-scream-karaokebar-zondag/")
                    )
                ),
                StageInfo(
                    name = "FEESTTENT (FESTIVAL CAMPING)",
                    performances = listOf(
                        Performance("FEESTTENT ZONDAG", "00:00", "02:00", "https://www.pinkpop.nl/randprogramma/feesttent-zondag/")
                    )
                ),
                StageInfo(
                    name = "FEESTPLEIN (GROEPS CAMPING)",
                    performances = listOf(
                        Performance("FEESTPLEIN ZONDAG", "21:00", "01:00", "https://www.pinkpop.nl/randprogramma/feestplein-zondag/")
                    )
                ),
                StageInfo(
                    name = "WIJNRESTAURANT OP HET MEGALAND",
                    performances = listOf(
                        Performance("WIJNRESTAURANT OP HET MEGALAND", "11:00", "20:30", "https://www.pinkpop.nl/wijnrestaurant-op-het-megaland/")
                    )
                )
            )
        )
    )

    // Helper to get all performances across all days and stages
    val allPerformances: List<Performance> = days.flatMap { day ->
        day.stages.flatMap { stage -> stage.performances }
    }

    // Helper to get a day by name
    fun getDayByName(name: String): FestivalDay? {
        return days.firstOrNull { it.name.lowercase() == name.lowercase() }
    }
}
