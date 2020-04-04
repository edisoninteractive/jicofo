package org.jitsi.provider;

import org.jitsi.utils.logging.Logger;
import org.jivesoftware.smack.provider.IQProvider;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.xmlpull.v1.XmlPullParser;

public class MuteIqWithAdminActionProvider extends IQProvider<MuteIqWithAdminAction> {

    private final static Logger logger
            = Logger.getLogger(MuteIqWithAdminActionProvider.class);

    public MuteIqWithAdminActionProvider() {
    }

    public static void registerMuteIqWithAdminActionProvider() {
        ProviderManager.addIQProvider("mute", "http://jitsi.org/jitmeet/audio", new MuteIqWithAdminActionProvider());
    }

    public MuteIqWithAdminAction parse(XmlPullParser parser, int initialDepth) throws Exception {
        String namespace = parser.getNamespace();
        if (!"http://jitsi.org/jitmeet/audio".equals(namespace)) {
            return null;
        } else {
            String rootElement = parser.getName();
            if ("mute".equals(rootElement)) {
                MuteIqWithAdminAction iq = new MuteIqWithAdminAction();
                String jidStr = parser.getAttributeValue("", "jid");

                Boolean allowUnmuteByUser = Boolean.parseBoolean(parser.getAttributeValue(null, "allowUnmuteByUser"));

                if (jidStr != null) {
                    Jid jid = JidCreate.from(jidStr);
                    iq.setJid(jid);
                }

                iq.setAllowUnMute(allowUnmuteByUser);

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
