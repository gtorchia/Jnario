package de.bmw.carit.jnario.lib;

import java.util.List;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.SelfDescribing;
import org.hamcrest.StringDescription;

import com.google.common.collect.ImmutableList;

public abstract class BaseMatcherChain<T> implements MatcherChain<T> {

	protected static class Matching<T> implements SelfDescribing{
	
			private final Matcher<T> matcher;
			private final T actual;
	
			
			
			public Matching(Matcher<T> matcher, T actual) {
				this.matcher = matcher;
				this.actual = actual;
			}
	
			public void describeTo(Description description) {
				description.appendDescriptionOf(matcher);
			}
			
			public boolean evaluate(){
				return matcher.matches(getActual());
			}
	
			public void doAssert() {
				if (!evaluate()) {
					throwAssertError();
				}
			}
			
			private void throwAssertError()	 {
				Description description = new StringDescription();
				description.appendText("\nExpected: ");
				description.appendDescriptionOf(this);
				description.appendText("\n     got: ");
				description.appendValue(getActual());
				description.appendText("\n");
				throw new java.lang.AssertionError(description.toString());
			}
	
			protected Object getActual() {
				return actual;
			}
			
		}

	private List<MatcherFactory<T>> factories;

	public BaseMatcherChain() {
		this(ImmutableList.<MatcherFactory<T>>of(new XMatchers.IsFactory<T>()));
	}
	
	public BaseMatcherChain(List<MatcherFactory<T>> factories){
		this.factories = factories;
	}

	public MatcherChain<T> append(MatcherFactory<T> factory) {
		factories.add(factory);
		return this;
	}

	protected Matching<T> matchingFor(Matcher<T> expected, T actual) {
		MatcherFactory<T> factory = firstMatcher();
		expected = factory.create(expected);
		return new Matching<T>(addRemainingMatchers(expected), actual);
	}

	private MatcherFactory<T> firstMatcher() {
		return factories.get(factories.size()-1);
	}


	private Matcher<T> addRemainingMatchers(Matcher<T> result) {
		for(int i = factories.size()-2; i >= 0; i--){
			result = factories.get(i).create(result);
		}
		return result;
	}
	
	protected List<MatcherFactory<T>> getFactories() {
		return factories;
	}

}