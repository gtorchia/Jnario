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
package org.jnario.suite.ui.labeling;

import org.eclipse.swt.graphics.Image;
import org.eclipse.xtend.core.jvmmodel.IXtendJvmAssociations;
import org.eclipse.xtend.ide.labeling.XtendImages;
import org.eclipse.xtend.ide.labeling.XtendLabelProvider;
import org.eclipse.xtext.common.types.JvmVisibility;
import org.eclipse.xtext.xbase.ui.labeling.XbaseImageAdornments;
import org.jnario.suite.jvmmodel.SuiteClassNameProvider;
import org.jnario.suite.suite.Suite;

import com.google.inject.Inject;

/**
 * Provides labels for a EObjects.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
@SuppressWarnings("restriction")
public class SuiteLabelProvider extends XtendLabelProvider {

	@Inject private SuiteClassNameProvider nameProvider;
	
	@Inject private XtendImages images;
	
	@Inject
	private XbaseImageAdornments adornments;
	
	@Inject
	private IXtendJvmAssociations associations;
	
	public Image image(Suite element) {
		return images.forClass(JvmVisibility.PUBLIC, adornments.get(associations.getInferredType(element)));
	}
	
	public String text(Suite element) {
		return nameProvider.describe(element);
	}

}
