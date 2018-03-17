package br.pucrio.opus.refresh.views;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import br.pucrio.opus.refresh.views.content.elementstree.TreeNode;

public class RefreshLabelProvider extends LabelProvider {
	
	public String getText(Object obj) {
		TreeNode node = (TreeNode)obj;
		return node.getText();
	}
	public Image getImage(Object obj) {
		TreeNode node = (TreeNode)obj;
		return node.getImage();
	}
	
}
