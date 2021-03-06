package org.joo.steak.framework;

import org.joo.steak.framework.event.StateChangedDispatcher;
import org.joo.steak.framework.event.StateChangedEvent;

/**
 * A state is an independent object which can perform business logic
 * upon some data. The data will be passed as <code>stateContext</code>.
 * It can notify the <code>StateManager</code> when it finishes its logic
 * to move to next state by calling <code>fireStateChangedEvent</code>
 * A state should not concern about how other states actually work.
 * A state can be assigned an unique ID in <code>StateManager</code>
 * @author griever
 *
 */
public interface State extends StateChangedDispatcher {

	/**
	 * Called by <code>StateManager</code> when reaching the current state
	 * @param stateContext 
	 * 			the state context which may contains data to be processed
	 */
	public void onEntry(StateContext stateContext);
	
	/**
	 * Called by <code>StateManager</code> when exiting the current state
	 * @param event
	 * 			the original event dispatched by this state when changing state
	 */
	public void onExit(StateChangedEvent event);
}
