package com.core.sonoscontroller.sonos.model;


import com.core.sonoscontroller.sonos.ParserHelper;

public class TrackMetadata {
    private final String title;
    private final String creator;
    private final String albumArtist;
    private final String uri;
    private final String album;
    private final String albumArtURI;
    private final String desc;

    public TrackMetadata(String title, String creator, String albumArtist, String uri, String album, String albumArtURI, String desc) {
        this.title = title;
        this.creator = creator;
        this.albumArtist = albumArtist;
        this.uri = uri;
        this.album = album;
        this.albumArtURI = albumArtURI;
        this.desc = desc;
    }

    public static TrackMetadata parse(String metadata) {
        return new TrackMetadata(
                ParserHelper.findOne("<dc:title>(.*)</dc:title>", metadata),
                ParserHelper.findOne("<dc:creator>(.*)</dc:creator>", metadata),
                ParserHelper.findOne("<r:albumArtist>(.*)</r:albumArtist>", metadata),
				ParserHelper.findOne("<res .+?>(.*)</res>", metadata),
                ParserHelper.findOne("<upnp:album>(.*)</upnp:album>", metadata),
                ParserHelper.findOne("<upnp:albumArtURI>(.*)</upnp:albumArtURI>", metadata),
				ParserHelper.findOne("<desc .+?>(.*)</desc>", metadata)
        );
    }

    public String getTitle() {
        return title;
    }

    public String getCreator() {
        return creator;
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

	public String getUri() {
		return uri;
	}

	public String getAlbum() {
        return album;
    }

    public String getAlbumArtURI() {
        return albumArtURI;
    }

    @Override
    public String toString() {
        return "TrackMetadata{" +
                "title='" + title + '\'' +
                ", creator='" + creator + '\'' +
                ", albumArtist='" + albumArtist + '\'' +
				", uri='" + uri + '\'' +
                ", album='" + album + '\'' +
                ", albumArtURI='" + albumArtURI + '\'' +
                '}';
    }
    		  //<DIDL-Lite xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:upnp="urn:schemas-upnp-org:metadata-1-0/upnp/" xmlns:r="urn:schemas-rinconnetworks-com:metadata-1-0/" xmlns="urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/">
			  // 	<item id="100e206cspotify%3aartistTopTracks%3a3eVuump9qyK0YCQQo4mKbc" parentID="10052064spotify%3aartist%3a3eVuump9qyK0YCQQo4mKbc" restricted="true">
			  // 		<dc:title>Top Tracks</dc:title>
			  // 		<upnp:class>object.container.playlistContainer</upnp:class>
			  // 		<desc id="cdudn" nameSpace="urn:schemas-rinconnetworks-com:metadata-1-0/">SA_RINCON2311_X_#Svc2311-0-Token</desc>
			  // 	</item>
			  // </DIDL-Lite>
    public String toDIDL() {
        return "<DIDL-Lite xmlns:dc=\"http://purl.org/dc/elements/1.1/\" xmlns:upnp=\"urn:schemas-upnp-org:metadata-1-0/upnp/\" xmlns:r=\"urn:schemas-rinconnetworks-com:metadata-1-0/\" xmlns=\"urn:schemas-upnp-org:metadata-1-0/DIDL-Lite/\">" +
                    "<item>" +
                        "<dc:title>" + title + "</dc:title>" +
                        "<dc:creator>" + creator + "</dc:creator>" +
                        "<dc:albumArtist>" + albumArtist + "</dc:albumArtist>" +
                        "<upnp:album>" + album + "</upnp:album>" +
                        "<upnp:albumArtURI>" + albumArtURI + "</upnp:albumArtURI>" +
                    "</item>" +
                "</DIDL-Lite>";
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((album == null) ? 0 : album.hashCode());
		result = prime * result + ((albumArtURI == null) ? 0 : albumArtURI.hashCode());
		result = prime * result + ((uri == null) ? 0 : uri.hashCode());
		result = prime * result + ((albumArtist == null) ? 0 : albumArtist.hashCode());
		result = prime * result + ((creator == null) ? 0 : creator.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrackMetadata other = (TrackMetadata) obj;
		if (album == null) {
			if (other.album != null)
				return false;
		} else if (!album.equals(other.album))
			return false;
		if (albumArtURI == null) {
			if (other.albumArtURI != null)
				return false;
		} else if (!albumArtURI.equals(other.albumArtURI))
			return false;
		if (albumArtist == null) {
			if (other.albumArtist != null)
				return false;
		} else if (!albumArtist.equals(other.albumArtist))
			return false;
		if (creator == null) {
			if (other.creator != null)
				return false;
		} else if (!creator.equals(other.creator))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (uri == null) {
			if (other.uri != null)
				return false;
		} else if (!uri.equals(other.uri))
			return false;
		return true;
	}
    
    
}
