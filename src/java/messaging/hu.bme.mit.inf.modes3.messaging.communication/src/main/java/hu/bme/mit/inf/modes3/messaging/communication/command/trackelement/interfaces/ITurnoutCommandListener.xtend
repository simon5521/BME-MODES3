package hu.bme.mit.inf.modes3.messaging.communication.command.trackelement.interfaces

import hu.bme.mit.inf.modes3.messaging.messages.enums.TurnoutState

interface ITurnoutCommandListener {
	def void onTurnoutCommand(int id, TurnoutState state)
}
