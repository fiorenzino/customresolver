package example.modweb.mod02.module;

import example.modweb.api.ModuleProvider;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class Mod02Module implements ModuleProvider {

	@Override
	public String getName() {
		return "Module 02";
	}

	@Override
	public String getId() {
		return "example.modweb.mod02";
	}

	@Override
	public String getDescription() {
		return "Module 02 provides functionality B.";
	}

	@Override
	public String getDefaultAction() {
		return "/private/mod02/index.jsp";
	}

}
