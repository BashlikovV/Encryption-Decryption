package encryptdecrypt

import java.io.File
import java.lang.StringBuilder

const val ENCRYPT = "enc"
const val DECRYPT = "dec"
const val IN = "-in"
const val OUT = "-out"
const val ALGORITHM = "-alg"
const val SHIFT = "shift"
const val UNICODE = "unicode"

fun encrypt(str: String, n: Int): String {
    val tmpResult = ArrayList<Char>(str.length)

    for (x in str.indices) {
        tmpResult.add(str[x] + n)
    }

    return tmpResult.toString()
}

fun shiftEncrypt(str: String, n: Int): String {
    val tmpResult = ArrayList<Char>(str.length)

    for (x in str.indices) {
        if (str[x].isLetter()) {
            val ch = str[x] + n
            if (ch > 'z' && ch < 'z' + n || ch > 'Z' && ch < 'Z' + n) {
                tmpResult.add(str[x] - 26 + n)
            } else {
                tmpResult.add(str[x] + n)
            }
        } else {
            tmpResult.add(str[x])
        }

    }

    return tmpResult.toString()
}

fun decrypt(str: String, n: Int): String {
    val tmpResult = ArrayList<Char>(str.length)

    for (x in str.indices) {
        tmpResult.add(str[x] - n)
    }

    return tmpResult.toString()
}

fun shiftDecrypt(str: String, n: Int): String {
    val tmpResult = ArrayList<Char>(str.length)

    for (x in str.indices) {
        if (str[x].isLetter()) {
            val ch = str[x] - n
            if (ch < 'a' && ch > 'a' - n || ch < 'A' && ch > 'A' - n) {
                tmpResult.add(str[x] + 26 - n)
            } else {
                tmpResult.add(str[x] - n)
            }
        } else {
            tmpResult.add(str[x])
        }
    }

    return tmpResult.toString()
}

fun main(args: Array<String>) {
    var operation = "enc"
    var str = ""
    var shift = 0
    var fileName = ""
    var secondFileName = ""
    var algType = "shift"
    val file: File
    val secondFile: File

    for (i in args.indices) {
        if (args[i] == "-mode") {
            operation = args[i + 1]
        }
        if (args[i] == "-key") {
            shift = args[i + 1].toInt()
        }
        if (args[i] == "-data") {
            str = args[i + 1]
        }
        if (args[i] == IN) {
            fileName = args[i + 1]
        }
        if (args[i] == OUT) {
            secondFileName = args[i + 1]
        }
        if (args[i] == ALGORITHM) {
            algType = args[i + 1]
        }
    }

    if (fileName.isNotEmpty()) {
        file = File(fileName)
        str = file.readText()
    }


    var result = ""

    if (algType == UNICODE) {
        result = when (operation) {
            ENCRYPT -> encrypt(str, shift)
            DECRYPT -> decrypt(str, shift)
            else -> "Invalid operation"
        }
    } else if (algType == SHIFT) {
        result = when (operation) {
            ENCRYPT -> shiftEncrypt(str, shift)
            DECRYPT -> shiftDecrypt(str, shift)
            else -> "Invalid operation"
        }
    }

    val stringBuilder = StringBuilder()
    for (i in 1 until result.length step 3) {
        stringBuilder.append(result[i])
    }

    if (secondFileName.isNotEmpty()) {
        secondFile = File(secondFileName)
        secondFile.writeText(stringBuilder.toString())
    } else {
        print(stringBuilder)
    }
}

