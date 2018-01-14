package br.pucrio.opus.organic.agglomeration.relation;

import java.util.List;

import br.pucrio.opus.organic.agglomeration.SmellyNode;
import br.pucrio.opus.organic.resources.Method;
import br.pucrio.opus.organic.resources.Type;

public abstract class RelationChecker {

	public abstract boolean isRelated(SmellyNode u, SmellyNode v);
	
	protected boolean areBothMethods(SmellyNode u, SmellyNode v) {
		return u.getResource() instanceof Method && v.getResource() instanceof Method;
	}
	
	protected boolean areBothClasses(SmellyNode u, SmellyNode v) {
		return u.getResource() instanceof Type && v.getResource() instanceof Type;
	}
	
	protected boolean intersects(List<?> uList, List<?> vList) {
		for (Object obj : uList) {
			if (vList.contains(obj)) {
				return true;
			}
		}
		return false;
	}
	
	public String getName() {
		return this.getClass().getName();
	}
}
