// OK if implemented as Identifiable<String>
interface Identifiable<T> {
  val id: T
}

// NOT OK
interface StringIdentifiable : Identifiable<String> {
  override val id: String
}

// OK
interface DirectStringIdentifiable {
  val id: String
}

@Target(AnnotationTarget.CLASS)
annotation class ProcessThis

@ProcessThis
data class MyClass1(
  override val id: String
) : Identifiable<String>

@ProcessThis
data class MyClass2(
  override val id: String
) : DirectStringIdentifiable

@ProcessThis
data class MyClass3(
  override val id: String
) : StringIdentifiable