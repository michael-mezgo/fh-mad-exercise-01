/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package at.ac.fhcampuswien

import kotlin.random.Random

class App {
    // Game logic for a number guessing game
    fun playNumberGame(digitsToGuess: Int = 4) {

        do {
            var resumeGame = false
            val numberToGuess = generateRandomNonRepeatingNumber(digitsToGuess)
            var guessedRight = false

            while (!guessedRight)
            {
                print("Your guess: ")
                val guess = readln()

                val result = checkUserInputAgainstGeneratedNumber(numberToGuess, guess.toInt())
                println(result)

                if(result.n == digitsToGuess)
                {
                    guessedRight = true
                }
            }

            println("Continue game? [y/N]")
            if(readln().lowercase() == "y")
                resumeGame = true
        } while (resumeGame)
    }

    /**
     * Generates a non-repeating number of a specified length between 1-9.
     *
     * Note: The function is designed to generate a number where each digit is unique and does not repeat.
     * It is important to ensure that the length parameter does not exceed the maximum possible length
     * for non-repeating digits (which is 9 excluding 0 for base-10 numbers).
     *
     * @param length The length of the non-repeating number to be generated.
     *               This dictates how many digits the generated number will have.
     * @return An integer of generated non-repeating number.
     *         The generated number will have a number of digits equal to the specified length and will
     *         contain unique, non-repeating digits.
     * @throws IllegalArgumentException if the length is more than 9 or less than 1.
     */
    val generateRandomNonRepeatingNumber: (Int) -> Int = { length ->
        if(length < 1 || length > 9)
            throw IllegalArgumentException("Number length too long")

        var randomNumber = ""

        while (randomNumber.length < length) {
            val randomDigit = Random.nextInt(from = 1, until = 10) //https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.random/-random/next-int.html

            if(randomDigit.toString() !in randomNumber)
                randomNumber += randomDigit
        }

        randomNumber.toInt()
    }

    /**
     * Compares the user's input integer against a generated number for a guessing game.
     * This function evaluates how many digits the user guessed correctly and how many of those
     * are in the correct position. The game generates number with non-repeating digits.
     *
     * Note: The input and the generated number must both be numbers.
     * If the inputs do not meet these criteria, an IllegalArgumentException is thrown.
     *
     * @param input The user's input integer. It should be a number with non-repeating digits.
     * @param generatedNumber The generated number with non-repeating digits to compare against.
     * @return [CompareResult] with two properties:
     *         1. `m`: The number of digits guessed correctly (regardless of their position).
     *         2. `n`: The number of digits guessed correctly and in the correct position.
     *         The result is formatted as "Output: m:n", where "m" and "n" represent the above values, respectively.
     * @throws IllegalArgumentException if the inputs do not have the same number of digits.
     */
    val checkUserInputAgainstGeneratedNumber: (Int, Int) -> CompareResult = { input, generatedNumber ->
        val userGuess = input.toString()
        val numberToGuess = generatedNumber.toString()

        var rightDigits = 0
        var rightPositions = 0
        val foundDigits = mutableListOf<Char>()


        if(userGuess.length != numberToGuess.length)
            throw IllegalArgumentException("Input to short or long!")

        for(i in numberToGuess.indices) {
            if(userGuess[i] == numberToGuess[i])
                rightPositions++

            if(userGuess[i] in numberToGuess && !foundDigits.contains(userGuess[i])) {
                rightDigits++
                foundDigits.add(userGuess[i])
            }
        }

        CompareResult(rightDigits, rightPositions)   // return value is a placeholder
    }
}

fun main() {
    App().playNumberGame()
    //App().playNumberGame(digitsToGuess = 5) // OR: App().playNumberGame(5)
}
