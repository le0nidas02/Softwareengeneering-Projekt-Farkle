object main extends App {
  println("--- Farkle Dice Roll Simulation ---")
  
  // Simulate rolling 6 dice
  val rolledDice = List.fill(6)(Dice.roll())
  
  println(s"You rolled ${rolledDice.length} dice:")
  
  // Create a field and display it
  val field = Field(rolledDice)
  println(field.toString)
  
  println("\n--- Simulation finished ---")
}
