package de.rub.nds.tlsattacker.core.protocol.handler;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.bouncycastle.crypto.digests.MD5Digest;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.tls.Certificate;

import de.rub.nds.modifiablevariable.util.ArrayConverter;
import de.rub.nds.tlsattacker.core.exceptions.AdjustmentException;
import de.rub.nds.tlsattacker.core.protocol.message.ProtocolMessage;
import de.rub.nds.tlsattacker.core.protocol.message.SSL2ServerHelloMessage;
import de.rub.nds.tlsattacker.core.protocol.message.SSL2ServerVerifyMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.ProtocolMessageParser;
import de.rub.nds.tlsattacker.core.protocol.parser.SSL2ServerVerifyParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.Preparator;
import de.rub.nds.tlsattacker.core.protocol.preparator.ProtocolMessagePreparator;
import de.rub.nds.tlsattacker.core.protocol.preparator.SSL2ServerVerifyPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.ProtocolMessageSerializer;
import de.rub.nds.tlsattacker.core.protocol.serializer.Serializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;
import de.rub.nds.tlsattacker.core.util.CertificateUtils;
import de.rub.nds.tlsattacker.transport.ConnectionEndType;

public class SSL2ServerVerifyHandler extends HandshakeMessageHandler<SSL2ServerVerifyMessage> {

    public SSL2ServerVerifyHandler(TlsContext context) {
        super(context);
    }
	
	@Override
	public ProtocolMessageParser<SSL2ServerVerifyMessage> getParser(byte[] message, int pointer) {
		return new SSL2ServerVerifyParser(message, pointer, tlsContext.getChooser().getSelectedProtocolVersion());
	}

	@Override
	public ProtocolMessagePreparator<SSL2ServerVerifyMessage> getPreparator(SSL2ServerVerifyMessage message) {
		return new SSL2ServerVerifyPreparator(message, tlsContext.getChooser());
	}

	private static void md5Update(MD5Digest md5, byte[] bytes) {
		md5.update(bytes, 0, bytes.length);
	}
	
    @Override
    public void adjustTLSContext(SSL2ServerVerifyMessage message) {
    	byte[] md5Output = getMD5Output();
    	
    	RC4Engine rc4 = new RC4Engine();
    	rc4.init(false, new KeyParameter(md5Output));
    	byte[] encrypted = message.getEncryptedPart().getValue();
    	int len = encrypted.length;
		byte[] decrypted = new byte[len];
    	rc4.processBytes(encrypted, 0, len, decrypted, 0);
    	
    	if (Arrays.equals(Arrays.copyOfRange(decrypted, len - 16, len), tlsContext.getClientRandom())) {
    		LOGGER.debug("Hurray! DROWN detected!");
    		// TODO: Where do we store this information? On the tlsContext?
    	}
    }

	private byte[] getMD5Output() {
		MD5Digest md5 = new MD5Digest();
		byte[] clearKey = new byte[11];
		md5Update(md5, clearKey);
		md5Update(md5, tlsContext.getPreMasterSecret());
		md5.update((byte)'0');
		md5Update(md5, tlsContext.getClientRandom());
		md5Update(md5, tlsContext.getServerRandom());
    	byte[] md5Output = new byte[md5.getDigestSize()];
    	md5.doFinal(md5Output, 0);
		return md5Output;
	}

	@Override
	public ProtocolMessageSerializer getSerializer(SSL2ServerVerifyMessage message) {
		// We currently don't send ServerVerify messages, only receive them.
		return null;
	}
	
}
