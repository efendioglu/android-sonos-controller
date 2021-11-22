package com.core.sonoscontroller.sonos.listener;

import com.core.sonoscontroller.sonos.ParserHelper;
import com.core.sonoscontroller.sonos.SonosDevice;
import com.core.sonoscontroller.uPnPClient.UPnPEvent;
import com.core.sonoscontroller.uPnPClient.UPnPEventAdapter;

import java.util.List;

import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Event listener used to parse UPnPEvents received from the Queue service 
 * relating to playback rendering, eg bass, treble, volume and EQ
 * @author Kilian
 *
 */
public class RenderingControlListener extends UPnPEventAdapter {
	private static final  Namespace upnpRCNamespace =  Namespace.getNamespace("urn:schemas-upnp-org:metadata-1-0/RCS/");
	
	/**
	 * Event listeners to be notified in case of noteworthy events
	 */
	private final List<SonosEventListener> listeners;

	public RenderingControlListener(String servicePath, SonosDevice device) {
		//super(servicePath);
		listeners = device.getEventListener();
	}

	@Override
	public void eventReceived(UPnPEvent event) {
		for(Element e : event.getProperties()) {
			//UPnP media renderer events are wrapped in multiple xml elements

			//Unwrap xml event
			Element properties = ParserHelper.unwrapSonosEvent(e,upnpRCNamespace);//e.getChild("Event", upnpRCSNamespace).getChild("InstanceID",upnpRCSNamespace);

			for(Element ele : properties.getChildren()) {
				
				switch(ele.getName()) {
					
					case "Volume":
						//Master LF and RF
						if(ele.getAttributeValue("channel").equals("Master")){
							int volume = Integer.parseInt(ele.getAttributeValue("val"));
							for(SonosEventListener listener : listeners) {
								listener.volumeChanged(volume);
							}
						}
						break;
						
					case "Treble":
						int treble = Integer.parseInt(ele.getAttributeValue("val"));
						for(SonosEventListener listener : listeners) {
							listener.trebleChanged(treble);
						}
						break;
						
					case "Bass":
						int bass = Integer.parseInt(ele.getAttributeValue("val"));
						for(SonosEventListener listener : listeners) {
							listener.bassChanged(bass);
						}
						break;
						
					case "Loudness":
						boolean loudness = Integer.parseInt(ele.getAttributeValue("val"))!= 0;
						for(SonosEventListener listener : listeners) {
							listener.loudenessChanged(loudness);
						}
						break;
				}
			}
		}
	}
}
