package br.pucrio.opus.refresh.views.content.trees;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.organic.OrganicDetectionService;
import br.pucrio.opus.refresh.services.RefreshLogger;
import br.pucrio.opus.refresh.views.PlatformIconProvider;

public class PackageFragmentTreeNode extends AbstractTreeNode {
	private IPackageFragment packageFragment;

	public PackageFragmentTreeNode(TreeNode parent, IPackageFragment packageFragment) {
		super(parent);
		this.packageFragment = packageFragment;
	}
	
	private List<ICompilationUnit> getSmellyCompilationUnits() {
		try {
			OrganicDetectionService service = OrganicDetectionService.getInstance();
			ICompilationUnit[] units = packageFragment.getCompilationUnits();
			List<ICompilationUnit> smellyUnits = new ArrayList<>();
			for (ICompilationUnit unit : units) {
				if (service.isSmelly(unit)) {
					smellyUnits.add(unit);
				}
			}
			return smellyUnits;
		} catch (JavaModelException e) {
			RefreshLogger.getInstance().logError(e);
			return null;
		}
	}

	@Override
	public String getText() {
		return packageFragment.getElementName();
	}

	@Override
	public Image getImage() {
		return PlatformIconProvider.packageFragment();
	}

	@Override
	public Object[] getChildren() {
		List<ICompilationUnit> units = getSmellyCompilationUnits();
		if (units == null || units.isEmpty()) {
			return new Object[0];
		}
		CompilationUnitTreeNode[] nodes = new CompilationUnitTreeNode[units.size()];
		for (int i = 0; i < units.size(); i++) {
			nodes[i] = new CompilationUnitTreeNode(this, units.get(i));
		}
		return nodes;
	}

	@Override
	public boolean hasChildren() {
		List<ICompilationUnit> units = getSmellyCompilationUnits();
		return units != null && !units.isEmpty();
	}

}
