package org.jnario.suite.doc

import com.google.inject.Inject
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtend.core.xtend.XtendClass
import org.jnario.Specification
import org.jnario.doc.AbstractDocGenerator
import org.jnario.suite.jvmmodel.SuiteClassNameProvider
import org.jnario.suite.suite.Reference
import org.jnario.suite.suite.SpecReference
import org.jnario.suite.suite.Suite
import org.jnario.suite.jvmmodel.SpecResolver
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.jnario.suite.suite.SuiteFile
import org.jnario.doc.HtmlFileBuilder
import org.jnario.doc.HtmlFile

import static extension org.jnario.util.Strings.*
import static extension org.eclipse.xtext.util.Strings.*

class SuiteDocGenerator extends AbstractDocGenerator {
	@Inject extension SuiteClassNameProvider 
	@Inject extension SpecResolver
	@Inject extension HtmlFileBuilder
	
	override doGenerate(Resource input, IFileSystemAccess fsa) {
		input.contents.filter(typeof(SuiteFile)).forEach[
			val htmlFile = createHtmlFile()
			xtendClasses.head.generate(fsa, htmlFile)	
		]
	}
   
	def HtmlFile createHtmlFile(SuiteFile file) {
		val suites = file.xtendClasses.filter(typeof(Suite))
		if(suites.empty) return HtmlFile::EMPTY_FILE
		HtmlFile::newHtmlFile[
			name = suites.head.className
			title = suites.head.describe.convertFromJavaString(true)
			content = suites.generateContent
			rootFolder = suites.head.root
		]
	}

	override HtmlFile createHtmlFile(XtendClass file) {
		val suite = file as Suite
		HtmlFile::newHtmlFile[
			name = suite.className 
			title = suite.describe.convertFromJavaString(true)
			content = suite.generateContent
			rootFolder = suite.root
		]
	}
	
	def generateContent(Iterable<Suite> suites)'''
		�FOR suite : suites�
		�IF !(suite == suites.head)�
		�suite.name.firstLine.markdown2Html�
		�ENDIF�
		�suite.generateContent�
		�ENDFOR�
	'''

	def generateContent(Suite suite)'''
		<span�suite.name.replace("#","").id�>�suite.name.trimFirstLine.markdown2Html�</span>
		�IF !suite.elements.empty�
		<ul>
		�FOR spec : suite.elements�
			�generate(spec)�
		�ENDFOR�
		</ul>
		�ENDIF�
	'''

	def generate(Reference ref)'''
	�FOR spec : ref.resolveSpecs�
		<li><a href="�ref.linkTo(spec)�">�spec.describe�</a>�ref.text�</li>
	�ENDFOR�
	''' 
	
	def linkTo(EObject context, Specification spec){
		context.root + spec.packageName.replace(".", "/") + "/" + spec.className.htmlFileName
	}
	
	def text(Reference ref){
		switch ref{
			SpecReference case ref.text != null : return ': ' + ref.text
		}
		return ""
	}
	
}