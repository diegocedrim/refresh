package br.pucrio.opus.refresh.views.content.trees;

import java.util.List;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.organic.collector.Smell;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class MethodTreeNode extends AbstractTreeNode {
	private IMethod method;
	private String text;

	public MethodTreeNode(TreeNode parent, IMethod method) {
		super(parent);
		this.method = method;
		this.text = getMethodName();
	}
	
	private String getMethodName() {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append(method.getElementName());
			buffer.append("(");
			String[] parameters = method.getParameterTypes();
			for (int i = 0; i < parameters.length; i++) {
				buffer.append(Signature.getSignatureSimpleName(parameters[i]));
				if (i < parameters.length - 1) {
					buffer.append(", ");
				}
			}
			buffer.append(") : ");
			buffer.append(Signature.getSignatureSimpleName(method.getReturnType()));
			return buffer.toString();
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return method.getElementName();
		}
	}

	@Override
	public Object[] getChildren() {
		OrganicDetectionService service = OrganicDetectionService.getInstance();
		List<Smell> smells = service.getResource(method).getSmells();
		if (smells.isEmpty()) {
			return new Object[0];
		}
		Object[] nodes = new Object[smells.size()];
		for (int i = 0; i < smells.size(); i++) {
			nodes[i] = new SmellTreeNode(this, smells.get(i), method);
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		OrganicDetectionService service = OrganicDetectionService.getInstance();
		List<Smell> smells = service.getResource(method).getSmells();
		return !smells.isEmpty();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Image getImage() {
		try {
			int flags = this.method.getFlags();
			if (Flags.isPublic(flags)) {
				return PlatformIconProvider.publicMethod();
			} else if (Flags.isPrivate(flags)) {
				return PlatformIconProvider.privateMethod();
			} else if (Flags.isProtected(flags) || Flags.isPackageDefault(flags)) {
				return PlatformIconProvider.protectedMethod();
			} 
			return null;
		} catch (JavaModelException e) {
			new RefreshLogger().logError(e);
			return null;
		}
		
	}
	
	@Override
	public IJavaElement getJavaElement() {
		return method;
	}

}
