package hu.bme.mit.inf.modes3.components.controller.command

import hu.bme.mit.inf.modes3.messaging.mms.handlers.control.SegmentCommandHandler
import hu.bme.mit.inf.modes3.messaging.mms.messages.SegmentCommandOrBuilder

package class SegmentCommandClient implements SegmentCommandHandler {
	private var TrackElementCommandCallback callback

	new(TrackElementCommandCallback controller) {
		callback = controller
	}

	override handleMessage(SegmentCommandOrBuilder message) {
		val id = message.segmentID
		val state = message.state
		callback.onSegmentCommand(id, state)
	}

}
