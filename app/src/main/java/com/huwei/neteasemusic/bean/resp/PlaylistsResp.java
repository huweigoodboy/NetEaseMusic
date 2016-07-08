package com.huwei.neteasemusic.bean.resp;

import com.huwei.neteasemusic.bean.Playlist;

import java.util.List;

/**
 * @author jerry
 * @date 2016/07/08
 */
public class PlaylistsResp extends ServerTip {
    public boolean more;
    public int total;
    public List<Playlist> playlists;
}
