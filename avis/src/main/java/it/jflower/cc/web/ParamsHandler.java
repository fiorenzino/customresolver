package it.jflower.cc.web;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ParamsHandler implements Serializable {
	public ParamsHandler() {

	}

	private Map<String, String> params;

	public Map<String, String> getParams() {
		if (this.params == null)
			this.params = new HashMap<String, String>();
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public void addParam(String param) {
		if (param.contains("=")) {
			String key = param.substring(0, param.indexOf("="));
			String val = param.substring(param.indexOf("=") + 1);
			getParams().put(key, val);
		}
	}

	public String getParam(String param) {
		if (getParams().containsKey(param)) {
			return getParams().get(param);
		}
		return null;
	}
}
