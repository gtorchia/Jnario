/*
 * generated by Xtext
 */
package de.bmw.carit.jnario.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.common.types.xtext.ui.ITypesProposalProvider;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.folding.DefaultFoldingStructureProvider;
import org.eclipse.xtext.ui.editor.folding.IFoldingRegionProvider;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.AbstractAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfiguration;

import com.google.inject.Binder;

import de.bmw.carit.jnario.ui.contentassist.ImportingTypesProposalProvider;
import de.bmw.carit.jnario.ui.editor.FoldingRegionProvider;
import de.bmw.carit.jnario.ui.editor.FoldingStructureProvider;
import de.bmw.carit.jnario.ui.editor.JnarioEditor;
import de.bmw.carit.jnario.ui.editor.TaskTokenTypeToPartitionTypeMapper;
import de.bmw.carit.jnario.ui.highlighting.SemanticHighlightingCalculator;
import de.bmw.carit.jnario.ui.highlighting.HighlightingConfiguration;
import de.bmw.carit.jnario.ui.highlighting.TokenHighlighting;

/**
 * Use this class to register components to be used within the IDE.
 */
public class JnarioUiModule extends de.bmw.carit.jnario.ui.AbstractJnarioUiModule {
	public JnarioUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(XtextEditor.class).to(JnarioEditor.class);
	}
	
	public Class<? extends org.eclipse.xtext.ui.editor.syntaxcoloring.ISemanticHighlightingCalculator> bindISemanticHighlightingCalculator() {
		return SemanticHighlightingCalculator.class;
	}
	
	public Class<? extends IHighlightingConfiguration> bindIHighlightingConfiguration() {
		return HighlightingConfiguration.class;
	}
	
	public Class<? extends AbstractAntlrTokenToAttributeIdMapper> bindAbstractAntlrTokenToAttributeIdMapper() {
		return TokenHighlighting.class;
	}
	
	public Class<? extends ITokenTypeToPartitionTypeMapper> bindITokenTypeToPartitionTypeMapper() {
		return TaskTokenTypeToPartitionTypeMapper.class;
	}
	
	@Override
	public Class<? extends ITypesProposalProvider> bindITypesProposalProvider() {
		return ImportingTypesProposalProvider.class;
	}
	
	public Class<? extends IFoldingRegionProvider> bindIFoldingRegionProvider() {
		return FoldingRegionProvider.class;
	}
	
	public Class<? extends DefaultFoldingStructureProvider> bindFoldingStructureProvider() {
		return FoldingStructureProvider.class;
	}
	
}
