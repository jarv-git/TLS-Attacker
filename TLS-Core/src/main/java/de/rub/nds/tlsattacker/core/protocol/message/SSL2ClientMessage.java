/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.message;

import de.rub.nds.modifiablevariable.ModifiableVariableFactory;
import de.rub.nds.modifiablevariable.ModifiableVariableProperty;
import de.rub.nds.modifiablevariable.integer.ModifiableInteger;
import de.rub.nds.modifiablevariable.singlebyte.ModifiableByte;
import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.constants.HandshakeMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolMessageType;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;

public abstract class SSL2ClientMessage extends HandshakeMessage {

    @ModifiableVariableProperty(type = ModifiableVariableProperty.Type.LENGTH)
    private ModifiableInteger messageLength;

    @ModifiableVariableProperty
    private ModifiableByte type;

    public SSL2ClientMessage(HandshakeMessageType handshakeMessageType) {
        super(handshakeMessageType);
    }

    public ModifiableInteger getMessageLength() {
        return messageLength;
    }

    public void setMessageLength(ModifiableInteger messageLength) {
        this.messageLength = messageLength;
    }

    public void setMessageLength(Integer messageLength) {
        this.messageLength = ModifiableVariableFactory.safelySetValue(this.messageLength, messageLength);
    }

    public ModifiableByte getType() {
        return type;
    }

    public void setType(ModifiableByte type) {
        this.type = type;
    }

    public void setType(byte type) {
        this.type = ModifiableVariableFactory.safelySetValue(this.type, type);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (getType() != null && getType().getValue() != null) {
            sb.append("\n Type: ").append(getType().getValue());
        }
        return sb.toString();
    }

}