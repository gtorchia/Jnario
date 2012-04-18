/*******************************************************************************
 * Copyright (c) 2012 BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package org.jnario.feature.ui.highlighting;

import static org.eclipse.xtext.nodemodel.util.NodeModelUtils.findNodesForFeature;
import static org.jnario.feature.ui.highlighting.FeatureHighlightingConfiguration.SCENARIO_ID;
import static org.jnario.util.Strings.getFirstWord;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtend.core.xtend.XtendClass;
import org.eclipse.xtend.core.xtend.XtendField;
import org.eclipse.xtend.core.xtend.XtendMember;
import org.eclipse.xtend.core.xtend.XtendPackage;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.LeafNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.xbase.XBlockExpression;
import org.jnario.ExampleTable;
import org.jnario.feature.feature.Feature;
import org.jnario.feature.feature.FeaturePackage;
import org.jnario.feature.feature.Scenario;
import org.jnario.feature.feature.Step;
import org.jnario.feature.feature.StepExpression;
import org.jnario.feature.feature.StepReference;
import org.jnario.feature.feature.util.FeatureSwitch;
import org.jnario.feature.jvmmodel.StepArgumentsProvider;
import org.jnario.ui.highlighting.JnarioHighlightingCalculator;

/**
 * @author Birgit Engelmann - Initial contribution and API
 * @author Sebastian Benz
 */
public class FeatureSemanticHighlightingCalculator extends JnarioHighlightingCalculator {

	private class Implementation extends FeatureSwitch<Boolean> {

		private final IHighlightedPositionAcceptor acceptor;

		public Implementation(IHighlightedPositionAcceptor acceptor) {
			this.acceptor = acceptor;
		}
		
		@Override
		public Boolean caseStepExpression(StepExpression object) {
			XBlockExpression expression = object.getBlockExpression();
			highlightRichStrings(expression, acceptor);
			return Boolean.TRUE;
		}

		@Override
		public Boolean caseScenario(Scenario scenario) {
			if(!scenario.getExamples().isEmpty()){
				for (ExampleTable table : scenario.getExamples()	) {
					highlightExampleHeader(table);
				}
			}
			for (XtendMember member : scenario.getMembers()) {
				if(member.eClass() == XtendPackage.Literals.XTEND_FIELD){
					XtendField field = (XtendField) member;
					highlightXtendField(field,acceptor);
				}
				highlightDeprectedXtendAnnotationTarget(acceptor, member);
			}
			return highlightXtendClassName(scenario, SCENARIO_ID);
		}

		private Boolean highlightXtendClassName(XtendClass object, String id) {
			List<INode> nodes = findNodesForFeature(object, XtendPackage.Literals.XTEND_CLASS__NAME);
			for (INode node : nodes) {
				highlightNode(node, id, acceptor);
			}
			return Boolean.TRUE;
		}
		
		@Override
		public Boolean caseFeature(Feature feature) {
			List<INode> nodes = findNodesForFeature(feature, XtendPackage.Literals.XTEND_CLASS__NAME);
			for (INode node : nodes) {
				highlightNode(node, FeatureHighlightingConfiguration.FEATURE_ID, acceptor);
			}
			// there is an xtext problem if the splitted token is the only rule present
			if(feature.getBackground() == null && feature.getScenarios().isEmpty()){
				nodes = findNodesForFeature(feature, FeaturePackage.Literals.FEATURE__DESCRIPTION);
				for (INode node : nodes) {
					highlightNode(node, FeatureHighlightingConfiguration.STEP_TEXT_ID, acceptor);
				}
			}
			return Boolean.TRUE;
		}
		
		@Override
		public Boolean caseStep(Step step) {
			String description;
			if(step.getName() != null){
				description = getFirstWord(step.getName());
				highlightStep(description, step, FeaturePackage.Literals.STEP__NAME);
			}
			else if(step instanceof StepReference){
				StepReference ref = (StepReference) step;
				highlightFirstWordOfReference(ref, ref.getReference());
			}
			highlightIdentifiers(step);
			return Boolean.TRUE;
		}

