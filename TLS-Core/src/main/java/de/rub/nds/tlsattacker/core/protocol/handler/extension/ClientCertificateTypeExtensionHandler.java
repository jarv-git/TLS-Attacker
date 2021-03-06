/**
 * TLS-Attacker - A Modular Penetration Testing Framework for TLS
 *
 * Copyright 2014-2017 Ruhr University Bochum / Hackmanit GmbH
 *
 * Licensed under Apache License 2.0
 * http://www.apache.org/licenses/LICENSE-2.0
 */
package de.rub.nds.tlsattacker.core.protocol.handler.extension;

import de.rub.nds.tlsattacker.core.constants.CertificateType;
import de.rub.nds.tlsattacker.core.protocol.message.extension.ClientCertificateTypeExtensionMessage;
import de.rub.nds.tlsattacker.core.protocol.parser.extension.ClientCertificateTypeExtensionParser;
import de.rub.nds.tlsattacker.core.protocol.preparator.extension.ClientCertificateTypeExtensionPreparator;
import de.rub.nds.tlsattacker.core.protocol.serializer.extension.ClientCertificateTypeExtensionSerializer;
import de.rub.nds.tlsattacker.core.state.TlsContext;

public class ClientCertificateTypeExtensionHandler extends ExtensionHandler<ClientCertificateTypeExtensionMessage> {

    public ClientCertificateTypeExtensionHandler(TlsContext context) {
        super(context);
    }

    @Override
    public ClientCertificateTypeExtensionParser getParser(byte[] message, int pointer) {
        return new ClientCertificateTypeExtensionParser(pointer, message);
    }

    @Override
    public ClientCertificateTypeExtensionPreparator getPreparator(ClientCertificateTypeExtensionMessage message) {
        return new ClientCertificateTypeExtensionPreparator(context.getChooser(), message, getSerializer(message));
    }

    @Override
    public ClientCertificateTypeExtensionSerializer getSerializer(ClientCertificateTypeExtensionMessage message) {
        return new ClientCertificateTypeExtensionSerializer(message);
    }

    @Override
    public void adjustTLSExtensionContext(ClientCertificateTypeExtensionMessage message) {
        context.setClientCertificateTypeDesiredTypes(CertificateType.getCertificateTypesAsList(message
                .getCertificateTypes().getValue()));
    }

}
