package org.jitsi.provider;

import org.jitsi.utils.logging.Logger;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.xmlpull.v1.XmlPullParser;

public class ScreenSharingIqProvider extends IQProvider<ScreenSharingIq> {

    private final static Logger logger
            = Logger.getLogger(ScreenSharingIqProvider.class);

    public ScreenSharingIqProvider() {
    }

    public static void registerScreenSharingIqProviderProvider() {
        ProviderManager.addIQProvider("mute", "http://jitsi.org/jitmeet/audio", new ScreenSharingIqProvider());
    }

    public ScreenSharingIq parse(XmlPullParser parser, int initialDepth) throws Exception {
        String namespace = parser.getNamespace();
        if (!"http://jitsi.org/jitmeet/audio".equals(namespace)) {
            return null;
        } else {
            String rootElement = parser.getName();
            if ("mute".equals(rootElement)) {
                ScreenSharingIq iq = new ScreenSharingIq();
                String jidStr = parser.getAttributeValue("", "jid");

                Boolean requestScreenSharing = Boolean.parseBoolean(parser.getAttributeValue(null, "requestScreenSharing"));

                if (jidStr != null) {
                    Jid jid = JidCreate.from(jidStr);
                    iq.setJid(jid);
                }

                iq.setRequestScreenSharing(requestScreenSharing);

                String name = parser.getAttributeValue("", "actor");
                if (name != null) {
                    Jid actor = JidCreate.from(name);
                    iq.setActor(actor);
                }

                boolean done = false;

                while(!done) {
                    switch(parser.next()) {
                        case 3:
                            name = parser.getName();
                            if (rootElement.equals(name)) {
                                done = true;
                            }
                            break;
                        case 4:
                            Boolean mute = Boolean.parseBoolean(parser.getText());
                            iq.setMute(mute);
                    }
                }

                return iq;
            } else {
                return null;
            }
        }
    }
}
