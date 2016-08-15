// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 04/12/2015 15:16:41
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.trees;

/**
 * BT library that includes the trees read from the following files:
 * <ul>
 * <li>data/jbt/enemyTurret.xbt</li>
 * </ul>
 */
public class EnemyTurretBT implements jbt.execution.core.IBTLibrary {
	/** Tree generated from file data/jbt/enemyTurret.xbt. */
	private static jbt.model.core.ModelTask Root;

	/* Static initialization of all the trees. */
	static {
		Root = new jbt.model.task.decorator.ModelRepeat(
				null,
				new jbt.model.task.composite.ModelSelector(
						null,
						new jbt.model.task.composite.ModelSequence(
								null,
								new com.blastedstudios.scab.ai.bt.conditions.Visible(
										null),
								new jbt.model.task.composite.ModelParallel(
										null,
										jbt.model.task.composite.ModelParallel.ParallelPolicy.SELECTOR_POLICY,
										new com.blastedstudios.scab.ai.bt.conditions.Danger(
												null),
										new com.blastedstudios.scab.ai.bt.conditions.Alert(
												null)),
								new com.blastedstudios.scab.ai.bt.actions.ClosestTurret(
										null),
								new com.blastedstudios.scab.ai.bt.actions.Move(
										null, null, "ClosestTurretObjective"),
								new com.blastedstudios.scab.ai.bt.actions.OperateTurret(
										null, null, "DangerTarget")),
						new jbt.model.task.composite.ModelSequence(
								null,
								new com.blastedstudios.scab.ai.bt.actions.CurrentObjective(
										null),
								new com.blastedstudios.scab.ai.bt.actions.Move(
										null, null, "CurrentObjectiveTarget"))));

	}

	/**
	 * Returns a behaviour tree by its name, or null in case it cannot be found.
	 * It must be noted that the trees that are retrieved belong to the class,
	 * not to the instance (that is, the trees are static members of the class),
	 * so they are shared among all the instances of this class.
	 */
	public jbt.model.core.ModelTask getBT(java.lang.String name) {
		if (name.equals("Root")) {
			return Root;
		}
		return null;
	}

	/**
	 * Returns an Iterator that is able to iterate through all the elements in
	 * the library. It must be noted that the iterator does not support the
	 * "remove()" operation. It must be noted that the trees that are retrieved
	 * belong to the class, not to the instance (that is, the trees are static
	 * members of the class), so they are shared among all the instances of this
	 * class.
	 */
	public java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> iterator() {
		return new BTLibraryIterator();
	}

	private class BTLibraryIterator
			implements
			java.util.Iterator<jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>> {
		static final long numTrees = 1;
		long currentTree = 0;

		public boolean hasNext() {
			return this.currentTree < numTrees;
		}

		public jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask> next() {
			this.currentTree++;

			if ((this.currentTree - 1) == 0) {
				return new jbt.util.Pair<java.lang.String, jbt.model.core.ModelTask>(
						"Root", Root);
			}

			throw new java.util.NoSuchElementException();
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}
	}
}
