// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/19/2014 08:49:29
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.actions;

/** ModelAction class created from MMPM action RunAway. */
public class RunAway extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of RunAway. */
	public RunAway(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a com.blastedstudios.scab.ai.bt.actions.execution.RunAway task
	 * that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new com.blastedstudios.scab.ai.bt.actions.execution.RunAway(
				this, executor, parent);
	}
}