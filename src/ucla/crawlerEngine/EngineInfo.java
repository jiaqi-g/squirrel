package ucla.crawlerEngine;

import java.lang.reflect.*;

public class EngineInfo {
	private Class<? extends AbstractEngine> engineClass;
	
	public EngineInfo(Class<? extends AbstractEngine> engineClass) {
		this.engineClass = engineClass;
	}
	
	public AbstractEngine newInstance() {
		try {
			return engineClass.newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
