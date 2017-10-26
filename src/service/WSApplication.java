package service;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

// addresse de basse pour acc�der au WS
@ApplicationPath("/")
public class WSApplication extends Application {
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();
	
	public WSApplication() {
		singletons.add(new WService());
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
}
