// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 02/17/2015 00:51:36
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.trees;

/**
 * BT library that includes the trees read from the following files:
 * <ul>
 * <li>data/jbt/forestBoss.xbt</li>
 * </ul>
 */
public class ForestBossBT implements jbt.execution.core.IBTLibrary {
	/** Tree generated from file data/jbt/forestBoss.xbt. */
	private static jbt.model.core.ModelTask Root;

	/* Static initialization of all the trees. */
	static {
		Root = new jbt.model.task.decorator.ModelRepeat(
				null,
				new jbt.model.task.composite.ModelSelector(
						null,
						new jbt.model.task.composite.ModelSequence(
								null,
								new jbt.model.task.decorator.ModelLimit(
										null,
										1,
										new com.blastedstudios.scab.ai.bt.actions.ForestInit(
												null))),
						new jbt.model.task.composite.ModelSequence(
								null,
								new com.blastedstudios.scab.ai.bt.actions.Jump(
										null, (boolean) true, null),
								new jbt.model.task.composite.ModelParallel(
										null,
										jbt.model.task.composite.ModelParallel.ParallelPolicy.SELECTOR_POLICY,
										new jbt.model.task.composite.ModelSelector(
												null,
												new jbt.model.task.composite.ModelSequence(
														null,
														new jbt.model.task.leaf.ModelWait(
																null, 1000)),
												new jbt.model.task.composite.ModelSequence(
														null,
														new jbt.model.task.leaf.ModelWait(
																null, 200),
														new com.blastedstudios.scab.ai.bt.conditions.PlayerUnder(
																null)))),
								new com.blastedstudios.scab.ai.bt.actions.Drop(
										null),
								new com.blastedstudios.scab.ai.bt.actions.Land(
										null),
								new com.blastedstudios.scab.ai.bt.actions.Action(
										null, "lunge", null),
								new jbt.model.task.leaf.ModelWait(null, 350),
								new com.blastedstudios.scab.ai.bt.actions.Action(
										null, "lunge", null),
								new jbt.model.task.leaf.ModelWait(null, 350),
								new com.blastedstudios.scab.ai.bt.actions.Action(
										null, "lunge", null),
								new jbt.model.task.leaf.ModelWait(null, 350),
								new com.blastedstudios.scab.ai.bt.actions.Land(
										null),
								new com.blastedstudios.scab.ai.bt.actions.CooldownStart(
										null, "forestBossCooldown", null,
										(int) 3500, null),
								new com.blastedstudios.scab.ai.bt.actions.Jump(
										null, (boolean) false, null),
								new jbt.model.task.composite.ModelParallel(
										null,
										jbt.model.task.composite.ModelParallel.ParallelPolicy.SEQUENCE_POLICY,
										new jbt.model.task.composite.ModelSequence(
												null,
												new com.blastedstudios.scab.ai.bt.actions.RunAway(
														null),
												new com.blastedstudios.scab.ai.bt.actions.CooldownEnd(
														null,
														"forestBossCooldown",
														null),
												new com.blastedstudios.scab.ai.bt.actions.Stop(
														null))))));

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
