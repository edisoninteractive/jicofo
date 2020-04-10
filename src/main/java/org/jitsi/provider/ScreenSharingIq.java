package org.jitsi.provider;

import org.jitsi.xmpp.extensions.jitsimeet.MuteIq;

public class ScreenSharingIq extends MuteIq {

    private Boolean requestScreenSharing = false;

    public Boolean getRequestScreenSharing() {
        return requestScreenSharing;
    }

    public void setRequestScreenSharing(Boolean requestScreenSharing) {
        this.requestScreenSharing = requestScreenSharing;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {

        if (this.getJid() != null) {
            xml.attribute("jid", this.getJid());
        }

        if (this.getActor() != null) {
            xml.attribute("actor", this.getActor());
        }

        xml.attribute("requestScreenSharing", this.requestScreenSharing);

        xml.rightAngleBracket().append(this.getMute().toString());

        return xml;
    }

}
