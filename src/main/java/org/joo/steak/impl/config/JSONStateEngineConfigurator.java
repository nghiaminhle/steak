package org.joo.steak.impl.config;

import java.util.HashMap;
import java.util.Map;

import org.joo.steak.framework.StateInitializationException;
import org.joo.steak.framework.config.StateEngineConfiguration;
import org.joo.steak.framework.config.StateEngineConfigurator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONStateEngineConfigurator implements StateEngineConfigurator {
	
	private String config;
	
	public JSONStateEngineConfigurator(String config) {
		this.config = config;
	}

	@Override
	public StateEngineConfiguration getConfiguration() {
		try {
			JSONObject configJSON = new JSONObject(config);
	
			JSONArray statesJSON = configJSON.optJSONArray("states");
			Map<String, Object> states = parseStatesJSON(statesJSON);
			
			JSONArray transitionsJSON = configJSON.optJSONArray("flows");
			Map<String, Map<String, Object[]>> transitions = parseTransitionsJSON(transitionsJSON);
			
			return new DefaultStateEngineConfiguration(states, transitions);
		} catch (JSONException ex) {
			throw new StateInitializationException("Invalid JSON configuration", ex);
		}
	}

	private Map<String, Map<String, Object[]>> parseTransitionsJSON(JSONArray transitionsJSON) {
		Map<String, Map<String, Object[]>> map = new HashMap<>();
		if (transitionsJSON != null) {
			for (int i=0; i<transitionsJSON.length(); i++) {
				JSONObject transitionJSON = transitionsJSON.getJSONObject(i);
				String state = transitionJSON.getString("from");
				String action = transitionJSON.getString("action");
				if (!map.containsKey(state)) {
					map.put(state, new HashMap<String, Object[]>());
				}
				JSONArray transitionsArrJSON = transitionJSON.getJSONArray("transitions");
				Object[] transitions = parseTransitionsArrJSON(transitionsArrJSON);
				map.get(state).put(action, transitions);
			}
		}
		return map;
	}

	private Object[] parseTransitionsArrJSON(JSONArray transitionsArrJSON) {
		if (transitionsArrJSON == null)
			return new Object[0];
		
		Object[] objects = new Object[transitionsArrJSON.length()];
		for (int i=0; i<transitionsArrJSON.length(); i++) {
			String obj = transitionsArrJSON.getString(i);
			objects[i] = obj;
		}
		return objects;
	}

	private Map<String, Object> parseStatesJSON(JSONArray statesJSON) {
		Map<String, Object> map = new HashMap<>();
		if (statesJSON != null) {
			for (int i=0; i<statesJSON.length(); i++) {
				JSONObject obj = statesJSON.getJSONObject(i);
				String key = obj.getString("name");
				String value = obj.getString("value");
				map.put(key, value);
			}
		}
		return map;
	}
}