		private void highlightStep(String string, EObject object, EAttribute attribute) {
			acceptor.addPosition(offset(object, attribute), string.length(),
					FeatureHighlightingConfiguration.STEP_ID);
		}

		private void highlightReference(String string, EObject object, EReference reference, String highlightConfig){
			acceptor.addPosition(offset(object, reference), string.length(), highlightConfig);
		}
		
		private void highlightFirstWordOfReference(Step reference, Step referencedStep){
			String description = getFirstWord(referencedStep.getName());
			if(description != ""){
				highlightReference(description, reference, FeaturePackage.Literals.STEP_REFERENCE__REFERENCE, FeatureHighlightingConfiguration.STEP_REFERNCE_ID);
				
			}
			else{
				// unresolved reference -> take first word until whitespace
				description = getFirstWord(stepReferenceName(reference, FeaturePackage.Literals.STEP_REFERENCE__REFERENCE));
				highlightReference(description, reference, FeaturePackage.Literals.STEP_REFERENCE__REFERENCE, FeatureHighlightingConfiguration.STEP_ID);
			}
		}
		
		private String stepReferenceName(Step step, EReference ref){
			List<INode> nodes = NodeModelUtils.findNodesForFeature(step, ref);
			// works only if keyword exists only once in Step
			return nodes.iterator().next().getText();
		}

		private int offset(EObject content, EAttribute attribute) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(content,
					attribute);
			// works only if keyword exists only once in Step
			return nodes.iterator().next().getOffset();
		}

		private int offset(EObject content, EReference reference) {
			List<INode> nodes = NodeModelUtils.findNodesForFeature(content,
					reference);
			// works only if keyword exists only once in Step
			return nodes.iterator().next().getOffset();
		}

		private void highlightIdentifiers(Step step){
			String name;
			int offset;
			if(step instanceof StepReference){
				name = stepReferenceName(step, FeaturePackage.Literals.STEP_REFERENCE__REFERENCE);
				offset = offset(step, FeaturePackage.Literals.STEP_REFERENCE__REFERENCE);
			}
			else{
				name = step.getName();
				offset = offset(step, FeaturePackage.Literals.STEP__NAME);
			}
			if(name != null){
				Matcher m = StepArgumentsProvider.ARG_PATTERN.matcher(name);
				while (m.find()) {
					acceptor.addPosition(offset + m.start(1), m.end(1) - m.start(1), FeatureHighlightingConfiguration.IDENTIFIERS_ID);
				} 
			}
		}

		private void highlightExampleHeader(ExampleTable table){
			if(table == null){
				return;
			}
			for (int i = 0; i < table.getColumns().size(); i++) {
				XtendField element = table.getColumns().get(i);
				INode node = NodeModelUtils.getNode(element);
				highlightNode(node, FeatureHighlightingConfiguration.IDENTIFIERS_ID, acceptor);
				if(table.getColumns().size() == i + 1){
					highlightTableHeadingEnd(node.getNextSibling());
				}
			}
		}

		private void highlightTableHeadingEnd(INode node) {
			while(node instanceof LeafNode){
				if(!((LeafNode)node).isHidden()){
					highlightNode(node, FeatureHighlightingConfiguration.IDENTIFIERS_ID, acceptor);
					return;
				}
				node = node.getNextSibling();
			}
		}
	}

	public void provideHighlightingFor(XtextResource resource,
			IHighlightedPositionAcceptor acceptor) {
		super.provideHighlightingFor(resource, acceptor);
		if (noNodeModel(resource)) {
			return;
		}

		Implementation highlighter = new Implementation(acceptor);
		Iterator<EObject> contents = resource.getAllContents();
		while (contents.hasNext()) {
			highlighter.doSwitch((EObject) contents.next());
		}
	}

	protected EObject root(XtextResource resource) {
		return resource.getParseResult().getRootASTElement();
	}

	protected boolean noNodeModel(XtextResource resource) {
		return resource == null || resource.getParseResult() == null
				|| root(resource) == null;
	}

}
