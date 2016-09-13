package org.joo.state.impl.states;

import org.joo.state.framework.StateTransition;
import org.joo.state.impl.AbstractTransitionProxy;

public class TransitionLazyLoadProxy extends AbstractTransitionProxy {
	
	private StateTransition transition;
	
	public TransitionLazyLoadProxy(String className) {
		super(className);
	}

	@Override
	protected StateTransition getTransition() {
		if (transition == null) {
			transition = loadTransition();
		}
		return transition;
	}
}
