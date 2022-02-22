package com.github.heyvito.goenv.services

val commentLineRegexp = Regex("^\\s*#")
val privateLineRegexp = Regex("\\s*private (.+)")

class EnvParser(file: ByteArray) {
    var privates: List<String>

    init {
        val fileStr = String(file).split("\n")
        val privates = arrayListOf<String>()
        for (l in fileStr) {
            if (commentLineRegexp matches l) {
                continue
            }

            if (privateLineRegexp matches l) {
                val components = privateLineRegexp.matchEntire(l)!!.groupValues
                val url = components.elementAt(1)
                privates.add(url)
            }
        }

        this.privates = privates
    }
}
