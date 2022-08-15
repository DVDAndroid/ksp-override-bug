import com.google.auto.service.AutoService
import com.google.devtools.ksp.closestClassDeclaration
import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.validate

@AutoService(SymbolProcessorProvider::class)
class AnnotationProcessorProvider : SymbolProcessorProvider {
  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor = AnnotationProcessor(
    environment.logger
  )
}

class AnnotationProcessor(private val logger: KSPLogger) : SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {
    val a = resolver.getSymbolsWithAnnotation("ProcessThis")

    a.filter { it is KSClassDeclaration && it.validate() }
      .forEach { it.accept(MyVisitor(logger), Unit) }

    return emptyList()
  }
}

class MyVisitor(private val logger: KSPLogger) : KSVisitorVoid() {

  override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
    super.visitClassDeclaration(classDeclaration, data)

    classDeclaration.getAllProperties().forEach { it.accept(this, Unit) }
  }

  override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
    super.visitPropertyDeclaration(property, data)

    logger.warn(property.findOverridee()?.closestClassDeclaration()?.simpleName?.asString().toString())
  }

}
