/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.workflow.action;

import de.rub.nds.tlsattacker.core.exceptions.WorkflowExecutionException;
import de.rub.nds.tlsattacker.core.protocol.message.ProtocolMessage;
import de.rub.nds.tlsattacker.core.record.AbstractRecord;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.workflow.action.executor.MessageActionResult;
import de.rub.nds.tlsattacker.core.workflow.action.executor.SendMessageHelper;
import java.util.List;

/**
 * todo print configured records
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class SendAction extends MessageAction {

    public SendAction() {
        super();
    }

    public SendAction(List<ProtocolMessage> messages) {
        super();
        actualMessages = messages;
    }

    public SendAction(ProtocolMessage message) {
        super();
        actualMessages.add(message);
    }

    @Override
    public void execute(TlsContext tlsContext) {
        if (isExecuted()) {
            throw new WorkflowExecutionException("Action already executed!");
        }
        LOGGER.info("Sending " + getReadableString(actualMessages));
        MessageActionResult result = SendMessageHelper.sendMessages(actualMessages, actualRecords, tlsContext);
        actualMessages = result.getMessageList();
        actualRecords = result.getRecordList();
        setExecuted(true);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Send Action:\n");
        sb.append("Messages:\n");
        for (ProtocolMessage message : actualMessages) {
            sb.append(message.toCompactString());
            sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public boolean executedAsPlanned() {
        return isExecuted();
    }

    public void setActualRecords(List<AbstractRecord> actualRecords) {
        this.actualRecords = actualRecords;
    }
}
