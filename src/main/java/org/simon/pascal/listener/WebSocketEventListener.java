/**
 *
 */
package org.simon.pascal.listener;

import org.simon.pascal.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

/**
 * @author nsimonin1
 *
 */
@Component
public class WebSocketEventListener {
	 private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);

	    @Autowired
	    private SimpMessageSendingOperations messagingTemplate;

	    @EventListener
	    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
	        logger.info("Received a new web socket connection");
	    }

	    @EventListener
	    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	        final StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

	        final String username = (String) headerAccessor.getSessionAttributes().get("username");

	        if(username != null) {
	            logger.info("User Disconnected : " + username);

	            final ChatMessage chatMessage = new ChatMessage();
	            chatMessage.setType(ChatMessage.MessageType.LEAVE);
	            chatMessage.setSender(username);

	            messagingTemplate.convertAndSend("/topic/publicChatRoom", chatMessage);
	        }
	    }
}
