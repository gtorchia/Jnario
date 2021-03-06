/*******************************************************************************
 * Copyright (c) 2012 BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
/*
 * generated by Xtext
 */
package org.jnario.feature;

import org.eclipse.xtend.core.compiler.XtendOutputConfigurationProvider;
import org.eclipse.xtend.core.formatting.XtendFormatter;
import org.eclipse.xtend.core.imports.XtendImportsConfiguration;
import org.eclipse.xtend.core.jvmmodel.IXtendJvmAssociations;
import org.eclipse.xtend.core.resource.XtendResourceDescriptionManager;
import org.eclipse.xtend.core.typesystem.DispatchAndExtensionAwareReentrantTypeResolver;
import org.eclipse.xtend.core.typesystem.TypeDeclarationAwareBatchTypeResolver;
import org.eclipse.xtend.core.validation.XtendConfigurableIssueCodes;
import org.eclipse.xtend.core.validation.XtendEarlyExitValidator;
import org.eclipse.xtend.core.xtend.XtendFactory;
import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.generator.IFilePostProcessor;
import org.eclipse.xtext.generator.IGenerator;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.generator.OutputConfigurationProvider;
import org.eclipse.xtext.linking.ILinker;
import org.eclipse.xtext.linking.ILinkingDiagnosticMessageProvider;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.IParser;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.ILocationInFileProvider;
import org.eclipse.xtext.resource.IResourceDescription.Manager;
import org.eclipse.xtext.scoping.IScopeProvider;
import org.eclipse.xtext.scoping.impl.AbstractDeclarativeScopeProvider;
import org.eclipse.xtext.validation.CompositeEValidator;
import org.eclipse.xtext.validation.ConfigurableIssueCodesProvider;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.NamesAreUniqueValidationHelper;
import org.eclipse.xtext.xbase.XbaseFactory;
import org.eclipse.xtext.xbase.compiler.JvmModelGenerator;
import org.eclipse.xtext.xbase.compiler.XbaseCompiler;
import org.eclipse.xtext.xbase.compiler.output.TraceAwarePostProcessor;
import org.eclipse.xtext.xbase.featurecalls.IdentifiableSimpleNameProvider;
import org.eclipse.xtext.xbase.formatting.IBasicFormatter;
import org.eclipse.xtext.xbase.imports.IImportsConfiguration;
import org.eclipse.xtext.xbase.jvmmodel.IJvmModelInferrer;
import org.eclipse.xtext.xbase.jvmmodel.JvmModelAssociator;
import org.eclipse.xtext.xbase.jvmmodel.JvmTypesBuilder;
import org.eclipse.xtext.xbase.resource.JvmDeclaredTypeSignatureHashProvider.SignatureHashBuilder;
import org.eclipse.xtext.xbase.scoping.batch.ImplicitlyImportedTypes;
import org.eclipse.xtext.xbase.typesystem.computation.ITypeComputer;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultBatchTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.DefaultReentrantTypeResolver;
import org.eclipse.xtext.xbase.typesystem.internal.ScopeProviderAccess;
import org.eclipse.xtext.xbase.util.XExpressionHelper;
import org.eclipse.xtext.xbase.validation.EarlyExitValidator;
import org.jnario.compiler.JnarioBatchCompiler;
import org.jnario.compiler.JnarioExpressionHelper;
import org.jnario.doc.AbstractDocGenerator;
import org.jnario.doc.DocOutputConfigurationProvider;
import org.jnario.feature.compiler.FeatureBatchCompiler;
import org.jnario.feature.conversion.FeatureValueConverterService;
import org.jnario.feature.doc.FeatureDocGenerator;
import org.jnario.feature.generator.FeatureCompiler;
import org.jnario.feature.generator.FeatureJvmModelGenerator;
import org.jnario.feature.jvmmodel.FeatureExecutableProvider;
import org.jnario.feature.jvmmodel.FeatureJvmModelInferrer;
import org.jnario.feature.linking.FeatureLazyLinker;
import org.jnario.feature.linking.FeatureLinkingDiagnosticMessageProvider;
import org.jnario.feature.linking.FeatureLinkingService;
import org.jnario.feature.naming.FeatureClassNameProvider;
import org.jnario.feature.naming.FeatureIdentifiableSimpleNameProvider;
import org.jnario.feature.naming.FeatureQualifiedNameProvider;
import org.jnario.feature.parser.CustomFeatureParser;
import org.jnario.feature.resource.FeatureLocationInFileProvider;
import org.jnario.feature.scoping.FeatureImportedNamespaceScopeProvider;
import org.jnario.feature.scoping.FeatureScopeProvider;
import org.jnario.feature.scoping.FeatureScopeProviderAccess;
import org.jnario.feature.validation.FeatureNamesAreUniqueValidationHelper;
import org.jnario.jvmmodel.ExecutableProvider;
import org.jnario.jvmmodel.ExtendedJvmTypesBuilder;
import org.jnario.jvmmodel.JnarioNameProvider;
import org.jnario.jvmmodel.JnarioSignatureHashBuilder;
import org.jnario.report.Executable2ResultMapping;
import org.jnario.report.HashBasedSpec2ResultMapping;
import org.jnario.scoping.JnarioImplicitlyImportedTypes;
import org.jnario.scoping.JnarioImportedNamespaceScopeProvider;
import org.jnario.scoping.JnarioResourceDescriptionStrategy;
import org.jnario.typing.JnarioTypeComputer;

import com.google.inject.Binder;
import com.google.inject.name.Names;

/**
 * @author Birgit Engelmann - Initial contribution and API
 */
