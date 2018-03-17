package br.pucrio.opus.refresh.recommendations.extractmethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;

public class VariableAndMethodCallsVisitor extends ASTVisitor {
	private List<IBinding> nodes;
	
	private Map<IBinding, List<Integer>> linesUsingMap;
	
	public VariableAndMethodCallsVisitor() {
		this.nodes = new ArrayList<>();
		this.linesUsingMap = new HashMap<>();
	}
	
	private int getLineNumber(ASTNode node) {
		CompilationUnit compilationUnit = (CompilationUnit)node.getRoot();
		return compilationUnit.getLineNumber(node.getStartPosition());
	}
	
	private void registerLineUsing(ASTNode node, IBinding binding) {
		int lineNumber = getLineNumber(node);
		List<Integer> linesUsing = this.linesUsingMap.get(binding);
		if (linesUsing == null) {
			linesUsing = new ArrayList<>();
			this.linesUsingMap.put(binding, linesUsing);
		}
		if (!linesUsing.contains(lineNumber)) {
			linesUsing.add(lineNumber);
		}
	}
	
	private void registerNodeOfInterest(ASTNode node, IBinding binding) {
		if (!this.nodes.contains(binding)) {
			this.nodes.add(binding);
		}
		this.registerLineUsing(node, binding);
	}
	
	@Override
	public boolean visit(SimpleName node) {
		IBinding binding = node.resolveBinding();
		if (binding != null && binding.getKind() == IBinding.VARIABLE) {
			registerNodeOfInterest(node, binding);
		}
		return true;
	}
	
	@Override
	public boolean visit(MethodInvocation node) {
		IMethodBinding binding = node.resolveMethodBinding();
		if (binding != null) {
			registerNodeOfInterest(node, binding);
		}
		return true;
	}
	
	public List<IBinding> getNodeBindings() {
		return nodes;
	}
	
	public List<Integer> getLinesUsing(IBinding binding) {
		return this.linesUsingMap.get(binding);
	}
}
