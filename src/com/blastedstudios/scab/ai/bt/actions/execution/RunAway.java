// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/19/2014 08:49:29
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.actions.execution;

import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.scab.world.WorldManager;
import com.blastedstudios.scab.world.being.NPC;
import com.blastedstudios.scab.world.being.NPC.AIFieldEnum;

/** ExecutionAction class created from MMPM action RunAway. */
public class RunAway extends jbt.execution.task.leaf.action.ExecutionAction {

	/**
	 * Constructor. Constructs an instance of RunAway that is able to run a
	 * com.blastedstudios.scab.ai.bt.actions.RunAway.
	 */
	public RunAway(jbt.model.core.ModelTask modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		super(modelTask, executor, parent);

		if (!(modelTask instanceof com.blastedstudios.scab.ai.bt.actions.RunAway)) {
			throw new java.lang.RuntimeException(
					"The ModelTask must subclass com.blastedstudios.scab.ai.bt.actions.RunAway");
		}
	}

	protected void internalSpawn() {
		this.getExecutor().requestInsertionIntoList(
				jbt.execution.core.BTExecutor.BTExecutorList.TICKABLE, this);
		Log.debug(this.getClass().getCanonicalName(), "spawned");
	}

	protected jbt.execution.core.ExecutionTask.Status internalTick() {
		NPC self = (NPC) getContext().getVariable(AIFieldEnum.SELF.name());
		WorldManager world = (WorldManager) getContext().getVariable(AIFieldEnum.WORLD.name());
		boolean playerLeft = world.getPlayer().getPosition().x < self.getPosition().x;
		self.setMoveLeft(!playerLeft);
		self.setMoveRight(playerLeft);
		self.aim(playerLeft ? 0f : 3.14f);
		return jbt.execution.core.ExecutionTask.Status.SUCCESS;
	}

	protected void internalTerminate() {}
	protected void restoreState(jbt.execution.core.ITaskState state) {}

	protected jbt.execution.core.ITaskState storeState() {
		return null;
	}

	protected jbt.execution.core.ITaskState storeTerminationState() {
		return null;
	}
}