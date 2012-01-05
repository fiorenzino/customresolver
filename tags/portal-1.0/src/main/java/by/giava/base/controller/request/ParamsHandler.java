package by.giava.base.controller.request;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class ParamsHandler implements Serializable {

	private static final long serialVersionUID = 1L;

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

	@Deprecated
	public void addParam(String param) {
		if (param.contains("=")) {
			String key = param.substring(0, param.indexOf("="));
			String val = param.substring(param.indexOf("=") + 1);
			getParams().put(key, val);
		}
	}

	public void addParam(String paramKey, String paramValue) {
		getParams().put(paramKey, paramValue);

	}

	public String getParam(String param) {
		if (getParams().containsKey(param)) {
			try {
				return URLDecoder.decode((String) getParams().get(param),
						"UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return getParams().get(param);
		}
		return "";
	}
}
