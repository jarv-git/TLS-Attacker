/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.serializer;

import de.rub.nds.tlsattacker.core.protocol.serializer.UnknownMessageSerializer;
import de.rub.nds.tlsattacker.core.constants.ProtocolVersion;
import de.rub.nds.tlsattacker.core.protocol.message.UnknownMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.UnknownMessageParser;
import java.util.Collection;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 *
 * @author Robert Merget - robert.merget@rub.de
 */
public class UnknownMessageSerializerTest {

    private UnknownMessage msg;
    private UnknownMessageSerializer serializer;

    public UnknownMessageSerializerTest() {
    }

    @Before
    public void setUp() {
        msg = new UnknownMessage();
        serializer = new UnknownMessageSerializer(msg, ProtocolVersion.TLS12);
    }

    /**
     * Test of serializeProtocolMessageContent method, of class
     * UnknownMessageSerializer.
     */
    @Test
    public void testSerializeProtocolMessageContent() {
        msg.setCompleteResultingMessage(new byte[] { 1, 2, 3 });
        assertArrayEquals(new byte[] { 1, 2, 3 }, serializer.serialize());
    }

}