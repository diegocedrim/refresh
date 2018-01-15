package br.pucrio.opus.refresh.views.content.trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class CompilationUnitTreeNode extends AbstractTreeNode {
	private ICompilationUnit compilationUnit;
	
	
	public CompilationUnitTreeNode(TreeNode parent, ICompilationUnit compilationUnit) {
		super(parent);
		this.compilationUnit = compilationUnit;
	}
	
	private List<IType> getSmellyTypes() {
		try {
			OrganicDetectionService service = OrganicDetectionService.getInstance();
			IType[] types = compilationUnit.getAllTypes();
			List<IType> smelly = new ArrayList<>();
			for (IType type : types) {
				if (service.isSmelly(type)) {
					smelly.add(type);
				}
			}
			return smelly;
		} catch (JavaModelException e) {
			RefreshLogger.getInstance().logError(e);
			return null;
		}
	}

	@Override
	public Object[] getChildren() {
		List<IType> types = getSmellyTypes();
		if (types == null) {
			return new Object[0];
		}
		Object[] nodes = new Object[types.size()];
		for (int i = 0; i < nodes.length; i++) {
			nodes[i] = new TypeTreeNode(this, types.get(i));
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		List<IType> types = getSmellyTypes();
		return types != null && !types.isEmpty();
	}

	@Override
	public String getText() {
		return this.compilationUnit.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.compilationUnit();
	}

	@Override
	public IJavaElement getJavaElement() {
		return compilationUnit;
	}
}
