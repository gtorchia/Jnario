/*
 * generated by Xtext
 */
package de.bmw.carit.jnario.ui.internal;

import static com.google.inject.util.Modules.override;
import static com.google.inject.Guice.createInjector;

import org.apache.log4j.Logger;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

import java.util.concurrent.ExecutionException;

import org.eclipse.xtext.ui.shared.SharedStateModule;

/**
 * This class was generated. Customizations should only happen in a newly
 * introduced subclass. 
 */
public class JnarioActivator extends AbstractUIPlugin {
	
	private static final Logger logger = Logger.getLogger(JnarioActivator.class);
	
	private Cache<String, Injector> injectors = CacheBuilder.newBuilder().build(new CacheLoader<String, Injector>() {
		@Override
		public Injector load(String language) throws Exception {
			Module runtimeModule = getRuntimeModule(language);
			Module sharedStateModule = getSharedStateModule();
			Module uiModule = getUiModule(language);
			Module mergedModule = override(override(runtimeModule).with(sharedStateModule)).with(uiModule);
			return createInjector(mergedModule);
		}
	});
	
	private static JnarioActivator INSTANCE;
	
	public static final String DE_BMW_CARIT_JNARIO_JNARIO = "de.bmw.carit.jnario.Jnario";
	
	public Injector getInjector(String languageName) {
		try {
			return injectors.get(languageName);
		} catch(ExecutionException e) {
			logger.error("Failed to create injector for " + languageName);
			logger.error(e.getMessage(), e);
			throw new RuntimeException("Failed to create injector for " + languageName, e);
		}
	}
	
	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		INSTANCE = this;
	}
	
	@Override
	public void stop(BundleContext context) throws Exception {
		injectors.invalidateAll();
		injectors.cleanUp();
		INSTANCE = null;
		super.stop(context);
	}
	
	public static JnarioActivator getInstance() {
		return INSTANCE;
	}
	
	protected Module getRuntimeModule(String grammar) {
		if (DE_BMW_CARIT_JNARIO_JNARIO.equals(grammar)) {
			return new de.bmw.carit.jnario.JnarioRuntimeModule();
		}
		
		throw new IllegalArgumentException(grammar);
	}
	
	protected Module getUiModule(String grammar) {
		if (DE_BMW_CARIT_JNARIO_JNARIO.equals(grammar)) {
			return new de.bmw.carit.jnario.ui.JnarioUiModule(this);
		}
		
		throw new IllegalArgumentException(grammar);
	}
	
	protected Module getSharedStateModule() {
		return new SharedStateModule();
	}
	
}
