package br.pucrio.opus.refresh.views.content.elementstree;

import org.eclipse.jdt.core.IJavaElement;

import br.pucrio.opus.refresh.views.content.LabelAware;

public interface TreeNode extends LabelAware {
	Object[] getChildren();

	Object getParent();

	boolean hasChildren();
	
	IJavaElement getJavaElement();
	
}
