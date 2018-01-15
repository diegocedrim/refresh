package br.pucrio.opus.refresh.views.content.trees;

import java.util.List;

import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class TypeTreeNode extends AbstractTreeNode {
	private IType type;

	public TypeTreeNode(TreeNode parent, IType type) {
		super(parent);
		this.type = type;
	}
	
	private IMethod[] getMethods() {
		try {
			return type.getMethods();
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
	}

	@Override
	public Object[] getChildren() {
		OrganicDetectionService service = OrganicDetectionService.getInstance();
		List<Smell> smells = service.getResource(type).getSmells();
		
		IMethod[] methods = getMethods();
		if (methods == null) {
			return new Object[0];
		}
		Object[] nodes = new Object[methods.length + smells.size()];
		for (int i = 0; i < smells.size(); i++) {
			nodes[i] = new SmellTreeNode(this, smells.get(i));
		}
		for (int i = smells.size(); i < nodes.length; i++) {
			nodes[i] = new MethodTreeNode(this, methods[i - smells.size()]);
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		OrganicDetectionService service = OrganicDetectionService.getInstance();
		List<Smell> smells = service.getResource(type).getSmells();
		IMethod[] methods = getMethods();
		return (methods != null && methods.length > 0) || !smells.isEmpty();
	}

	@Override
	public String getText() {
		return type.getElementName();
	}

	@Override
	public Image getImage() {
		try {
			if (type.isInterface()) {
				return PlatformIconProvider.interfaceIcon();
			} else if (type.isEnum()) {
				return PlatformIconProvider.enumIcon();
			} else if (type.isClass()) {
				return PlatformIconProvider.classIcon();
			} 
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
		}
		return null;
	}

}
