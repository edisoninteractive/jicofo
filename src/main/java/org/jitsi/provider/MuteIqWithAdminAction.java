package org.jitsi.provider;

import org.jitsi.xmpp.extensions.jitsimeet.MuteIq;

public class MuteIqWithAdminAction extends MuteIq {

    private Boolean allowUnMute = false;

    public Boolean getAllowUnMute() {
        return allowUnMute;
    }

    public void setAllowUnMute(Boolean allowUnMute) {
        this.allowUnMute = allowUnMute;
    }

    @Override
    protected IQChildElementXmlStringBuilder getIQChildElementBuilder(IQChildElementXmlStringBuilder xml) {

        if (this.getJid() != null) {
            xml.attribute("jid", this.getJid());
        }

        if (this.getActor() != null) {
            xml.attribute("actor", this.getActor());
        }

        xml.attribute("allowUnMute", this.allowUnMute);
        xml.rightAngleBracket().append(this.getMute().toString());

        return xml;
    }
}
