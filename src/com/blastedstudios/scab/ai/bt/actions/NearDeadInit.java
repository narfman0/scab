// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                       DO NOT MODIFY                     
//                                                         
// Generated on 10/25/2014 15:29:55
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.actions;

/** ModelAction class created from MMPM action NearDeadInit. */
public class NearDeadInit extends jbt.model.task.leaf.action.ModelAction {

	/** Constructor. Constructs an instance of NearDeadInit. */
	public NearDeadInit(jbt.model.core.ModelTask guard) {
		super(guard);
	}

	/**
	 * Returns a com.blastedstudios.scab.ai.bt.actions.execution.NearDeadInit
	 * task that is able to run this task.
	 */
	public jbt.execution.core.ExecutionTask createExecutor(
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		return new com.blastedstudios.scab.ai.bt.actions.execution.NearDeadInit(
				this, executor, parent);
	}
}