public class FeatureRuntimeModule extends org.jnario.feature.AbstractFeatureRuntimeModule {
	
	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(AbstractDocGenerator.class).to(FeatureDocGenerator.class);
		binder.bind(SignatureHashBuilder.class).to(JnarioSignatureHashBuilder.class);
		binder.bind(JnarioNameProvider.class).to(FeatureClassNameProvider.class);
		binder.bind(ExecutableProvider.class).to(FeatureExecutableProvider.class);
		binder.bind(Executable2ResultMapping.class).to(HashBasedSpec2ResultMapping.class);
		binder.bind(ImplicitlyImportedTypes.class).to(JnarioImplicitlyImportedTypes.class);
		binder.bind(ScopeProviderAccess.class).to(FeatureScopeProviderAccess.class);
		binder.bind(NamesAreUniqueValidationHelper.class).to(FeatureNamesAreUniqueValidationHelper.class);
		binder.bind(boolean.class).annotatedWith(
				Names.named(CompositeEValidator.USE_EOBJECT_VALIDATOR)).toInstance(false);
	}
	
	public Class<? extends ILinkingDiagnosticMessageProvider> bindILinkingDiagnosticMessageProvider() {
		return FeatureLinkingDiagnosticMessageProvider.class;
	}
	
	public Class<? extends JvmTypesBuilder> bindJvmTypesBuilder(){
		return ExtendedJvmTypesBuilder.class;
	}
	
	public Class<? extends JvmModelGenerator> bindJvmModelGenerator(){
		return FeatureJvmModelGenerator.class;
	}
	
	@Override
	public java.lang.Class<? extends IScopeProvider> bindIScopeProvider() {
		return FeatureScopeProvider.class;
	}
	
	@Override
	public void configureIScopeProviderDelegate(Binder binder) {
		binder.bind(IScopeProvider.class).annotatedWith(Names.named(AbstractDeclarativeScopeProvider.NAMED_DELEGATE))
		.to(FeatureImportedNamespaceScopeProvider.class);
	}

	@Override
	public Class<? extends IdentifiableSimpleNameProvider> bindIdentifiableSimpleNameProvider() {
		return FeatureIdentifiableSimpleNameProvider.class;
	}

	public Class<? extends IJvmModelInferrer> bindIJvmModelInferrer() {
		return FeatureJvmModelInferrer.class;
	}

	public Class <? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return JnarioResourceDescriptionStrategy.class;
	}

	public Class<? extends JvmModelAssociator> bindJvmModelAssociator() {
		return IXtendJvmAssociations.Impl.class;
	}

	public Class<? extends EarlyExitValidator> bindEarlyExitValidator() {
		return XtendEarlyExitValidator.class;
	}
	
	public Class<? extends XbaseCompiler> bindXbaseCompiler() {
		return FeatureCompiler.class; 
	}	

	public Class<? extends OutputConfigurationProvider> bindOutputConfigurationProvider() {
		return DocOutputConfigurationProvider.class;
	}
	
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider(){
		return FeatureQualifiedNameProvider.class;
	}
	
	
	@Override
	public Class<? extends IGenerator> bindIGenerator() {
		return FeatureJvmModelGenerator.class;
	}
	
	
	public Class<? extends IFilePostProcessor> bindPostProcessor() {
		return TraceAwarePostProcessor.class;
	}
	
	@Override
	public Class<? extends ILinker> bindILinker() {
		return FeatureLazyLinker.class;
	}
	
	@Override
	public Class<? extends ILinkingService> bindILinkingService() {
		return FeatureLinkingService.class;
	}
	
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return FeatureValueConverterService.class;
	}
	
	
	@Override
	public Class<? extends IParser> bindIParser() {
		return CustomFeatureParser.class;
	}
	
	@Override
	public Class<? extends ILocationInFileProvider> bindILocationInFileProvider() {
		return FeatureLocationInFileProvider.class;
	}
	
	public Class<? extends JnarioBatchCompiler> bindJnarioBatchCompiler(){
		return FeatureBatchCompiler.class;
	}
	
	public Class<? extends XExpressionHelper> bindXExpressionHelper() {
		return JnarioExpressionHelper.class;
	}
	
	public Class<? extends IOutputConfigurationProvider> bindIOutputConfigurationProvider() {
		return XtendOutputConfigurationProvider.class;
	}
	
	public XbaseFactory bindXbaseFactory() {
		return XbaseFactory.eINSTANCE;
	}
	
	public Class<? extends IBasicFormatter> bindIBasicFormatter() {
		return XtendFormatter.class;
	}

	public Class<? extends IImportsConfiguration> bindIImportsConfiguration() {
		return XtendImportsConfiguration.class;
	}

	@Override
	public Class<? extends ConfigurableIssueCodesProvider> bindConfigurableIssueCodesProvider() {
		return XtendConfigurableIssueCodes.class;
	}
	
	public XtendFactory bindXtendFactory() {
		return XtendFactory.eINSTANCE;
	}

	@Override
	public Class<? extends DefaultBatchTypeResolver> bindDefaultBatchTypeResolver() {
		return TypeDeclarationAwareBatchTypeResolver.class;
	}

	@Override
	public Class<? extends DefaultReentrantTypeResolver> bindDefaultReentrantTypeResolver() {
		return DispatchAndExtensionAwareReentrantTypeResolver.class;
	}
	
	@Override
	public Class<? extends Manager> bindIResourceDescription$Manager() {
		return XtendResourceDescriptionManager.class;
	}
	
	@Override
	public Class<? extends ITypeComputer> bindITypeComputer() {
		return JnarioTypeComputer.class;
	}
	
	@Override
	public Class<? extends IResourceValidator> bindIResourceValidator() {
		return org.eclipse.xtend.core.validation.CachingResourceValidatorImpl.class;
	}
	

}
