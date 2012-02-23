/*******************************************************************************
 * Copyright (c) 2012 BMW Car IT and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package de.bmw.carit.jnario.common.test.util;

import com.google.inject.Guice;
import com.google.inject.Injector;

import de.bmw.carit.jnario.feature.FeatureRuntimeModule;
import de.bmw.carit.jnario.feature.FeatureStandaloneSetup;
import de.bmw.carit.jnario.feature.feature.FeatureFactory;

/**
 * @author Birgit Engelmann - Initial contribution and API
 */
public class FeatureStandaloneTestSetup extends FeatureStandaloneSetup {
	@Override
	public Injector createInjector() {
		return Guice.createInjector(new FeatureRuntimeModule() {
			@Override
			public ClassLoader bindClassLoaderToInstance() {
				return FeatureTestRunner.class.getClassLoader();
			}
			
			@SuppressWarnings("unused")
			public Class<? extends BehaviorExecutor> bindBehaviorExecutor() {
				return FeatureExecutor.class;
			}

			@SuppressWarnings("unused")
			public FeatureFactory bindFactory() {
				return FeatureFactory.eINSTANCE;
			}

		});
	}
}