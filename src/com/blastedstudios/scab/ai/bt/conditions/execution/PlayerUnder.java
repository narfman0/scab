// ******************************************************* 
//                   MACHINE GENERATED CODE                
//                MUST BE CAREFULLY COMPLETED              
//                                                         
//           ABSTRACT METHODS MUST BE IMPLEMENTED          
//                                                         
// Generated on 10/19/2014 08:49:29
// ******************************************************* 
package com.blastedstudios.scab.ai.bt.conditions.execution;

import com.blastedstudios.gdxworld.util.Log;
import com.blastedstudios.gdxworld.util.Properties;
import com.blastedstudios.scab.world.WorldManager;
import com.blastedstudios.scab.world.being.NPC;
import com.blastedstudios.scab.world.being.NPC.AIFieldEnum;

/** ExecutionCondition class created from MMPM condition PlayerUnder. */
public class PlayerUnder extends
		jbt.execution.task.leaf.condition.ExecutionCondition {

	/**
	 * Constructor. Constructs an instance of PlayerUnder that is able to run a
	 * com.blastedstudios.scab.ai.bt.conditions.PlayerUnder.
	 */
	public PlayerUnder(jbt.model.core.ModelTask modelTask,
			jbt.execution.core.BTExecutor executor,
			jbt.execution.core.ExecutionTask parent) {
		super(modelTask, executor, parent);

		if (!(modelTask instanceof com.blastedstudios.scab.ai.bt.conditions.PlayerUnder)) {
			throw new java.lang.RuntimeException(
					"The ModelTask must subclass com.blastedstudios.scab.ai.bt.conditions.PlayerUnder");
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
		return Math.abs(self.getPosition().x - world.getPlayer().getPosition().x) < Properties.getFloat("ai.player.under.distance", 2f) && 
				world.getPlayer().getPosition().y < self.getPosition().y ?
						Status.SUCCESS : Status.FAILURE;
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