package br.pucrio.opus.organic.agglomeration.relation;

public class SyntacticalRelationChecker extends CompositeRelationChecker {

	public SyntacticalRelationChecker() {
		this.addChecker(new SharedAttributeChecker());
		this.addChecker(new MethodCallChecker());
		this.addChecker(new ClassExtensionChecker());
		this.addChecker(new OverrideChecker());
	}
}
